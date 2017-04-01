package cn.seocoo.platform.admin;

import cn.seocoo.platform.common.base.BaseAction;
import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.QRCodeUtil;
import cn.seocoo.platform.common.util.StringUtil;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.*;
import cn.seocoo.platform.service.adviceImage.inf.AdviceImageService;
import cn.seocoo.platform.service.merchantInfo.inf.MerchantInfoService;
import cn.seocoo.platform.service.merchantNetSn.inf.MerchantNetSnService;
import cn.seocoo.platform.service.payChannel.inf.PayChannelService;
import cn.seocoo.platform.service.userRelationship.inf.UserRelationshipService;
import com.alibaba.fastjson.JSONObject;
import com.odchina.micro.shiro.ShiroUser;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PaySettingAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    private UserRelationshipService userRelationshipService;

    private AdviceImageService adviceImageService;
    private MerchantInfoService merchantInfoService;
    private PayChannelService payChannelService;

    private MerchantNetSnService merchantNetSnService ;


    /**
     * 首页
     *
     * @return
     */
    public String paySetting() {
        // 获取当前角色
        ShiroUser su = queryCurrentShiroUser();
        // 获取当前角色的GroupCode
        UserRelationship userRelationship = new UserRelationship();
        userRelationship.setLoginName(su.getLoginName());
        List<UserRelationship> userRelationshipList = userRelationshipService.queryUserRelationshipList(userRelationship);
        List<AdviceImage> adviceImageList = null;
        int size = 0;
        if (userRelationshipList != null & userRelationshipList.size() > 0) {
            userRelationship = userRelationshipList.get(0);
        }
        //查看 该商户是否开通支付通道
        PayChannel payChannel = new PayChannel();
        payChannel.setMerchantCode(userRelationship.getMerchantCode());
        List<PayChannel> payChannelList = payChannelService.queryPayChannelList(payChannel);
        int count = 0;
        // 判断支付通道是否为已绑定
        for (PayChannel channel : payChannelList) {
            if (StringUtils.equals(channel.getStatus().toString(), "1")) {
                count++;
            }
        }
        // 判断支付通道
        if (payChannelList != null && payChannelList.size() > 0 && count > 0) {
            request.setAttribute("hasPayChannel", "has");
        } else {
            request.setAttribute("hasPayChannel", "no");
        }
        request.setAttribute("merchantCode", userRelationship.getMerchantCode());
        return "paySetting";
    }



    /**
     * 生成静态二维码
     *
     * @return
     */
    public String staticQRCode() throws IOException {
        String merchantCode = request.getParameter("merchantCode");
        //静态二维码 链接地址  http://192.168.15.49:8081/manage/codeToPay.do?merchantCode=O148835939716898643419
        MerchantInfo merchantInfo = new MerchantInfo();
        merchantInfo.setMerchantCode(merchantCode);
        List<MerchantInfo> merchantInfos = merchantInfoService.queryMerchantInfoList(merchantInfo);
        String userpic = "";
        if (merchantInfos != null && merchantInfos.size() > 0) {
            merchantInfo = merchantInfos.get(0);
        }
        BufferedImage image = null;
        try {
        	String path = SystemConfigUtil.getSingleProperty("userpic_visit_path").getPropertyValue();
            image = QRCodeUtil.createImage(merchantInfo.getReceiptQrCode(), path+"/images/paylogo.png", true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        int i = is.available(); // 得到文件大小
        byte data[] = new byte[i];
        is.read(data); // 读数据
        is.close();
        BASE64Encoder encoder = new BASE64Encoder();
        String encode = encoder.encode(data);
        request.setAttribute("merchantCode", merchantCode);
        request.setAttribute("userpic", merchantInfo.getReceiptQrCode());
        request.setAttribute("image", encode);
        return "staticQRCode";
    }


    /**
     * 显示sn 码
     * @return
     */
    public String snSeting(){
        String merchantCode = request.getParameter("merchantCode");
        MerchantNetSn merchantNetSn = new MerchantNetSn();
        merchantNetSn.setMerchantCode(merchantCode);
        List<MerchantNetSn> merchantNetSns = merchantNetSnService.queryMerchantNetSnList(merchantNetSn);
        if (merchantNetSns != null && merchantNetSns.size() > 0) {
            merchantNetSn = merchantNetSns.get(0);
            request.setAttribute("merchantNetSn",merchantNetSn);
        }
        request.setAttribute("merchantCode", merchantCode);
        return "snSeting";
    }

    /**
     * 生成 sn 码
     */
    public void saveSnRef() throws IOException {
        JSONObject json = new JSONObject();
        String merchantCode = request.getParameter("merchantCode");
        //snCode码生成：6位随机数
        String snCode= StringUtil.randomCodeByLength(6);
        MerchantNetSn merchantNetSn = new MerchantNetSn();
        merchantNetSn.setMerchantCode(merchantCode);
        List<MerchantNetSn> merchantNetSns = merchantNetSnService.queryMerchantNetSnList(merchantNetSn);
        if (merchantNetSns != null && merchantNetSns.size() > 0) {
            json.put("resultCode", Constant.RESULT_CODE_FAIL);
            json.put("resultMsg", "您已经生成SN码");
        } else {
            merchantNetSn.setSnCode(snCode);
            merchantNetSn.setActivated(0);// 新增的是未激活
            merchantNetSnService.saveMerchantNetSn(merchantNetSn);
            json.put("resultCode", Constant.RESULT_CODE_SUCCESS);
            json.put("resultMsg", merchantNetSn);
        }
        this.sendMessages(json.toJSONString());
    }


    /**
     * 删除 sn
     */
    public  void  snCodeDel() throws IOException {
        JSONObject json = new JSONObject();
        String merchantCode = request.getParameter("merchantCode");
        MerchantNetSn merchantNetSn = new MerchantNetSn();
        merchantNetSn.setMerchantCode(merchantCode);
        List<MerchantNetSn> merchantNetSns = merchantNetSnService.queryMerchantNetSnList(merchantNetSn);
        if (merchantNetSns != null && merchantNetSns.size() > 0) {
            merchantNetSnService.deleteMerchantNetSn(merchantNetSn);
            json.put("resultCode", Constant.RESULT_CODE_SUCCESS);
            json.put("resultMsg", merchantNetSn);
        } else {
            json.put("resultCode", Constant.RESULT_CODE_FAIL);
        }
        this.sendMessages(json.toJSONString());
    }

    /**
     * 重置 sn
     */
    public  void  snCodeEdit() throws IOException {
        JSONObject json = new JSONObject();
        String merchantCode = request.getParameter("merchantCode");
        MerchantNetSn merchantNetSn = new MerchantNetSn();
        merchantNetSn.setMerchantCode(merchantCode);
        List<MerchantNetSn> merchantNetSns = merchantNetSnService.queryMerchantNetSnList(merchantNetSn);
        if (merchantNetSns != null && merchantNetSns.size() > 0) {
            merchantNetSn.setActivated(0);
            merchantNetSnService.updateMerchantNetSn(merchantNetSn);
            json.put("resultCode", Constant.RESULT_CODE_SUCCESS);
            json.put("resultMsg", merchantNetSn);
        } else {
            json.put("resultCode", Constant.RESULT_CODE_FAIL);
        }
        this.sendMessages(json.toJSONString());
    }

    /**
     * 激活 sn
     */
    public  void  activatSNCode() throws IOException {
        JSONObject json = new JSONObject();
        String merchantCode = request.getParameter("merchantCode");
        MerchantNetSn merchantNetSn = new MerchantNetSn();
        merchantNetSn.setMerchantCode(merchantCode);
        List<MerchantNetSn> merchantNetSns = merchantNetSnService.queryMerchantNetSnList(merchantNetSn);
        if (merchantNetSns != null && merchantNetSns.size() > 0) {
            merchantNetSn.setActivated(1);
            merchantNetSnService.updateMerchantNetSn(merchantNetSn);
            json.put("resultCode", Constant.RESULT_CODE_SUCCESS);
            json.put("resultMsg", merchantNetSn);
        } else {
            json.put("resultCode", Constant.RESULT_CODE_FAIL);
        }
        this.sendMessages(json.toJSONString());
    }


    public UserRelationshipService getUserRelationshipService() {
        return userRelationshipService;
    }

    public void setUserRelationshipService(UserRelationshipService userRelationshipService) {
        this.userRelationshipService = userRelationshipService;
    }

    public AdviceImageService getAdviceImageService() {
        return adviceImageService;
    }

    public void setAdviceImageService(AdviceImageService adviceImageService) {
        this.adviceImageService = adviceImageService;
    }

    public MerchantInfoService getMerchantInfoService() {
        return merchantInfoService;
    }

    public void setMerchantInfoService(MerchantInfoService merchantInfoService) {
        this.merchantInfoService = merchantInfoService;
    }

    public PayChannelService getPayChannelService() {
        return payChannelService;
    }

    public void setPayChannelService(PayChannelService payChannelService) {
        this.payChannelService = payChannelService;
    }

    public MerchantNetSnService getMerchantNetSnService() {
        return merchantNetSnService;
    }

    public void setMerchantNetSnService(MerchantNetSnService merchantNetSnService) {
        this.merchantNetSnService = merchantNetSnService;
    }
}
