package cn.seocoo.platform.manage.merchant;

import cn.seocoo.platform.admin.AuditAction;
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
import cn.seocoo.platform.service.area.inf.AreaService;
import cn.seocoo.platform.service.bank.inf.BankService;
import cn.seocoo.platform.service.bankInfo.inf.BankInfoService;
import cn.seocoo.platform.service.dimDicInfo.inf.DimDicInfoService;
import cn.seocoo.platform.service.group.inf.GroupService;
import cn.seocoo.platform.service.groupMerchantPay.inf.GroupMerchantPayService;
import cn.seocoo.platform.service.merchant.inf.MerchantService;
import cn.seocoo.platform.service.merchantInfo.inf.MerchantInfoService;
import cn.seocoo.platform.service.merchantRate.inf.MerchantRateService;
import cn.seocoo.platform.service.merchantUser.inf.MerchantUserService;
import cn.seocoo.platform.service.payChannel.inf.PayChannelService;
import cn.seocoo.platform.service.pushMsg.inf.PushMsgService;
import cn.seocoo.platform.service.rateDimAttr.inf.RateDimAttrService;
import cn.seocoo.platform.service.rateSku.inf.RateSkuService;
import cn.seocoo.platform.service.userBank.inf.UserBankService;
import cn.seocoo.platform.service.userIdinfo.inf.UserIdinfoService;
import cn.seocoo.platform.service.userImage.inf.UserImageService;
import cn.seocoo.platform.service.userRelationship.inf.UserRelationshipService;
import cn.seocoo.platform.service.wxdic.inf.WxdicService;
import cn.seocoo.platform.service.zfbdic.inf.ZfbdicService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.odchina.micro.shiro.ShiroUser;
import com.odchina.micro.util.HttpUtils;
import com.odchina.micro.util.PayCoreUtil;
import com.odchina.micro.util.StringTools;
import com.tydic.framework.util.MD5Util;
import com.tydic.framework.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class MerchantAction extends BaseAction {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Merchant merchant;
    private PayChannel payChannel;
    private UserRelationshipService userRelationshipService;

    private MerchantService merchantService;
    private AreaService areaService;
    private WxdicService wxdicService;
    private ZfbdicService zfbdicService;
    private GroupService groupService;
    private MerchantInfoService merchantInfoService;
    private GroupMerchantPayService groupMerchantPayService;
    private MerchantUserService merchantUserService;
    private RateSkuService rateSkuService;
    private PayChannelService payChannelService;

    private MerchantRateService merchantRateService;

    private PushMsgService pushMsgService;
    private RateDimAttrService rateDimAttrService;
    private UserIdinfoService userIdinfoService;
    private UserBankService userBankService;

    private DimDicInfoService dimDicInfoService;

    private BankService bankService;
    private BankInfoService bankInfoService;
    private UserImageService userImageService ;


    private Logger logger = Logger.getLogger(this.getClass());

    public String show() {
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
        return "merchant";
    }

    /**
     * 获取直属商户列表
     *
     * @return
     */
    public String directlyMerchantList() {
        String groupCode = request.getParameter("groupCode");
        String select = request.getParameter("select");
        String input = request.getParameter("input");
        String pageIndex = request.getParameter("pageIndex");
        Integer beginRow = (Integer.parseInt(pageIndex)) * Constant.PAGESIZE_TEN;
        List<String> groupList = new ArrayList<String>();
        groupList.add(groupCode);
        Map map = new HashMap();
        map.put("beginRow", beginRow);
        map.put("pageSize", Constant.PAGESIZE_TEN);
        // 查询
        if (StringUtils.isNotBlank(select) && StringUtils.isNotBlank(input)) {
            if (StringUtils.equals(select, "Code")) {
                map.put("outMchntId", input);
            } else if (StringUtils.equals(select, "Name")) {
                map.put("mchntName", input);
            }
        }
        //查询 当前代理商 的商户
        GroupMerchantPay groupMerchantPay = new GroupMerchantPay();
        groupMerchantPay.setGroupCode(groupCode);
        final List<GroupMerchantPay> groupMerchantPays = groupMerchantPayService.queryGroupMerchantPayList(groupMerchantPay);
        if (groupMerchantPays != null && groupMerchantPays.size() > 0) {
            groupMerchantPay = groupMerchantPays.get(0);
        }
        // 获取角色 名称 是不是审核员

        int isAudito = 0;
        ShiroUser su = queryCurrentShiroUser();
        String key = SystemConfigUtil.getSingleProperty("payAuditorKey").getPropertyValue();
        Collection<String> roleNames = getRoleNames(su.getLoginName());
        if (roleNames == null) {
            roleNames = new HashSet<String>();
        }
        for (String roleName : roleNames) {
            if (roleName.contains(key)) {
                isAudito = 1;
            }
        }
        if (isAudito == 1) {
            map.put("parentMerchantCode", "");
        } else if (StringUtils.isNotBlank(groupMerchantPay.getMerchantCode())) {
            map.put("parentMerchantCode", groupMerchantPay.getMerchantCode());
        } else {
            map.put("parentMerchantCode", "1");
        }

        List<Merchant> merchantPage = merchantService.queryMerchantWithMerchantInfo(map);
        // 记录总数
        int totalMerchant = 0;
        totalMerchant = merchantService.queryMerchantWithMerchantInfoCount(map);
        // 求余 获取分页总数
        int totalPage = 0;
        int remainder = totalMerchant % Constant.PAGESIZE_TEN;
        if (remainder == 0) {
            totalPage = totalMerchant / Constant.PAGESIZE_TEN;
        } else {
            totalPage = totalMerchant / Constant.PAGESIZE_TEN + 1;
        }
        // 获取支付通道中该商家绑定了那些通道
        // 获取 merchantCode集合
        List<String> merchantCodeList = new ArrayList<String>();
        for (Merchant mt : merchantPage) {
            merchantCodeList.add(mt.getOutMchntId());
        }
        Map mp = new HashMap();
        mp.put("merchantCode",merchantCodeList);
        mp.put("status",1);
        List<PayChannel> PayChannel = payChannelService.queryPayChannelByMerchantCodes(mp);
        // 门店与 支付通道关联
        //String payMerchant = "";
        //GroupMerchantPay pay = new GroupMerchantPay();
        //pay.setGroupCode(groupCode);
        //List<GroupMerchantPay> payList = groupMerchantPayService.queryGroupMerchantPayList(pay);
        //if (payList != null && payList.size() > 0) {
        //    payMerchant = payList.get(0).getMerchantCode();
        //}

        for (Merchant merchant : merchantPage) {
            boolean flag = false;
            List<PayChannel> payChanneList = new ArrayList<PayChannel>();
            for (PayChannel payChannel : PayChannel) {
                if (StringUtils.equals(payChannel.getMerchantCode(), merchant.getOutMchntId()) && StringUtils.equals(payChannel.getCmbcMchntId(), merchant.getCmbcMchntId())) {
                    payChanneList.add(payChannel);
                    flag = true;
                }
            }
            if (flag) {
                merchant.setPayChannel(payChanneList);
            }
            //if (StringUtils.equals(merchant.getOutMchntId(),payMerchant)) {
            //    merchant.setPay(1);
            //} else {
            //    merchant.setPay(0);
            //}
        }
        request.setAttribute("merchantPage", merchantPage);
        request.setAttribute("totalPage", totalPage);

        return "directlyMerchantList";
    }

    /**
     * 显示添加商户页面
     *
     * @return
     */
    public String showAddMerchant() {
        String groupCode = request.getParameter("groupCode");
        // 初始化商户号 如： O761T20170112092501504
        String outMchntId = "O" + StringTools.getRandomString(21);
        // 获取用户信息
        ShiroUser su = queryCurrentShiroUser();
        // 初始化区域
        Area area = new Area();
        area.setPcode(Constant.CHINA_CODE);
        //查询 商户等级
        DimDicInfo dimDicInfo = new DimDicInfo();
        dimDicInfo.setCode("level");
        List<DimDicInfo> dimDicInfos = dimDicInfoService.queryDimDicAttrValue(dimDicInfo);
        List<Area> provinceList = areaService.queryAreaList(area);
        // 查询 银行列表
        Bank bank = new Bank();
        List<Bank> banks = bankService.queryBankList(bank);
        request.setAttribute("provinceList", provinceList);
        request.setAttribute("outMchntId", outMchntId);
        request.setAttribute("groupCode", groupCode);
        request.setAttribute("LoginName", su.getLoginName());
        request.setAttribute("dimDicInfos", dimDicInfos);
        request.setAttribute("banks", banks);
        return "showAddMerchant";
    }

    public void queryBankInfo() throws IOException {
        String cityCode = request.getParameter("cityCode");
        int bankId = request.getParameter("bankId") == null ? 0 : Integer.valueOf(request.getParameter("bankId"));

        BankInfo bankInfo = new BankInfo();
        bankInfo.setAreaCode(cityCode);
        bankInfo.setBankId(bankId);
        List<BankInfo> bankInfos = bankInfoService.queryBankInfoList(bankInfo);
        JSONObject json = new JSONObject();
        if (bankInfos != null && bankInfos.size() > 0) {
            json.put("resultCode", Constant.RESULT_CODE_SUCCESS);
            json.put("bankInfos", bankInfos);
        } else {
            json.put("resultCode", Constant.RESULT_CODE_FAIL);
        }
        this.sendMessages(json.toJSONString());
    }

    /**
     * 获取省份下的所有城市列表
     *
     * @param provinceCode
     * @throws IOException
     */
    public void queryCityList() throws IOException {
        String provinceCode = request.getParameter("provinceCode");
        // City city = new City();
        // city.setProvinceCode(provinceCode);
        // List<City> provinceList = cityService.queryCityList(city );
        // 省份下拉列表
        if (StringUtils.isBlank(provinceCode)) {
            provinceCode = "000";
        }
        Area area = new Area();
        area.setPcode(provinceCode);
        List<Area> provinceList = areaService.queryAreaList(area);

        String json = JSON.toJSONString(provinceList);
        this.sendMessages(json);
    }

    /**
     * 提交保存商户信息
     *
     * @throws IOException
     */
    public void saveMerchant() throws IOException {

        String PlatformId = SystemConfigUtil.getSingleProperty("PlatformId_ms_url").getPropertyValue();
        String txnSeq = StringTools.getRandomString(18);
        ShiroUser su = queryCurrentShiroUser();
        String receiptQrCode = SystemConfigUtil.getSingleProperty("manage_visit_path").getPropertyValue();
        String loginName = su.getLoginName();
        JSONObject json = new JSONObject();
        // 获取角色 名称 是不是审核员

        int isAudito = 0;
        String key = SystemConfigUtil.getSingleProperty("payAuditorKey").getPropertyValue();
        Collection<String> roleNames = getRoleNames(su.getLoginName());
        if (roleNames == null) {
            roleNames = new HashSet<String>();
        }
        for (String roleName : roleNames) {
            if (roleName.contains(key)) {
                isAudito = 1;
            }
        }
        try {
            merchantService.saveMerchantAndMerchantUser(merchant, PlatformId, txnSeq, loginName, receiptQrCode, isAudito);
            json.put("result", Constant.RESULT_CODE_SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", Constant.RESULT_CODE_FAIL);
        }
        this.sendMessages(json.toJSONString());
    }

    /**
     * 验证代理商 是否可以 添加商户
     */
    public void saveMerchantChecked() throws IOException {
        JSONObject json = new JSONObject();
        ShiroUser su = queryCurrentShiroUser();
        //验证是否是 管理员
        int isAudito = 0;
        String key = SystemConfigUtil.getSingleProperty("payAuditorKey").getPropertyValue();
        Collection<String> roleNames = getRoleNames(su.getLoginName());
        if (roleNames == null) {
            roleNames = new HashSet<String>();
        }
        for (String roleName : roleNames) {
            if (roleName.contains(key)) {
                isAudito = 1;
            }
        }
        //如果是 管理员 则通过
        if (isAudito == 1) {
            json.put("resultCode", Constant.RESULT_CODE_SUCCESS);
        } else {
            // 验证 是否有 代理商 商户
            UserRelationship userRelationship = new UserRelationship();
            userRelationship.setLoginName(su.getLoginName());
            List<UserRelationship> userRelationshipList = userRelationshipService.queryUserRelationshipList(userRelationship);
            if (userRelationshipList != null & userRelationshipList.size() > 0) {
                userRelationship = userRelationshipList.get(0);
                GroupMerchantPay groupMerchantPay = new GroupMerchantPay();
                groupMerchantPay.setGroupCode(userRelationship.getGroupCode());
                List<GroupMerchantPay> groupMerchantPays = groupMerchantPayService.queryGroupMerchantPayList(groupMerchantPay);
                if (groupMerchantPays != null && groupMerchantPays.size() > 0) {
                    Merchant merhcant = new Merchant();
                    merhcant.setOutMchntId(groupMerchantPays.get(0).getMerchantCode());
                    List<Merchant> merchants = merchantService.queryMerchantList(merhcant);
                    if (merchants != null && merchants.size() > 0) {
                        if (merchants.get(0).getFlag() == 2) {
                            json.put("resultCode", Constant.RESULT_CODE_SUCCESS);
                        } else {
                            json.put("resultCode", Constant.RESULT_CODE_FAIL);
                            json.put("resultMsg", "代理商商户未进件或未绑定");
                        }
                    } else {
                        json.put("resultCode", Constant.RESULT_CODE_FAIL);
                        json.put("resultMsg", "代理商商户不存在");
                    }

                } else {
                    json.put("resultCode", Constant.RESULT_CODE_FAIL);
                    json.put("resultMsg", "代理商商户不存在");
                }
            }
        }
        this.sendMessages(json.toJSONString());

    }

    /***
     * 编辑商户信息
     */
    public String showEditeMerchant() {
        String merchantId = request.getParameter("merchantId") == null ? "0" : request.getParameter("merchantId");
        String groupCode = request.getParameter("groupCode");
        merchant = new Merchant();
        merchant.setId(Integer.valueOf(merchantId));
        Merchant mer = merchantService.queryMerchant(merchant);
        // 通过区域代码|acdCode: 获取省市编号
        queryAreaMassage(request, mer.getAcdCode(), "1");
        // 判断拓展人员编号|operId:过长则截取
        if (mer.getOperId().length() > 15) {
            mer.setOperId(mer.getOperId().substring(0, 15));
        }
        //银行区域
        UserBank userBank = new UserBank();
        if (StringUtils.isNotBlank(mer.getOutMchntId())) {
            userBank.setMerchantCode(mer.getOutMchntId());
        } else {
            userBank.setMerchantCode("-1");
        }
        List<UserBank> userBanks = userBankService.queryUserBankList(userBank);

        if (userBanks != null && userBanks.size() > 0) {
            userBank = userBanks.get(0);
            queryAreaMassage(request, userBank.getBankAreaCode(), "2");
            mer.setCardNumber(userBank.getCardNumber());
            mer.setAffiliatedBank(userBank.getAffiliatedBank());
            mer.setBankName(userBank.getBankName());
            mer.setBankNumber(userBank.getBankNumber());

        }
        //  查询商户等级
        DimDicInfo dimDicInfo = new DimDicInfo();
        dimDicInfo.setCode("level");
        List<DimDicInfo> dimDicInfos = dimDicInfoService.queryDimDicAttrValue(dimDicInfo);

        //查询所属银行
        Bank bank = new Bank();
        List<Bank> banks = bankService.queryBankList(bank);
        // 查询开户行
        BankInfo bankInfo = new BankInfo();
        if (StringUtils.isNotBlank(mer.getBankNumber())) {
            bankInfo.setBankCode(mer.getBankNumber());
        } else {
            bankInfo.setBankCode("-1");
        }
        List<BankInfo> bankInfos = bankInfoService.queryBankInfoList(bankInfo);
        if (bankInfos != null && bankInfos.size() > 0) {
            bankInfo = bankInfos.get(0);
        }
        bankInfo.setBankCode("");
        bankInfo.setBankName("");
        bankInfo.setId(null);
        List<BankInfo> bankInfosList = new ArrayList<BankInfo>();
        if (StringUtils.isNotBlank(bankInfo.getBankId()+"") && StringUtils.isNotBlank(bankInfo.getAreaCode())) {
           bankInfosList = bankInfoService.queryBankInfoList(bankInfo);
        }
        // 查询门店信息
        MerchantInfo merchantInfo = new MerchantInfo();
        if (StringUtils.isNotBlank(mer.getOutMchntId())) {
            merchantInfo.setMerchantCode(mer.getOutMchntId());
        } else {
            merchantInfo.setMerchantCode("-1");
        }
        final List<MerchantInfo> merchantInfos = merchantInfoService.queryMerchantInfoList(merchantInfo);
        if (merchantInfos != null && merchantInfos.size() > 0) {
            merchantInfo = merchantInfos.get(0);
            mer.setMerchantLevel(merchantInfo.getLevel());
        }
        // 如果是手机段  获取图片
        UserImage userImage = new UserImage();
        userImage.setMerchantCode(mer.getOutMchntId());
        final List<UserImage> userImages = userImageService.queryUserImageList(userImage);
        if (userImages != null && userImages.size()>0) {
            String resourcePath=SystemConfigUtil.getSingleProperty("RESOURCES_PATH_MANAGE").getPropertyValue();
            mer.setIdBeforePic(resourcePath+userImages.get(0).getiD_before_pic());
            mer.setIdAfterPic(resourcePath+userImages.get(0).getiD_after_pic());
            mer.setCheckPic1(resourcePath+userImages.get(0).getBank_before_pic());
            mer.setCheckPic2(resourcePath+userImages.get(0).getBank_after_pic());
        }
        request.setAttribute("dimDicInfos", dimDicInfos);
        request.setAttribute("bankInfosList", bankInfosList);
        request.setAttribute("banks", banks);
        request.setAttribute("merchant", mer);
        return "showEditeMerchant";
    }

    /**
     * 保存编辑后商户
     *
     * @throws IOException
     */
    public void saveEditeMerchant() throws IOException {
        // 判断拓展人员编号|operId:过长则截取
        if (merchant.getOperId().length() > 15) {
            merchant.setOperId(merchant.getOperId().substring(0, 15));
        }

        JSONObject json = new JSONObject();
        try {
            merchantService.updateMerchantAndMerchantUser(merchant);
            json.put("result", Constant.RESULT_CODE_SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", Constant.RESULT_CODE_FAIL);
        }
        this.sendMessages(json.toJSONString());
    }

    /***********
     * 商户 进件 民生银行
     *
     * @throws IOException
     */
    public void intoBlance() throws IOException {
        JSONObject json = new JSONObject();
        String merchantId = request.getParameter("merchantId") == null ? "0" : request.getParameter("merchantId");
        merchant = new Merchant();
        merchant.setId(Integer.valueOf(merchantId));
        merchant = merchantService.queryMerchant(merchant);
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
        this.sendMessages(json.toJSONString());
    }

    /**
     * 显示绑定信息
     *
     * @return
     */
    public String showbindingBlance() {
        String groupCode = request.getParameter("groupCode");
        String merchantId = request.getParameter("merchantId") == null ? "0" : request.getParameter("merchantId");
        request.setAttribute("groupCode", groupCode);
        request.setAttribute("merchantId", merchantId);
        return "showBindingBlance";
    }

    /**
     * 显示微信绑定页
     *
     * @return
     */
    public String showWxPage() {
        String groupCode = request.getParameter("groupCode");
        String merchantId = request.getParameter("merchantId") == null ? "0" : request.getParameter("merchantId");
        merchant = new Merchant();
        merchant.setId(Integer.valueOf(merchantId));
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
        payChannel.setGroupCode(groupCode);
        payChannel.setApiCode("0005");
        payChannel.setMerchantCode(merchant.getOutMchntId());
        List<PayChannel> payChannels = payChannelService.queryPayChannelList(payChannel);
        if (payChannels != null && payChannels.size() > 0) {
            payChannel = payChannels.get(0);
        }

        //带入银行开户人


        if (StringUtil.isEmpty(payChannel.getAccount())) {
            //查询银行卡信息
            UserBank bank = new UserBank();
            bank.setMerchantCode(merchant.getOutMchntId());
            List<UserBank> bankList = userBankService.queryUserBankList(bank);
            if (bankList != null && bankList.size() > 0) {
                bank = bankList.get(0);
                payChannel.setAccount(bank.getCardNumber());
                payChannel.setPbcBankId(bank.getBankNumber());

                UserIdinfo idInfo = new UserIdinfo();
                idInfo.setMerchantCode(merchant.getOutMchntId());
                List<UserIdinfo> idInfoList = userIdinfoService.queryUserIdinfoList(idInfo);
                if (idInfoList != null && idInfoList.size() > 0) {
                    payChannel.setAcctName(idInfoList.get(0).getRealName());
                }
            }
        }
        //根据等级带入费率
        MerchantInfo info = new MerchantInfo();
        info.setMerchantCode(merchant.getOutMchntId());
        List<MerchantInfo> infoList = merchantInfoService.queryMerchantInfoList(info);
        if (infoList != null && infoList.size() > 0) {
            String skuWxString = "";
            RateDimAttr rateDimAttr = new RateDimAttr();
            rateDimAttr.setDimCode("level");// 等级
            rateDimAttr.setDimAttrCode(infoList.get(0).getLevel());
            List<RateDimAttr> rateDimAttrWithLastLevel = rateDimAttrService.queryRateDimAttrWithLastLevel(rateDimAttr);
            if (rateDimAttrWithLastLevel != null && rateDimAttrWithLastLevel.size() > 0) {
                rateDimAttr = rateDimAttrWithLastLevel.get(0);
                skuWxString = "bankminsheng" + "tnt001" + rateDimAttr.getDimCode() + rateDimAttr.getDimAttrCode() + "payStyleweixin";
            }
            skuWxString = MD5Util.getMD5String(skuWxString);
            // 根据SKUCode查询费率
            List<RateSku> queryRateSkuList = rateSkuService.queryRateSkuList(null);
            for (RateSku rateSku2 : queryRateSkuList) {
                if (StringUtils.equals(rateSku2.getSkuCode(), skuWxString)) {
                    payChannel.setFixFeeRate(String.valueOf(rateSku2.getSetRate()));
                }
            }
        }

        request.setAttribute("merchant", merchant);
        request.setAttribute("payChannel", payChannel);
        request.setAttribute("merchantTypeList", merchantTypeList);
        return "bindingWXMerchatn";
    }

    /**
     * 显示支付宝绑定页
     *
     * @return
     */
    public String showZFBPage() {
        String groupCode = request.getParameter("groupCode");
        String merchantId = request.getParameter("merchantId") == null ? "0" : request.getParameter("merchantId");
        merchant = new Merchant();
        merchant.setId(Integer.valueOf(merchantId));
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
        payChannel.setGroupCode(groupCode);
        payChannel.setApiCode("0007");
        payChannel.setMerchantCode(merchant.getOutMchntId());
        List<PayChannel> payChannels = payChannelService.queryPayChannelList(payChannel);
        if (payChannels != null && payChannels.size() > 0) {
            payChannel = payChannels.get(0);
        }
        //带入银行开户人
        if (StringUtil.isEmpty(payChannel.getAccount())) {
            //查询银行卡信息
            UserBank bank = new UserBank();
            bank.setMerchantCode(merchant.getOutMchntId());
            List<UserBank> bankList = userBankService.queryUserBankList(bank);
            if (bankList != null && bankList.size() > 0) {
                bank = bankList.get(0);
                payChannel.setAccount(bank.getCardNumber());
                payChannel.setPbcBankId(bank.getBankNumber());

                UserIdinfo idInfo = new UserIdinfo();
                idInfo.setMerchantCode(merchant.getOutMchntId());
                List<UserIdinfo> idInfoList = userIdinfoService.queryUserIdinfoList(idInfo);
                if (idInfoList != null && idInfoList.size() > 0) {
                    payChannel.setAcctName(idInfoList.get(0).getRealName());
                }
            }
        }

        //根据等级带入费率
        MerchantInfo info = new MerchantInfo();
        info.setMerchantCode(merchant.getOutMchntId());
        List<MerchantInfo> infoList = merchantInfoService.queryMerchantInfoList(info);
        if (infoList != null && infoList.size() > 0) {
            String skuSFBString = "";
            RateDimAttr rateDimAttr = new RateDimAttr();
            rateDimAttr.setDimCode("level");// 等级
            rateDimAttr.setDimAttrCode(infoList.get(0).getLevel());
            List<RateDimAttr> rateDimAttrWithLastLevel = rateDimAttrService.queryRateDimAttrWithLastLevel(rateDimAttr);
            if (rateDimAttrWithLastLevel != null && rateDimAttrWithLastLevel.size() > 0) {
                rateDimAttr = rateDimAttrWithLastLevel.get(0);
                skuSFBString = "bankminsheng" + "tnt001" + rateDimAttr.getDimCode() + rateDimAttr.getDimAttrCode() + "payStylezhifubao";
            }
            skuSFBString = MD5Util.getMD5String(skuSFBString);
            // 根据SKUCode查询费率
            List<RateSku> queryRateSkuList = rateSkuService.queryRateSkuList(null);
            for (RateSku rateSku2 : queryRateSkuList) {
                if (StringUtils.equals(rateSku2.getSkuCode(), skuSFBString)) {
                    payChannel.setFixFeeRate(String.valueOf(rateSku2.getSetRate()));
                }
            }
        }

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

        UserBank bank = new UserBank();
        bank.setMerchantCode(payChannel.getMerchantCode());
        bank.setCardNumber(payChannel.getAccount());
        bank.setBankNumber(payChannel.getPbcBankId());

        //String feePay=payChannel.getFixFeeRate();
        if (payChannelList != null && payChannelList.size() > 0) {
            payChannel.setId(payChannelList.get(0).getId());
            payChannelService.updatePayChannel(payChannel);

            userBankService.updateUser(bank);
        } else// 新建
        {
            payChannel.setTxnSeq(txnSeq);
            payChannel.setStatus(0);// 设置状态为未绑定
            payChannel.setCreateTime(new Date());
            payChannel.setApiCode("0005");// 微信编号
            payChannelService.savePayChannel(payChannel);

            // 获取用户信息
            ShiroUser su = queryCurrentShiroUser();
            bank.setAuditStatus(0);
            bank.setCreateUser(su.getLoginName());
            bank.setCreateTime(new Date());
            userBankService.saveUserBank(bank);
        }

        JSONObject json = new JSONObject();
        json.put("result", "SUCCESS");
        this.sendMessages(json.toJSONString());
    }

    /**
     * 绑定 微信到 银行
     *
     * @throws IOException
     */
    public void gotoBindWxMerhant() throws IOException {
        String groupCode = request.getParameter("groupCode");
        String merchantCode = request.getParameter("merchantCode");
        payChannel = new PayChannel();
        payChannel.setGroupCode(groupCode);
        payChannel.setMerchantCode(merchantCode);
        payChannel.setApiCode("0005");
        PayChannel pc = payChannelService.queryPayChannel(payChannel);
        // 调用支付通道
        String gotoBingMerchant = gotoBingMerchant(pc);
        this.sendMessages(gotoBingMerchant);
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
                merchant = new Merchant();
                merchant.setGroupCode(pc.getGroupCode());
                merchant.setOutMchntId(pc.getMerchantCode());
                merchant.setFlag(2);// 将状改为 已绑定
                merchantService.updateMerchantByOutMchntId(merchant);
                // 更新用户信息状态
                MerchantInfo merchantInfo = new MerchantInfo();
                merchantInfo.setMerchantCode(pc.getMerchantCode());
                List<MerchantInfo> queryMerchantInfoList = merchantInfoService.queryMerchantInfoList(merchantInfo);
                if (queryMerchantInfoList != null && queryMerchantInfoList.size() > 0) {
                    //用户认证审核 升级审核
                    merchantInfo = queryMerchantInfoList.get(0);
                    if (merchantInfo.getCertifyStatus() <= 1) {
                        merchantInfo.setCertifyStatus(2);
                        merchantInfoService.updateMerchantInfo(merchantInfo);
                        pushMsgToUser(pc.getMerchantCode(), pc.getAcctName() + "您好！您提交的资料已审核通过。", "审核通过");
                    }
                    // 微信 升级 商户费率
                    MerchantRate merchantRate = new MerchantRate();
                    merchantRate.setMerchantCode(pc.getMerchantCode() + "_temp");
                    if (StringUtils.equals(pc.getApiCode(), "0005")) {
                        merchantRate.setPayChannel("weixin");
                        List<MerchantRate> queryMerchantRateList = merchantRateService.queryMerchantRateList(merchantRate);
                        if (queryMerchantRateList != null && queryMerchantRateList.size() > 0) {
                            //先删除后添加
                            merchantRate.setMerchantCode(pc.getMerchantCode());
                            merchantRateService.deleteMerchantRate(merchantRate);
                            merchantRate = queryMerchantRateList.get(0);
                            merchantRate.setMerchantCode(pc.getMerchantCode());
                            merchantRateService.saveMerchantRate(merchantRate);
                            // 删除临时 数据
                            merchantRate.setMerchantCode(pc.getMerchantCode() + "_temp");
                            merchantRateService.deleteMerchantRate(merchantRate);
                        }
                    }
                    // 支付宝升级 商户费率
                    if (StringUtils.equals(pc.getApiCode(), "0007")) {
                        merchantRate.setPayChannel("zhifubao");
                        List<MerchantRate> queryMerchantRateList = merchantRateService.queryMerchantRateList(merchantRate);
                        if (queryMerchantRateList != null && queryMerchantRateList.size() > 0) {
                            //先删除后添加
                            merchantRate.setMerchantCode(pc.getMerchantCode());
                            merchantRateService.deleteMerchantRate(merchantRate);
                            merchantRate = queryMerchantRateList.get(0);
                            merchantRate.setMerchantCode(pc.getMerchantCode());
                            merchantRateService.saveMerchantRate(merchantRate);
                            // 删除临时 数据
                            merchantRate.setMerchantCode(pc.getMerchantCode() + "_temp");
                            merchantRateService.deleteMerchantRate(merchantRate);
                        }
                    }
                }
                logger.debug("gotoBingMerchant 绑定支付通道成功：");

            } else {
                json.put("resultCode", Constant.RESULT_CODE_FAIL);
                json.put("resultMsg", errorMsg);
            }
        }
        return json.toJSONString();
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

        UserBank bank = new UserBank();
        bank.setMerchantCode(payChannel.getMerchantCode());
        bank.setCardNumber(payChannel.getAccount());
        bank.setBankNumber(payChannel.getPbcBankId());
        if (payChannelList != null && payChannelList.size() > 0) {
            payChannel.setId(payChannelList.get(0).getId());
            payChannelService.updatePayChannel(payChannel);

            userBankService.updateUser(bank);
        } else// 新建
        {
            payChannel.setTxnSeq(txnSeq);
            payChannel.setStatus(0);// 设置状态为未绑定
            payChannel.setCreateTime(new Date());
            payChannel.setApiCode("0007");// 微信编号
            payChannelService.savePayChannel(payChannel);

            // 获取用户信息
            ShiroUser su = queryCurrentShiroUser();
            bank.setAuditStatus(0);
            bank.setCreateUser(su.getLoginName());
            bank.setCreateTime(new Date());
            userBankService.saveUserBank(bank);
        }

        JSONObject json = new JSONObject();
        json.put("result", "SUCCESS");
        this.sendMessages(json.toJSONString());
    }

    /**
     * 获取非直属商户列表
     *
     * @return
     */
    public String notDirectlyMerchantList() {
        // 先获取该代理商下所有代理商
        String select = request.getParameter("select");
        String input = request.getParameter("input");
        String pageIndex = request.getParameter("pageIndex");
        String groupCode = request.getParameter("groupCode");
        Map map = new HashMap();
        map.put("parentCode", groupCode);
        String getGroupCodeList = groupService.queryGetGroupCodes(map);
        String[] GroupCodeList = getGroupCodeList.split(",");
        // 出去自身Code
        List<String> groupList = new ArrayList<String>();
        for (String string : GroupCodeList) {
            if (!StringUtils.equals(string, "$") && !StringUtils.equals(string, groupCode)) {
                groupList.add(string);
            }
        }
        // 判断有没有直属商户
        if (groupList != null && groupList.size() > 0) {
            Integer beginRow = (Integer.parseInt(pageIndex)) * Constant.PAGESIZE_TEN;
            Map mp = new HashMap();
            mp.put("beginRow", beginRow);
            mp.put("pageSize", Constant.PAGESIZE_TEN);
            mp.put("groupCode", groupList);
            // 查询
            if (StringUtils.isNotBlank(select) && StringUtils.isNotBlank(input)) {
                if (StringUtils.equals(select, "Code")) {
                    mp.put("outMchntId", input);
                } else if (StringUtils.equals(select, "Name")) {
                    mp.put("mchntName", input);
                }
            }
            List<Merchant> merchantPage = merchantService.queryMerchantPage(mp);
            // 记录总数
            int totalMerchant = 0;
            totalMerchant = merchantService.queryMerchantPageCount(mp);
            // 求余 获取分页总数
            int totalPage = 0;
            int remainder = totalMerchant % Constant.PAGESIZE_TEN;
            if (remainder == 0) {
                totalPage = totalMerchant / Constant.PAGESIZE_TEN;
            } else {
                totalPage = totalMerchant / Constant.PAGESIZE_TEN + 1;
            }
            // 获取支付通道中该商家绑定了那些通道
            map.put("status", 1);// 1 是绑定，0是为绑定
            List<PayChannel> PayChannel = payChannelService.queryPayChannelByGroupCodes(map);
            // 门店与 支付通道关联
            for (Merchant merchant : merchantPage) {
                boolean flag = false;
                List<PayChannel> payChanneList = new ArrayList<PayChannel>();
                for (PayChannel payChannel : PayChannel) {
                    if (StringUtils.equals(payChannel.getMerchantCode(), merchant.getOutMchntId()) && StringUtils.equals(payChannel.getCmbcMchntId(), merchant.getCmbcMchntId())) {
                        payChanneList.add(payChannel);
                        flag = true;
                    }
                }
                if (flag) {
                    merchant.setPayChannel(payChanneList);
                }
            }
            request.setAttribute("merchantPage", merchantPage);
            request.setAttribute("totalPage", totalPage);
        } else {
            request.setAttribute("merchantPage", null);
            request.setAttribute("totalPage", 0);
        }
        return "notDirectlyMerchantList";
    }

    /***
     * 省市区 回显
     *
     * @param request
     */
    public void queryAreaMassage(HttpServletRequest request, String areaCode, String type) {
        String provinceCode = "";// 省区域编码
        String cityCode = "";// 市区域编码
        String districtCode = "";// 区县编码
        List<Area> provinceList = new ArrayList<Area>();
        List<Area> cityList = new ArrayList<Area>();
        List<Area> districtList = new ArrayList<Area>();

        // 查询 传入Code 信息
        Area area = new Area();
        if (areaCode == null || "".equals(areaCode))
            areaCode = "0";
        area.setCode(areaCode);
        List<Area> areas = areaService.queryAreaList(area);
        if (areas != null && areas.size() > 0) {
            Area currentArea = areas.get(0);
            int level = currentArea.getLevel();

            if (level == 3)// 区县回显
            {
                districtCode = currentArea.getCode();
                cityCode = currentArea.getPcode();
                provinceCode = currentArea.getPcode().substring(0, 2) + "0000";

                // 获取对应市list集合
                area.setPcode(provinceCode);
                area.setLevel(2);
                cityList = areaService.queryAreaListByPcode(area);

                // 获取县区的list集合
                area.setPcode(cityCode);
                area.setLevel(3);
                districtList = areaService.queryAreaListByPcode(area);

            } else if (level == 2)// 市回显
            {
                cityCode = currentArea.getCode();
                provinceCode = currentArea.getPcode();

                // 获取对应市list集合
                area.setPcode(provinceCode);
                area.setLevel(2);
                cityList = areaService.queryAreaListByPcode(area);

                // 获取县区的list集合
                area.setPcode(cityCode);
                area.setLevel(3);
                districtList = areaService.queryAreaListByPcode(area);
            } else if (level == 1) {
                provinceCode = currentArea.getCode();

                // 获取市list集合
                area.setPcode(provinceCode);
                area.setLevel(2);
                cityList = areaService.queryAreaListByPcode(area);
            }
        }

        // 获取省list
        area.setPcode(Constant.CHINA_CODE);
        area.setLevel(1);
        provinceList = areaService.queryAreaListByPcode(area);
        if (StringUtils.equals(type, "1")) {
            request.setAttribute("provinceList", provinceList);
            request.setAttribute("cityList", cityList);
            request.setAttribute("areaList", districtList);

            request.setAttribute("provinceCode", provinceCode);
            request.setAttribute("cityCode", cityCode);
            request.setAttribute("areaCode", districtCode);
        } else {
            request.setAttribute("provinceListBank", provinceList);
            request.setAttribute("cityListBank", cityList);

            request.setAttribute("bankProvince", provinceCode);
            request.setAttribute("bankCity", cityCode);
        }
    }

    /**
     * 查询账号
     *
     * @throws IOException
     */
    public String binMerUser() throws IOException {
        //String groupCode = request.getParameter("groupCode");
        String merchantId = request.getParameter("merchantId");

        UserRelationship p = new UserRelationship();
        if (!StringUtil.isEmpty(merchantId)) {
            Merchant t = new Merchant();
            t.setId(Integer.parseInt(merchantId));
            List<Merchant> list = merchantService.queryMerchantList(t);
            if (list != null && list.size() > 0) {
               // p.setGroupCode(groupCode);
               /* p.setMerchantCode(list.get(0).getOutMchntId());
                List<UserRelationship> userList = userRelationshipService.queryUserRelationshipList(p);
                if (userList != null && userList.size() > 0) {
                    p = userList.get(0);
                }*/
            	p.setGroupCode(list.get(0).getGroupCode());
            	p.setMerchantCode(list.get(0).getOutMchntId());
            	MerchantUser user =new MerchantUser();
				user.setMerchantCode(list.get(0).getOutMchntId());
				List<MerchantUser> userList = merchantUserService.queryMerchantUserList(user);
                if (userList != null && userList.size() > 0) {
                    p.setLoginName(userList.get(0).getLoginName());
                   
                }
            }

        }
        request.setAttribute("user", p);
        return "merUser";
    }

    /**
     * 保存账号
     *
     * @throws IOException
     */
    public void saveUser() throws IOException {
        JSONObject json = new JSONObject();
        String groupCode = request.getParameter("groupCode");
        String merchantCode = request.getParameter("merchantCode");
        String userCode = request.getParameter("userCode");
        String password = request.getParameter("password");

        // 检验账号名称是否重复
        if (checkUserCode(userCode)) {
                // 本地保存
              /*  UserRelationship p = new UserRelationship();
               // p.setGroupCode(groupCode);
                p.setMerchantCode(merchantCode);
                List<UserRelationship> pList = userRelationshipService.queryUserRelationshipList(p);*/
	        	 UserRelationship p = new UserRelationship();
	        	 p.setGroupCode(groupCode);
	             p.setMerchantCode(merchantCode);
             
                MerchantUser u =new MerchantUser();
				u.setMerchantCode(merchantCode);
				List<MerchantUser> pList = merchantUserService.queryMerchantUserList(u);
                String resourcePath = SystemConfigUtil.getSingleProperty("user_server_path").getPropertyValue();
                if (pList != null && pList.size() > 0) {
//                    p.setId(pList.get(0).getId());

                    String url = resourcePath + "/auth!queryUserInfo.do";
                    String bodyContent = "loginName=" + userCode;
                    String str = BapUtil.httpSendMsg(url, bodyContent);
                    JSONObject jsonObject = JSONObject.parseObject(str);
                    Map map = (Map) jsonObject;
                    if ("haveData".equals(map.get("msg").toString())) {
                        List<UserInfo> userInfoList = JSONObject.parseArray(map.get("managerInfo").toString(), UserInfo.class);
                        Long userId = null;
                        if (userInfoList != null && userInfoList.size() > 0) {
                            userId = userInfoList.get(0).getId();
                        }
                        String urlsso = resourcePath + "/auth!updatePassword.do";
                        String bodyContentsso = "id=" + userId + "&password=" + password;
                        String stri = BapUtil.httpSendMsg(urlsso, bodyContentsso);
                        if (stri.contains("SUCCESS")) {
                            json.put("result", Constant.RESULT_CODE_SUCCESS);
                        } else {
                            json.put("result", Constant.RESULT_CODE_FAIL);
                        }
                    }
                } else {
                    String trueName = userCode;
                    String areaCode = "";
                    Merchant g = new Merchant();
                   // g.setGroupCode(groupCode);
                    g.setOutMchntId(merchantCode);
                    List<Merchant> list = merchantService.queryMerchantList(g);
                    if (list != null && list.size() > 0) {
                        trueName = list.get(0).getMchntFullName();
                        areaCode = list.get(0).getAcdCode();
                    }
                    String role = SystemConfigUtil.getSingleProperty("payMerchantRole").getPropertyValue();
                    // 获取用户信息
                    ShiroUser su = queryCurrentShiroUser();
                    // 保存 账号信息
                    String url = resourcePath + "/auth!saveRegisterUser.do";
                    String bodyContent = "loginName=" + userCode + "&password=" + password + "&phone=&trueName=" + trueName + "&status=1&roleIds=" + role + "&creator=" + su.getLoginName() + "&areaCode=" + areaCode;
                    String str = BapUtil.httpSendMsg(url, bodyContent);
                    if (str.contains(Constant.RESULT_CODE_SUCCESS)) {
                        p.setLoginName(userCode);
                        userRelationshipService.saveUserRelationship(p);

                    	MerchantUser user =new MerchantUser();
						user.setLoginName(userCode);
						user.setMerchantCode(merchantCode);
						user.setCreateTime(new Date());
						merchantUserService.saveMerchantUser(user);

                        json.put("result", Constant.RESULT_CODE_SUCCESS);
                    } else {
                        json.put("result", Constant.RESULT_CODE_FAIL);
                    }
                }
        } else {
            json.put("result", "REPEAT");
        }

        this.sendMessages(json.toJSONString());
    }

    /**
     * 保存账号 编辑
     *
     * @throws IOException
     */
    public void saveUserEdit() throws IOException {
        JSONObject json = new JSONObject();
        String groupCode = request.getParameter("groupCode");
        String merchantCode = request.getParameter("merchantCode");
        String userCode = request.getParameter("userCode");
        String password = request.getParameter("password");

        if (!StringUtil.isEmpty(groupCode)) {
            // 本地保存
            UserRelationship p = new UserRelationship();
            p.setGroupCode(groupCode);
            p.setMerchantCode(merchantCode);
            List<UserRelationship> pList = userRelationshipService.queryUserRelationshipList(p);
            p.setLoginName(userCode);
            String resourcePath = SystemConfigUtil.getSingleProperty("user_server_path").getPropertyValue();
            if (pList != null && pList.size() > 0) {
                p.setId(pList.get(0).getId());

                String url = resourcePath + "/auth!queryUserInfo.do";
                String bodyContent = "loginName=" + userCode;
                String str = BapUtil.httpSendMsg(url, bodyContent);
                JSONObject jsonObject = JSONObject.parseObject(str);
                Map map = (Map) jsonObject;
                if ("haveData".equals(map.get("msg").toString())) {
                    List<UserInfo> userInfoList = JSONObject.parseArray(map.get("managerInfo").toString(), UserInfo.class);
                    Long userId = null;
                    if (userInfoList != null && userInfoList.size() > 0) {
                        userId = userInfoList.get(0).getId();
                    }
                    // 修改密码
                    String urlsso = resourcePath + "/auth!updatePassword.do";
                    String bodyContentsso = "id=" + userId + "&password=" + password;
                    String stri = BapUtil.httpSendMsg(urlsso, bodyContentsso);
                    if (stri.contains("SUCCESS")) {
                        json.put("result", Constant.RESULT_CODE_SUCCESS);
                    } else {
                        json.put("result", Constant.RESULT_CODE_FAIL);
                    }
                }
            }
        } else {
            json.put("result", Constant.RESULT_CODE_FAIL);
        }
        this.sendMessages(json.toJSONString());
    }

    /**
     * 检验账号是否重复
     *
     * @param userCode
     * @return
     * @throws IOException
     */
    private boolean checkUserCode(String userCode) throws IOException {
        MerchantUser user = new MerchantUser();
        user.setLoginName(userCode);
        List<MerchantUser> userList = merchantUserService.queryMerchantUserList(user);
        if (userList != null && userList.size() > 0) {
            return false;
        }
        String resourcePath = SystemConfigUtil.getSingleProperty("user_server_path").getPropertyValue();
        String url = resourcePath + "/auth!validateRegisterUser.do";
        String bodyContent = "loginName=" + userCode;
        String str = BapUtil.httpSendMsg(url, bodyContent);
        if ("haveUser".equals(str)) {
            return false;
        } else {
            return true;
        }
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

    /**
     * 获取角色名称
     *
     * @param loginName
     * @return
     */
    protected Collection<String> getRoleNames(String loginName) {
        String authUrl = SystemConfigUtil.getSingleProperty("user_server_path").getPropertyValue();
        Map<String, String> params = new HashMap<String, String>();
        params.put("loginName", loginName);
        Collection<String> permissions = new HashSet<String>();
        String data = rest(authUrl + "/auth!getRoleNames.do", params);
        String[] array = JSON.parseObject(data, String[].class);
        for (int i = 0; array != null && i < array.length; i++) {
            String role = array[i];
            if (org.springframework.util.StringUtils.hasText(role)) {
                permissions.add(role);
            }
        }
        return permissions;
    }

    private String rest(String url, Map<String, String> params) {
        try {
            return new String(HttpUtils.post(url, params), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }


    public RateDimAttrService getRateDimAttrService() {
        return rateDimAttrService;
    }

    public void setRateDimAttrService(RateDimAttrService rateDimAttrService) {
        this.rateDimAttrService = rateDimAttrService;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public UserRelationshipService getUserRelationshipService() {
        return userRelationshipService;
    }

    public void setUserRelationshipService(UserRelationshipService userRelationshipService) {
        this.userRelationshipService = userRelationshipService;
    }

    public MerchantService getMerchantService() {
        return merchantService;
    }

    public void setMerchantService(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    public AreaService getAreaService() {
        return areaService;
    }

    public void setAreaService(AreaService areaService) {
        this.areaService = areaService;
    }

    public WxdicService getWxdicService() {
        return wxdicService;
    }

    public void setWxdicService(WxdicService wxdicService) {
        this.wxdicService = wxdicService;
    }

    public PayChannelService getPayChannelService() {
        return payChannelService;
    }

    public void setPayChannelService(PayChannelService payChannelService) {
        this.payChannelService = payChannelService;
    }

    public PayChannel getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(PayChannel payChannel) {
        this.payChannel = payChannel;
    }

    public ZfbdicService getZfbdicService() {
        return zfbdicService;
    }

    public void setZfbdicService(ZfbdicService zfbdicService) {
        this.zfbdicService = zfbdicService;
    }

    public GroupService getGroupService() {
        return groupService;
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    public MerchantInfoService getMerchantInfoService() {
        return merchantInfoService;
    }

    public void setMerchantInfoService(MerchantInfoService merchantInfoService) {
        this.merchantInfoService = merchantInfoService;
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

    public MerchantRateService getMerchantRateService() {
        return merchantRateService;
    }

    public void setMerchantRateService(MerchantRateService merchantRateService) {
        this.merchantRateService = merchantRateService;
    }

    public UserIdinfoService getUserIdinfoService() {
        return userIdinfoService;
    }

    public void setUserIdinfoService(UserIdinfoService userIdinfoService) {
        this.userIdinfoService = userIdinfoService;
    }

    public UserBankService getUserBankService() {
        return userBankService;
    }

    public void setUserBankService(UserBankService userBankService) {
        this.userBankService = userBankService;
    }

    public GroupMerchantPayService getGroupMerchantPayService() {
        return groupMerchantPayService;
    }

    public void setGroupMerchantPayService(GroupMerchantPayService groupMerchantPayService) {
        this.groupMerchantPayService = groupMerchantPayService;
    }

    public DimDicInfoService getDimDicInfoService() {
        return dimDicInfoService;
    }

    public void setDimDicInfoService(DimDicInfoService dimDicInfoService) {
        this.dimDicInfoService = dimDicInfoService;
    }

    public BankService getBankService() {
        return bankService;
    }

    public void setBankService(BankService bankService) {
        this.bankService = bankService;
    }

    public BankInfoService getBankInfoService() {
        return bankInfoService;
    }

    public void setBankInfoService(BankInfoService bankInfoService) {
        this.bankInfoService = bankInfoService;
    }

    public RateSkuService getRateSkuService() {
        return rateSkuService;
    }

    public void setRateSkuService(RateSkuService rateSkuService) {
        this.rateSkuService = rateSkuService;
    }

    public UserImageService getUserImageService() {
        return userImageService;
    }

    public void setUserImageService(UserImageService userImageService) {
        this.userImageService = userImageService;
    }
}
