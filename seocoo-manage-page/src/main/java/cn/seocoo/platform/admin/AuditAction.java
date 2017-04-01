package cn.seocoo.platform.admin;

import cn.seocoo.platform.common.base.BaseAction;
import cn.seocoo.platform.common.util.BapUtil;
import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.*;
import cn.seocoo.platform.msbank.BindPayChannel;
import cn.seocoo.platform.msbank.CreateMerchant;
import cn.seocoo.platform.msbank.PublicRequestMsg;
import cn.seocoo.platform.msbank.PublicReturnMsg;
import cn.seocoo.platform.push.AppPushMsg;
import cn.seocoo.platform.service.merchant.inf.MerchantService;
import cn.seocoo.platform.service.merchantInfo.inf.MerchantInfoService;
import cn.seocoo.platform.service.merchantRate.inf.MerchantRateService;
import cn.seocoo.platform.service.merchantUser.inf.MerchantUserService;
import cn.seocoo.platform.service.payChannel.inf.PayChannelService;
import cn.seocoo.platform.service.pushMsg.inf.PushMsgService;
import cn.seocoo.platform.service.rateDimAttr.inf.RateDimAttrService;
import cn.seocoo.platform.service.rateSku.inf.RateSkuService;
import cn.seocoo.platform.service.userRelationship.inf.UserRelationshipService;
import cn.seocoo.platform.service.wxdic.inf.WxdicService;
import cn.seocoo.platform.service.zfbdic.inf.ZfbdicService;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.odchina.micro.shiro.ShiroUser;
import com.odchina.micro.util.HttpUtils;
import com.odchina.micro.util.PayCoreUtil;
import com.odchina.micro.util.StringTools;
import com.tydic.framework.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;

public class AuditAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private UserRelationshipService userRelationshipService;

    private MerchantInfoService merchantInfoService;
    private PayChannelService payChannelService;
    private RateDimAttrService rateDimAttrService;
    private MerchantService merchantService;
    private RateSkuService rateSkuService;
    private WxdicService wxdicService;
    private ZfbdicService zfbdicService;
    private Logger logger = Logger.getLogger(this.getClass());
    private MerchantUserService merchantUserService;

    private PayChannel payChannel;

    private MerchantRateService merchantRateService;

    private PushMsgService pushMsgService;

    public String audit() {
        // 获取当前角色
        ShiroUser su = queryCurrentShiroUser();
        // 获取当前角色的GroupCode
        UserRelationship userRelationship = new UserRelationship();
        userRelationship.setLoginName(su.getLoginName());
        List<UserRelationship> userRelationshipList = userRelationshipService.queryUserRelationshipList(userRelationship);
        if (userRelationshipList != null & userRelationshipList.size() > 0) {
            userRelationship = userRelationshipList.get(0);
        }
        request.setAttribute("userRelationship", userRelationship);
        return "audit";
    }

    /**
     * 认证列表
     *
     * @return
     */
    public String auditList() {
        String groupCode = request.getParameter("groupCode");
        String select = request.getParameter("select");
        String input = request.getParameter("input");
        String pageIndex = request.getParameter("pageIndex");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");

        Integer beginRow = (Integer.parseInt(pageIndex)) * Constant.PAGESIZE_TEN;
        Map map = new HashMap();
        map.put("beginRow", beginRow);
        map.put("pageSize", Constant.PAGESIZE_TEN);
        // 查询
        if (StringUtils.isNotBlank(select)) {
            if (StringUtils.equals(select, "Auditing")) {
                map.put("certifyStatus", "1");
            } else if (StringUtils.equals(select, "Audited")) {
                map.put("certifyStatus", "2");
            }
        } else {
            map.put("certifyStatus", "1,2");
        }
        // 手机输入框 没有值则不查询
        if (StringUtils.isNotBlank(input)) {
            map.put("createUser", input);
        }
        // 时间查询
        if (StringUtils.isNotBlank(startTime)) {
            map.put("startDate", startTime + " 00:00:00");
            if (StringUtils.isNotBlank(endTime + " 23:59:59")) {
                map.put("endDate", endTime + " 23:59:59");
            } else {
                map.put("endDate", new Date());
            }
        }
        List<MerchantInfo> merchantUserPageWithMerchant = merchantInfoService.queryMerchantUserPageWithMerchant(map);
        // 记录总数
        int totalMerchant = 0;
        totalMerchant = merchantInfoService.queryMerchantInfoPageCount(map);
        // 求余 获取分页总数
        int totalPage = 0;
        int remainder = totalMerchant % Constant.PAGESIZE_TEN;
        if (remainder == 0) {
            totalPage = totalMerchant / Constant.PAGESIZE_TEN;
        } else {
            totalPage = totalMerchant / Constant.PAGESIZE_TEN + 1;
        }
        if (merchantUserPageWithMerchant != null && merchantUserPageWithMerchant.size() > 0) {
            // 获取支付通道中该商家绑定了那些通道
            PayChannel payChannel = new PayChannel();
            payChannel.setGroupCode(groupCode);
            // payChannel.setStatus(1);// 1 是绑定，0 是未绑定
            List<PayChannel> PayChannel = payChannelService.queryPayChannelList(payChannel);
            // 门店与 支付通道关联
            for (MerchantInfo merchantInfo : merchantUserPageWithMerchant) {
                boolean flag = false;
                List<PayChannel> payChanneList = new ArrayList<PayChannel>();
                for (PayChannel payChannel1 : PayChannel) {
                    if (StringUtils.equals(payChannel1.getMerchantCode(), merchantInfo.getMerchantCode())) {
                        payChanneList.add(payChannel1);
                        flag = true;
                    }
                }
                if (flag) {
                    merchantInfo.setPayChannel(payChanneList);
                }
            }
        }
        request.setAttribute("merchantInfo", merchantUserPageWithMerchant);
        request.setAttribute("totalPage", totalPage);

        return "auditList";
    }

    /**
     * 用户信息审核页面
     *
     * @return
     */
    public String auditMsgPage() {
        String merchantCode = request.getParameter("merchantCode");
        MerchantInfo merchantInfo = new MerchantInfo();
        merchantInfo.setMerchantCode(merchantCode);
        // 获取图片地址
        String userpic = SystemConfigUtil.getSingleProperty("userpic_visit_path").getPropertyValue();
        List<MerchantInfo> merchantInfoAndBankAndPic = merchantInfoService.queryMerchantInfoAndBankAndPic(merchantInfo);
        if (merchantInfoAndBankAndPic != null && merchantInfoAndBankAndPic.size() > 0) {
            merchantInfo = merchantInfoAndBankAndPic.get(0);
            merchantInfo.setBank_after_pic(userpic + merchantInfo.getBank_after_pic());
            merchantInfo.setBank_before_pic(userpic + merchantInfo.getBank_before_pic());
            merchantInfo.setID_after_pic(userpic + merchantInfo.getID_after_pic());
            merchantInfo.setID_before_pic(userpic + merchantInfo.getID_before_pic());
        }

        request.setAttribute("merchantInfo", merchantInfo);
        request.setAttribute("userpic", userpic);
        return "auditMsgPage";
    }

    /**
     * 审核成功
     *
     * @throws IOException
     */
    public void auditSuccess() throws IOException {
        String merchantCode = request.getParameter("merchantCode");
        String level = request.getParameter("level");
        // 获取平台号
        String PlatformId = SystemConfigUtil.getSingleProperty("PlatformId_ms_url").getPropertyValue();
        // 生成流水号
        String txnSeq = StringTools.getRandomString(18);
        // 保存最低费率 --获取最底等级
        String skuWxString = "";
        String skuSFBString = "";
        RateDimAttr rateDimAttr = new RateDimAttr();
        rateDimAttr.setDimCode("level");// 等级
        rateDimAttr.setDimAttrCode(level);
        List<RateDimAttr> rateDimAttrWithLastLevel = rateDimAttrService.queryRateDimAttrWithLastLevel(rateDimAttr);
        if (rateDimAttrWithLastLevel != null && rateDimAttrWithLastLevel.size() > 0) {
            rateDimAttr = rateDimAttrWithLastLevel.get(0);
            skuWxString = "bankminsheng" + "tnt001" + rateDimAttr.getDimCode() + rateDimAttr.getDimAttrCode() + "payStyleweixin";
            skuSFBString = "bankminsheng" + "tnt001" + rateDimAttr.getDimCode() + rateDimAttr.getDimAttrCode() + "payStylezhifubao";
        }
        skuWxString = MD5Util.getMD5String(skuWxString);
        skuSFBString = MD5Util.getMD5String(skuSFBString);
        // 根据SKUCode查询费率
        List<MerchantRate> merchantRateList = new ArrayList<MerchantRate>();
        List<RateSku> queryRateSkuList = rateSkuService.queryRateSkuList(new RateSku());
        for (RateSku rateSku2 : queryRateSkuList) {
            MerchantRate merchantRate = new MerchantRate();
            merchantRate.setCreateTime(new Date());
            merchantRate.setIntoRate(rateSku2.getSetRate());
            merchantRate.setTradeRate(rateSku2.getIntoRate());
            merchantRate.setMerchantCode(merchantCode + "_temp");
            if (StringUtils.equals(rateSku2.getSkuCode(), skuWxString)) {
                merchantRate.setPayChannel("weixin");
                merchantRateList.add(merchantRate);
            }
            if (StringUtils.equals(rateSku2.getSkuCode(), skuSFBString)) {
                merchantRate.setPayChannel("zhifubao");
                merchantRateList.add(merchantRate);
            }
        }
        // 将审核信息保存到商户
        MerchantInfo merchantInfo = new MerchantInfo();
        merchantInfo.setMerchantCode(merchantCode);
        List<MerchantInfo> merchantInfoAndBankAndPic = merchantInfoService.queryMerchantInfoAndBankAndPic(merchantInfo);

        JSONObject json = new JSONObject();

        if (merchantInfoAndBankAndPic != null && merchantInfoAndBankAndPic.size() > 0) {
            merchantInfo = merchantInfoAndBankAndPic.get(0);
            Merchant merchant = new Merchant();
            merchant.setAcdCode(merchantInfo.getBankAreaCode());
            merchant.setAddress(merchantInfo.getAddress());
            merchant.setAutoSettle(1);// 自动结算
            merchant.setCity(merchantInfo.getCity());
            merchant.setContactName(merchantInfo.getRealName());
            merchant.setCorpName(merchantInfo.getRealName());
            merchant.setDevType(1);// 拓展模式:1-第三方，2-民生银行
            merchant.setIdentification("");
            merchant.setIdtCard(merchantInfo.getIdNumber());
            merchant.setIsCert("0");// 是否持证|isCert:0-非持证商户，1-持证商户
            merchant.setLicId("-"); // 没有则 -
            merchant.setLicIdValidity("-");
            merchant.setMchntFullName(merchantInfo.getRealName());
            merchant.setMchntName(merchantInfo.getRealName());
            merchant.setMessage("");
            merchant.setOperId("sgkj001");// 合作方操作员编号
            merchant.setOutMchntId(merchantInfo.getMerchantCode());
            merchant.setParentMchntId(merchantInfo.getParentMerchantCode());
            merchant.setPlatformId(PlatformId);
            merchant.setProvince(merchantInfo.getProvince());
            merchant.setRemark("");
            merchant.setServTel(merchantInfo.getCreateUser());
            merchant.setTelephone(merchantInfo.getCreateUser());
            merchant.setCreateTime(new Date());
            merchant.setTxnSeq(txnSeq);
            merchant.setCmbcMchntId("");
            merchant.setFlag(0);// 新建
            try {
                merchantService.saveMerchant(merchant);
                merchantRateService.batchInsertMerchantRate(merchantRateList);
                json.put("result", Constant.RESULT_CODE_SUCCESS);
            } catch (Exception e) {
                json.put("result", Constant.RESULT_CODE_FAIL);
            }
        }

        this.sendMessages(json.toJSONString());
    }

    /**
     * 商户进件
     *
     * @throws IOException
     */
    public void intoBlance() throws IOException {
        JSONObject json = new JSONObject();
        String merchantCode = request.getParameter("merchantCode") == null ? "0" : request.getParameter("merchantCode");
        Merchant merchant = new Merchant();
        merchant.setOutMchntId(merchantCode);
        List<Merchant> queryMerchantList = merchantService.queryMerchantList(merchant);
        if (queryMerchantList != null && queryMerchantList.size() > 0) {
            merchant = queryMerchantList.get(0);
            // 转换银行接受字段
            CreateMerchant createMerchant = new CreateMerchant();
            createMerchant.setAcdCode(merchant.getAcdCode());
            createMerchant.setAddress(merchant.getAddress());
            createMerchant.setAutoSettle(merchant.getAutoSettle().toString());
            createMerchant.setCity(merchant.getCity());
            createMerchant.setContactName(merchant.getContactName());
            createMerchant.setCorpName(merchant.getCorpName());
            createMerchant.setDataSrc("2");
            createMerchant.setDevType(merchant.getDevType().toString());
            createMerchant.setIdentification(merchant.getIdentification());
            createMerchant.setIdtCard(merchant.getIdtCard());
            createMerchant.setIsCert(merchant.getIsCert());
            createMerchant.setLicId(merchant.getLicId());
            createMerchant.setLicValidity(merchant.getLicIdValidity());
            createMerchant.setMchntFullName(merchant.getMchntFullName());
            createMerchant.setMchntName(merchant.getMchntName());
            createMerchant.setMessage(merchant.getMessage());
            createMerchant.setOperId(merchant.getOperId());
            createMerchant.setOutMchntId(merchant.getOutMchntId());
            createMerchant.setParentMchntId(merchant.getParentMchntId());
            createMerchant.setPlatformId(merchant.getPlatformId());
            createMerchant.setProvince(merchant.getProvince());
            createMerchant.setRemark(merchant.getRemark());
            createMerchant.setServTel(merchant.getServTel());
            createMerchant.setTelephone(merchant.getTelephone());
            createMerchant.setTxnSeq(merchant.getTxnSeq());
            createMerchant.setCmbcMchntId(merchant.getCmbcMchntId());
            String context = JSONObject.toJSONString(createMerchant);// 转换成JSON
            // 签名：
            String sign = PayCoreUtil.getSign(context);
            System.out.println("--------------------------------------");
            System.out.println("签名：");
            System.out.println(sign);
            // 加密前：
            String signContext = PayCoreUtil.sign(sign, context);
            System.out.println("--------------------------------------");
            System.out.println("加密前：");
            System.out.println(signContext);
            // 加密后：
            String encryptContext = PayCoreUtil.encrypt(signContext);
            System.out.println("--------------------------------------");
            System.out.println("加密后：");
            System.out.println(encryptContext);
            // 生成报文信息
            PublicRequestMsg merhant = new PublicRequestMsg();
            merhant.setBusinessContext(encryptContext);

            System.out.println("报文信息======" + JSONObject.toJSONString(merhant) + "\n");

            // 银行返回
            // 如果已经进件这修改，没有进件则 进件
            // 民生银行新增商户进件接口url
            String addMerchant = SystemConfigUtil.getSingleProperty("addMerchant_ms_url").getPropertyValue();
            // 民生银行编辑商户进件接口url
            String editMerchant = SystemConfigUtil.getSingleProperty("editMerchant_ms_url").getPropertyValue();
            String str = "";
            if (StringUtils.isNotBlank(merchant.getCmbcMchntId())) {
                str = HttpUtils.sendPost(editMerchant, JSONObject.toJSONString(merhant));

            } else {
                str = HttpUtils.sendPost(addMerchant, JSONObject.toJSONString(merhant));

            }
            if (str == null || str == "") {
                json.put("resultCode", Constant.RESULT_CODE_FAIL);
                json.put("resultMsg", "网络通讯异常！");
            } else {

                System.out.println("银行返回=" + str);

                PublicReturnMsg m = JSONObject.parseObject(str, PublicReturnMsg.class);
                // 解密后
                String dncryptContext = PayCoreUtil.dncrypt(m.getBusinessContext());
                System.out.println("--------------------------------------");
                System.out.println("解密后：");
                System.out.println(dncryptContext);

                String signChkResult = PayCoreUtil.signCheck(dncryptContext);
                System.out.println("--------------------------------------");
                System.out.println("验证签名结果：");
                System.out.println(signChkResult);
                // 解析JSON 返回值
                Gson gson = new Gson();
                @SuppressWarnings("unchecked")
                Map<String, Object> paraMap = gson.fromJson(dncryptContext, Map.class);
                String body = paraMap.get("body").toString();
                @SuppressWarnings("unchecked")
                Map<String, Object> msg = gson.fromJson(body, Map.class);
                String respCode = msg.get("respCode").toString();// 银行返回状态
                String errorMsg = msg.get("errorMsg").toString();// 银行返回状态 信息
                if (!StringUtils.equals(respCode, "0000")) {
                    json.put("resultCode", Constant.RESULT_CODE_FAIL);
                    json.put("resultMsg", errorMsg);
                } else {
                    // 返回成功则 更新商户
                    json.put("resultCode", Constant.RESULT_CODE_SUCCESS);
                    json.put("resultMsg", errorMsg);
                    String cmbcMchntId = msg.get("cmbcMchntId").toString();
                    merchant.setCmbcMchntId(cmbcMchntId);
                    merchant.setFlag(1);
                    merchantService.updateMerchant(merchant);
                }
            }
        } else {
            json.put("resultCode", Constant.RESULT_CODE_FAIL);
            json.put("resultMsg", "参数出错");
        }
        this.sendMessages(json.toJSONString());
    }

    /**
     * 显示绑定信息
     *
     * @return
     */
    public String showbindingBlance() {
        String merchantCode = request.getParameter("merchantCode") == null ? "0" : request.getParameter("merchantCode");
        String groupCode = request.getParameter("groupCode");
        MerchantInfo merchantInfo = new MerchantInfo();
        merchantInfo.setMerchantCode(merchantCode);
        List<MerchantInfo> merchantInfoAndBankAndPic = merchantInfoService.queryMerchantInfoAndBankAndPic(merchantInfo);
        if (merchantInfoAndBankAndPic != null && merchantInfoAndBankAndPic.size() > 0) {
            merchantInfo = merchantInfoAndBankAndPic.get(0);
        }
        request.setAttribute("merchantInfo", merchantInfo);
        request.setAttribute("merchantCode", merchantCode);
        request.setAttribute("groupCode", groupCode);
        return "auditMsgBindingBlance";
    }

    /**
     * 显示微信绑定页
     *
     * @return
     */
    public String showWxPage() {
        String merchantCode = request.getParameter("merchantCode") == null ? "0" : request.getParameter("merchantCode");
        String cardNumber = request.getParameter("cardNumber");
        String bankNumber = request.getParameter("bankNumber");
        String realName = request.getParameter("realName");
        String groupCode = request.getParameter("groupCode");
        Merchant merchant = new Merchant();
        merchant.setOutMchntId(merchantCode);
        merchant = merchantService.queryMerchant(merchant);

        // 微信字典信息查询
        Wxdic wxdic = new Wxdic();
        List<Wxdic> queryWxdic = wxdicService.queryWxdicList(wxdic);
        HashSet merchantTypeList = new HashSet();
        for (Wxdic wx : queryWxdic) {
            String merchantType = wx.getMerchantType();
            merchantTypeList.add(merchantType);
        }
        // 获取绑定通道信息是否存在
        PayChannel payChannel = new PayChannel();
        payChannel.setApiCode("0005");
        payChannel.setGroupCode(groupCode);
        payChannel.setMerchantCode(merchant.getOutMchntId());
        List<PayChannel> queryPayChannelList = payChannelService.queryPayChannelList(payChannel);
        if (queryPayChannelList != null && queryPayChannelList.size() > 0) {
            payChannel = queryPayChannelList.get(0);
        }
        // 获取费率
        MerchantRate merchantRate = new MerchantRate();
        merchantRate.setPayChannel("weixin");// 微信的费率
        merchantRate.setMerchantCode(merchantCode + "_temp");
        List<MerchantRate> merchantRateList_temp = merchantRateService.queryMerchantRateList(merchantRate);
        merchantRate.setMerchantCode(merchantCode);
        List<MerchantRate> merchantRateList = merchantRateService.queryMerchantRateList(merchantRate);
        if (merchantRateList_temp != null && merchantRateList_temp.size() > 0) {
            merchantRate = merchantRateList_temp.get(0);
        } else if (merchantRateList != null && merchantRateList.size() > 0) {
            merchantRate = merchantRateList.get(0);
        }
        request.setAttribute("merchantRate", merchantRate);
        request.setAttribute("merchant", merchant);
        request.setAttribute("payChannel", payChannel);
        request.setAttribute("merchantTypeList", merchantTypeList);
        request.setAttribute("cardNumber", cardNumber);
        request.setAttribute("bankNumber", bankNumber);
        request.setAttribute("realName", realName);
        return "bindingWXMerchatn";
    }

    /**
     * 显示支付宝绑定页
     *
     * @return
     */
    public String showZFBPage() {
        String merchantCode = request.getParameter("merchantCode") == null ? "0" : request.getParameter("merchantCode");
        String cardNumber = request.getParameter("cardNumber");
        String bankNumber = request.getParameter("bankNumber");
        String realName = request.getParameter("realName");
        String groupCode = request.getParameter("groupCode");
        Merchant merchant = new Merchant();
        merchant.setOutMchntId(merchantCode);
        ;
        merchant = merchantService.queryMerchant(merchant);
        // 支付宝字典信息
        Zfbdic zfbdic = new Zfbdic();
        List<Zfbdic> queryZfbdic = zfbdicService.queryZfbdicList(zfbdic);
        HashSet merchantTypeList = new HashSet();
        for (Zfbdic zf : queryZfbdic) {
            String firstCategory = zf.getFirstCategory();
            merchantTypeList.add(firstCategory);
        }
        // 获取绑定通道信息是否存在
        PayChannel payChannel = new PayChannel();
        payChannel.setApiCode("0007");
        payChannel.setGroupCode(groupCode);
        payChannel.setMerchantCode(merchant.getOutMchntId());
        List<PayChannel> queryPayChannelList = payChannelService.queryPayChannelList(payChannel);
        if (queryPayChannelList != null && queryPayChannelList.size() > 0) {
            payChannel = queryPayChannelList.get(0);
        }

        // 获取费率
        MerchantRate merchantRate = new MerchantRate();
        merchantRate.setPayChannel("weixin");// 微信的费率
        merchantRate.setMerchantCode(merchantCode + "_temp");
        List<MerchantRate> merchantRateList_temp = merchantRateService.queryMerchantRateList(merchantRate);

        merchantRate.setMerchantCode(merchantCode);
        List<MerchantRate> merchantRateList = merchantRateService.queryMerchantRateList(merchantRate);
        if (merchantRateList_temp != null && merchantRateList_temp.size() > 0) {
            merchantRate = merchantRateList_temp.get(0);
        } else if (merchantRateList != null && merchantRateList.size() > 0) {
            merchantRate = merchantRateList.get(0);
        }
        request.setAttribute("merchantRate", merchantRate);

        request.setAttribute("cardNumber", cardNumber);
        request.setAttribute("bankNumber", bankNumber);
        request.setAttribute("realName", realName);
        request.setAttribute("merchant", merchant);
        request.setAttribute("payChannel", payChannel);
        request.setAttribute("merchantTypeList", merchantTypeList);
        return "bindingZFBMerchatn";
    }

    /**
     * 查询微信字典类别
     *
     * @throws IOException
     */
    public void queryWXCode() throws IOException {
        String WXCode = request.getParameter("WXCode");
        String type = request.getParameter("type");
        Wxdic wxdic = new Wxdic();
        if (StringUtils.equals(type, "0")) {
            wxdic.setMerchantType(WXCode);
        }
        if (StringUtils.equals(type, "1")) {
            wxdic.setIndustry(WXCode);
        }
        if (StringUtils.equals(type, "2")) {
            wxdic.setCategory(WXCode);
        }
        List<Wxdic> queryWxdic = wxdicService.queryWxdicList(wxdic);
        HashSet industryList = new HashSet();
        for (Wxdic wx : queryWxdic) {
            if (StringUtils.equals(type, "0")) {
                String Industry = wx.getIndustry();
                industryList.add(Industry);
            }
            if (StringUtils.equals(type, "1")) {
                String Industry = wx.getCategory();
                industryList.add(Industry);
            }
            if (StringUtils.equals(type, "2")) {
                Integer Industry = wx.getCategoryID();
                industryList.add(Industry);
            }

        }
        JSONObject json = new JSONObject();
        json.put("industryList", industryList);
        this.sendMessages(json.toJSONString());
    }

    /**
     * 查询支付宝字典类别
     *
     * @throws IOException
     */
    public void queryZFBCode() throws IOException {
        String ZFBCode = request.getParameter("ZFBCode");
        String type = request.getParameter("type");
        Zfbdic zfbdic = new Zfbdic();
        if (StringUtils.equals(type, "0")) {
            zfbdic.setFirstCategory(ZFBCode);
        }
        if (StringUtils.equals(type, "1")) {
            zfbdic.setSecondCategory(ZFBCode);
        }
        if (StringUtils.equals(type, "2")) {
            zfbdic.setThirdCategory(ZFBCode);
        }
        List<Zfbdic> queryZfbdic = zfbdicService.queryZfbdicList(zfbdic);
        HashSet industryList = new HashSet();
        for (Zfbdic zf : queryZfbdic) {
            if (StringUtils.equals(type, "0")) {
                String secondCategory = zf.getSecondCategory();
                industryList.add(secondCategory);
            }
            if (StringUtils.equals(type, "1")) {
                String thirdCategory = zf.getThirdCategory();
                if (StringUtils.equals(thirdCategory, "/")) {
                    String categoryId = zf.getCategoryId();
                    industryList.add(categoryId);
                } else {
                    industryList.add(thirdCategory);
                }
            }
            if (StringUtils.equals(type, "2")) {
                String categoryId = zf.getCategoryId();
                industryList.add(categoryId);
            }

        }
        JSONObject json = new JSONObject();
        json.put("industryList", industryList);
        this.sendMessages(json.toJSONString());
    }

    /**
     * 保存微信绑定信息
     *
     * @throws IOException
     */
    public void saveBindWxMerhant() throws IOException {
        String txnSeq = StringTools.getRandomString(18);
        // 更新
        PayChannel pc = new PayChannel();
        pc.setMerchantCode(payChannel.getMerchantCode());
        pc.setApiCode(payChannel.getApiCode());
        List<PayChannel> payChannelList = payChannelService.queryPayChannelList(pc);
        if (payChannelList != null && payChannelList.size() > 0) {
            payChannelService.updatePayChannel(payChannel);
        } else// 新建
        {
            payChannel.setTxnSeq(txnSeq);
            payChannel.setStatus(0);// 设置状态为未绑定
            payChannel.setCreateTime(new Date());
            payChannel.setApiCode("0005");// 微信编号
            payChannelService.savePayChannel(payChannel);
        }

        JSONObject json = new JSONObject();
        json.put("result", "SUCCESS");
        this.sendMessages(json.toJSONString());
    }

    /**
     * 保存支付宝绑定信息
     *
     * @throws IOException
     */
    public void saveBindZFBMerhant() throws IOException {
        String txnSeq = StringTools.getRandomString(18);
        // 更新
        PayChannel pc = new PayChannel();
        pc.setMerchantCode(payChannel.getMerchantCode());
        pc.setApiCode(payChannel.getApiCode());
        List<PayChannel> payChannelList = payChannelService.queryPayChannelList(pc);
        if (payChannelList != null && payChannelList.size() > 0) {
            payChannelService.updatePayChannel(payChannel);
        } else// 新建
        {
            payChannel.setTxnSeq(txnSeq);
            payChannel.setStatus(0);// 设置状态为未绑定
            payChannel.setCreateTime(new Date());
            payChannel.setApiCode("0007");// 微信编号
            payChannelService.savePayChannel(payChannel);
        }

        JSONObject json = new JSONObject();
        json.put("result", "SUCCESS");
        this.sendMessages(json.toJSONString());
    }

    /**
     * 绑定 支付宝到 银行
     *
     * @throws IOException
     */
    public void gotoBindZFBMerhant() throws IOException {
        String groupCode = request.getParameter("groupCode");
        String merchantCode = request.getParameter("merchantCode");
        payChannel = new PayChannel();
        payChannel.setGroupCode(groupCode);
        payChannel.setApiCode("0007");
        payChannel.setMerchantCode(merchantCode);
        PayChannel pc = payChannelService.queryPayChannel(payChannel);
        // 调用支付通道
        String gotoBingMerchant = gotoBingMerchant(pc);
        this.sendMessages(gotoBingMerchant);
    }

    /**
     * 支付通道绑定
     *
     * @return
     */
    public String gotoBingMerchant(PayChannel pc) {
        JSONObject json = new JSONObject();
        // 转换银行接受指定字段
        BindPayChannel bindPayChannel = new BindPayChannel();
        bindPayChannel.setAccount(pc.getAccount());
        bindPayChannel.setAcctName(pc.getAcctName());
        bindPayChannel.setAcctType(pc.getAcctType().toString());
        bindPayChannel.setApiCode(pc.getApiCode());
        bindPayChannel.setCmbcMchntId(pc.getCmbcMchntId());
        bindPayChannel.setDayLimit(pc.getDayLimit());
        bindPayChannel.setFixFeeRate(pc.getFixFeeRate());
        bindPayChannel.setIndustryId(pc.getIndustryId());
        bindPayChannel.setMessage(pc.getMessage());
        bindPayChannel.setMonthLimit(pc.getMonthLimit());
        bindPayChannel.setOperateType(pc.getOperateType());
        bindPayChannel.setOperId(pc.getOperId());
        bindPayChannel.setOutMchntId(pc.getMerchantCode());
        bindPayChannel.setPbcBankId(pc.getPbcBankId());
        bindPayChannel.setPlatformId(pc.getPlatformId());
        bindPayChannel.setSpecFeeRate(pc.getSpecFeeRate());
        bindPayChannel.setTxnSeq(pc.getTxnSeq());
        bindPayChannel.setCmbcSignId(pc.getCmbcSignId());
        String context = JSONObject.toJSONString(bindPayChannel);// 转换成JSON
        // 签名：
        String sign = PayCoreUtil.getSign(context);
        System.out.println("--------------------------------------");
        System.out.println("签名：");
        System.out.println(sign);
        // 加密前：
        String signContext = PayCoreUtil.sign(sign, context);
        System.out.println("--------------------------------------");
        System.out.println("加密前：");
        System.out.println(signContext);
        // 加密后：
        String encryptContext = PayCoreUtil.encrypt(signContext);
        System.out.println("--------------------------------------");
        System.out.println("加密后：");
        System.out.println(encryptContext);
        // 生成报文信息
        PublicRequestMsg merhant = new PublicRequestMsg();
        merhant.setBusinessContext(encryptContext);

        System.out.println("报文信息======" + JSONObject.toJSONString(merhant) + "\n");

        // 银行返回
        // 如果已经绑定 支付通道则修改信息
        // 民生银行新增支付通道接口url
        String addPayChannel = SystemConfigUtil.getSingleProperty("addPayChannel_ms_url").getPropertyValue();
        // 民生银行编辑支付通道接口url
        String editPayChannel = SystemConfigUtil.getSingleProperty("editPayChannel_ms_url").getPropertyValue();
        String str = "";
        if (StringUtils.isNotBlank(pc.getCmbcSignId())) {
            str = HttpUtils.sendPost(editPayChannel, JSONObject.toJSONString(merhant));
        } else {
            str = HttpUtils.sendPost(addPayChannel, JSONObject.toJSONString(merhant));
        }
        if (str == null) {
            json.put("resultCode", Constant.RESULT_CODE_FAIL);
            json.put("resultMsg", "网络通讯异常！");
        } else {

            System.out.println("银行返回=" + str);

            PublicReturnMsg m = JSONObject.parseObject(str, PublicReturnMsg.class);
            // 解密后
            String dncryptContext = PayCoreUtil.dncrypt(m.getBusinessContext());
            System.out.println("--------------------------------------");
            System.out.println("解密后：");
            System.out.println(dncryptContext);

            String signChkResult = PayCoreUtil.signCheck(dncryptContext);
            System.out.println("--------------------------------------");
            System.out.println("验证签名结果：");
            System.out.println(signChkResult);
            // 解析JSON 返回值
            Gson gson = new Gson();
            @SuppressWarnings("unchecked")
            Map<String, Object> paraMap = gson.fromJson(dncryptContext, Map.class);
            String body = paraMap.get("body").toString();
            @SuppressWarnings("unchecked")
            Map<String, Object> msg = gson.fromJson(body, Map.class);
            String respCode = msg.get("respCode").toString();
            String errorMsg = msg.get("errorMsg").toString();
            if (StringUtils.equals(respCode, "0000")) {
                json.put("resultCode", Constant.RESULT_CODE_SUCCESS);
                json.put("resultMsg", errorMsg);
                // 返回成功则更新支付通道状态
                String cmbcSignId = msg.get("cmbcSignId").toString();
                pc.setCmbcSignId(cmbcSignId);
                pc.setStatus(1);
                payChannelService.updatePayChannel(pc);
                // 返回成功则更新商户状态
                Merchant merchant = new Merchant();
                merchant.setGroupCode(pc.getGroupCode());
                merchant.setOutMchntId(pc.getMerchantCode());
                merchant.setFlag(2);// 将状改为 已绑定
                merchantService.updateMerchantByOutMchntId(merchant);
                logger.debug("gotoBingMerchant 绑定支付通道成功：");
            } else {
                json.put("resultCode", Constant.RESULT_CODE_FAIL);
                json.put("resultMsg", errorMsg);
            }
        }
        return json.toJSONString();
    }

    /**
     * 查看 会员进件后的信息
     *
     * @return
     */
    public String showAudit() {
        String merchantCode = request.getParameter("merchantCode");

        Merchant merchant = new Merchant();
        merchant.setOutMchntId(merchantCode);
        List<Merchant> queryMerchantList = merchantService.queryMerchantList(merchant);
        if (queryMerchantList != null && queryMerchantList.size() > 0) {
            merchant = queryMerchantList.get(0);

        }
        MerchantInfo merchantInfo = new MerchantInfo();
        merchantInfo.setMerchantCode(merchantCode);
        List<MerchantInfo> merchantInfoAndBankAndPic = merchantInfoService.queryMerchantInfoAndBankAndPic(merchantInfo);
        if (merchantInfoAndBankAndPic != null && merchantInfoAndBankAndPic.size() > 0) {
            merchantInfo = merchantInfoAndBankAndPic.get(0);
        }
        request.setAttribute("merchantInfo", merchantInfo);
        request.setAttribute("merchant", merchant);
        return "showAudit";
    }

    /**
     * 审核不通过
     *
     * @throws IOException
     */
    public void auditFail() throws IOException {
        String merchantCode = request.getParameter("merchantCode");
        String failMsg = request.getParameter("failMsg");
        String title = "审核不通过";
        MerchantInfo merchantInfo = new MerchantInfo();
        merchantInfo.setMerchantCode(merchantCode);
        merchantInfo.setCertifyStatus(0);
        JSONObject json = new JSONObject();
        try {
            merchantInfoService.updateMerchantInfo(merchantInfo);
            new Thread(new countProfitThread(merchantCode, failMsg, title)).start();
            json.put("result", Constant.RESULT_CODE_SUCCESS);
        } catch (Exception e) {
            json.put("result", Constant.RESULT_CODE_FAIL);
        }
        this.sendMessages(json.toJSONString());
    }

    /**
     * 手机推送
     *
     * @param merchantCode
     * @param massage
     */
    public void pushMsgToUser(String merchantCode, String massage, String title) {
        // 老板app数据推送
        MerchantUser mUser = new MerchantUser();
        mUser.setMerchantCode(merchantCode);
        List<MerchantUser> merchantUserList = merchantUserService.queryMerchantUserList(mUser);
        if (merchantUserList != null && merchantUserList.size() > 0) {
            for (MerchantUser meUser : merchantUserList) {
                if (meUser != null && !"".equals(meUser.getDeviceToken())) {
                    PushMsg pushMsg = new PushMsg();
                    pushMsg.setMsgTitle(title);
                    pushMsg.setTarget(meUser.getLoginName());
                    pushMsg.setTerminal(meUser.getTerminal());
                    pushMsg.setMsgCotent(massage);
                    pushMsg.setCreateTime(new Date());
                    Integer id = pushMsgService.InsertPushMsg(pushMsg);
                    String manageUrl = SystemConfigUtil.getSingleProperty("manage_visit_path").getPropertyValue();
                    String url = manageUrl + "/wap!showPushMsgToUser.do?id=" + id;
                    pushMsg.setMsgUrl(url);


                    AppPushMsg push = new AppPushMsg();
                    push.setTarget(meUser.getLoginName());
                    push.setMsgType("voice");
                    push.setMsg(massage);
                    push.setMsgTitle(title);
                    push.setMsgUrl(url);
                    push.setMerchantCode(meUser.getMerchantCode());
                    push.setTerminal(meUser.getTerminal());
                    pushMsg(push);
                }
            }
        }
    }

    /**
     * 推送消息
     */
    public void pushMsg(AppPushMsg pushMsg) {
        String param = JSONObject.toJSONString(pushMsg);
        String msg = BapUtil.generateRequestMessage(param, "pushSingle");
        String push_url = SystemConfigUtil.getSingleProperty("msg_push_url").getPropertyValue();
        // push_url="http://127.0.0.1:8092/push/service/home.do";
        BapUtil.httpSendJson(push_url, msg);
    }

    /**
     * 开启线程 发送 消息
     */
    class countProfitThread implements Runnable {
        private final Logger pushLogger = Logger.getLogger(AuditAction.class);
        private String merchantCode = null;
        private String failMsg = null;
        private String title = null;

        public countProfitThread(String merchantCode, String failMsg, String title) {
            this.merchantCode = merchantCode;
            this.failMsg = failMsg;
            this.title = title;
        }

        public void run() {
            try {
                pushLogger.debug("======================>countProfit thread  in ==========================");
                pushMsgToUser(merchantCode, failMsg, title);
            } catch (Exception e) {
                pushLogger.debug("========>countProfit service error!");
            }
        }
    }

    public UserRelationshipService getUserRelationshipService() {
        return userRelationshipService;
    }

    public void setUserRelationshipService(UserRelationshipService userRelationshipService) {
        this.userRelationshipService = userRelationshipService;
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

    public RateDimAttrService getRateDimAttrService() {
        return rateDimAttrService;
    }

    public void setRateDimAttrService(RateDimAttrService rateDimAttrService) {
        this.rateDimAttrService = rateDimAttrService;
    }

    public MerchantService getMerchantService() {
        return merchantService;
    }

    public void setMerchantService(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    public RateSkuService getRateSkuService() {
        return rateSkuService;
    }

    public void setRateSkuService(RateSkuService rateSkuService) {
        this.rateSkuService = rateSkuService;
    }

    public MerchantRateService getMerchantRateService() {
        return merchantRateService;
    }

    public void setMerchantRateService(MerchantRateService merchantRateService) {
        this.merchantRateService = merchantRateService;
    }

    public WxdicService getWxdicService() {
        return wxdicService;
    }

    public void setWxdicService(WxdicService wxdicService) {
        this.wxdicService = wxdicService;
    }

    public ZfbdicService getZfbdicService() {
        return zfbdicService;
    }

    public void setZfbdicService(ZfbdicService zfbdicService) {
        this.zfbdicService = zfbdicService;
    }

    public PayChannel getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(PayChannel payChannel) {
        this.payChannel = payChannel;
    }

    public MerchantUserService getMerchantUserService() {
        return merchantUserService;
    }

    public void setMerchantUserService(MerchantUserService merchantUserService) {
        this.merchantUserService = merchantUserService;
    }

    public PushMsgService getPushMsgService() {
        return pushMsgService;
    }

    public void setPushMsgService(PushMsgService pushMsgService) {
        this.pushMsgService = pushMsgService;
    }


}
