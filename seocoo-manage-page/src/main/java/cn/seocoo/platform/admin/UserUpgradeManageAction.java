package cn.seocoo.platform.admin;

import cn.seocoo.platform.common.base.BaseAction;
import cn.seocoo.platform.common.util.BapUtil;
import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.*;
import cn.seocoo.platform.push.AppPushMsg;
import cn.seocoo.platform.service.dimDicInfo.inf.DimDicInfoService;
import cn.seocoo.platform.service.merchantInfo.inf.MerchantInfoService;
import cn.seocoo.platform.service.merchantRate.inf.MerchantRateService;
import cn.seocoo.platform.service.merchantUser.inf.MerchantUserService;
import cn.seocoo.platform.service.pushMsg.inf.PushMsgService;
import cn.seocoo.platform.service.rateDimAttr.inf.RateDimAttrService;
import cn.seocoo.platform.service.rateSku.inf.RateSkuService;
import cn.seocoo.platform.service.userUpgrade.inf.UserUpgradeService;
import com.alibaba.fastjson.JSONObject;
import com.tydic.framework.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;

public class UserUpgradeManageAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private UserUpgradeService userUpgradeService;

    private DimDicInfoService dimDicInfoService;

    private MerchantInfoService merchantInfoService;

    private RateDimAttrService rateDimAttrService;

    private RateSkuService rateSkuService;

    private MerchantRateService merchantRateService;

    private MerchantUserService merchantUserService;

    private PushMsgService pushMsgService;

    public String userUpgrade() {

        return "userUpgrade";
    }

    /**
     * 获取用户升级列表
     *
     * @return
     */
    public String userUpList() {

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
            map.put("applyStatus", select);
        } else {
            map.put("applyStatus", "0,1,2");
        }

        // 手机输入框 没有值则不查询
        if (StringUtils.isNotBlank(input)) {
            map.put("applyUser", input);
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
        List<UserUpgrade> userUpgradePage = userUpgradeService.queryUserUpgradePage(map);
        // 记录总数
        int totalMerchant = 0;
        totalMerchant = userUpgradeService.queryUserUpgradePageCount(map);
        // 求余 获取分页总数
        int totalPage = 0;
        int remainder = totalMerchant % Constant.PAGESIZE_TEN;
        if (remainder == 0) {
            totalPage = totalMerchant / Constant.PAGESIZE_TEN;
        } else {
            totalPage = totalMerchant / Constant.PAGESIZE_TEN + 1;
        }
        // 根据等级CODE 查询 等级名
        DimDicInfo dimDicInfo = new DimDicInfo();
        dimDicInfo.setCode("level");
        List<DimDicInfo> queryDimDicAttrValue = dimDicInfoService.queryDimDicAttrValue(dimDicInfo);
        if (userUpgradePage != null && userUpgradePage.size() > 0) {
            for (UserUpgrade userUpgrade : userUpgradePage) {
                for (DimDicInfo dimDicInfo2 : queryDimDicAttrValue) {
                    if (StringUtils.equals(dimDicInfo2.getAttrCode(), userUpgrade.getFromLevelCode())) {
                        userUpgrade.setFromLevelName(dimDicInfo2.getAttrName());
                    }
                    if (StringUtils.equals(dimDicInfo2.getAttrCode(), userUpgrade.getToLevelCode())) {
                        userUpgrade.setToLevelName(dimDicInfo2.getAttrName());
                    }
                }
            }
        }

        request.setAttribute("userUpgradeList", userUpgradePage);
        request.setAttribute("totalPage", totalPage);

        return "userUpList";
    }

    /**
     * 显示 用户等级升级 页面
     *
     * @return
     */
    public String auditUserUpgrade() {
        String applyCode = request.getParameter("applyCode");

        // 获取升级信息
        UserUpgrade userUpgrade = new UserUpgrade();
        userUpgrade.setApplyCode(applyCode);
        List<UserUpgrade> queryUserUpgradeList = userUpgradeService.queryUserUpgradeList(userUpgrade);
        if (queryUserUpgradeList != null && queryUserUpgradeList.size() > 0) {
            userUpgrade = queryUserUpgradeList.get(0);
        }
        // 根据等级CODE 查询 等级名
        DimDicInfo dimDicInfo = new DimDicInfo();
        dimDicInfo.setCode("level");
        List<DimDicInfo> queryDimDicAttrValue = dimDicInfoService.queryDimDicAttrValue(dimDicInfo);

        for (DimDicInfo dimDicInfo2 : queryDimDicAttrValue) {
            if (StringUtils.equals(dimDicInfo2.getAttrCode(), userUpgrade.getFromLevelCode())) {
                userUpgrade.setFromLevelName(dimDicInfo2.getAttrName());
            }
            if (StringUtils.equals(dimDicInfo2.getAttrCode(), userUpgrade.getToLevelCode())) {
                userUpgrade.setToLevelName(dimDicInfo2.getAttrName());
            }
        }

        // 获取商户 发展的 会员
        MerchantInfo merchantInfo = new MerchantInfo();
        merchantInfo.setCreateUser(userUpgrade.getApplyUser());
        List<MerchantInfo> merchantInfos = merchantInfoService.queryMerchantInfoList(merchantInfo);
        if (merchantInfos != null && merchantInfos.size() > 0) {
            merchantInfo = merchantInfos.get(0);
        }
        MerchantInfo mi = new MerchantInfo();
        mi.setParentMerchantCode(merchantInfo.getMerchantCode());
        mi.setCertifyStatus(2);// 管理员升级 以 资料 审核通过的为主
        List<MerchantInfo> merchantInfoList = merchantInfoService.queryMerchantInfoList(mi);

        int totalCount = 0;
        for (DimDicInfo dimDicInfo2 : queryDimDicAttrValue) {
            int count = 0;
            for (MerchantInfo merchantInfo2 : merchantInfoList) {
                // 匹配 升级编码 不包含 自身
                if (StringUtils.equals(dimDicInfo2.getAttrCode(), merchantInfo2.getLevel())) {
                    count++;
                    totalCount++;
                }
            }
            dimDicInfo2.setCount(count);
        }
        request.setAttribute("dimDicAttrValueList", queryDimDicAttrValue);
        request.setAttribute("userUpgrade", userUpgrade);
        request.setAttribute("totalCount", totalCount);
        return "auditUserUpgrade";
    }

    /**
     * 升级审核成功
     *
     * @throws IOException
     */
    public void auditUserUpgradeSuccess() throws IOException {
        String applyCode = request.getParameter("applyCode");
        String merchantCode = request.getParameter("merchantCode");
        // 获取升级用户的信息
        UserUpgrade userUpgrade = new UserUpgrade();
        userUpgrade.setApplyCode(applyCode);
        List<UserUpgrade> queryUserUpgradeList = userUpgradeService.queryUserUpgradeList(userUpgrade);
        JSONObject json = new JSONObject();
        if (queryUserUpgradeList != null && queryUserUpgradeList.size() > 0) {
            userUpgrade = queryUserUpgradeList.get(0);
            // 保存最低费率 --获取最底等级
            String skuWxString = "";
            String skuSFBString = "";
            RateDimAttr rateDimAttr = new RateDimAttr();
            rateDimAttr.setDimCode("level");// 等级
            rateDimAttr.setDimAttrCode(userUpgrade.getToLevelCode());
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
            try {
                // 先删除费率表
                MerchantRate merchantRate = new MerchantRate();
                merchantRate.setMerchantCode(merchantCode + "_temp");
                merchantRateService.deleteMerchantRate(merchantRate);
                // 批量添加
                merchantRateService.batchInsertMerchantRate(merchantRateList);

                json.put("resultCode", Constant.RESULT_CODE_SUCCESS);
                userUpgrade.setApplyStatus(1);
                userUpgradeService.updateUserUpgrade(userUpgrade);
            } catch (Exception e) {
                e.printStackTrace();
                json.put("resultCode", Constant.RESULT_CODE_FAIL);
            }
        }
        this.sendMessages(json.toJSONString());
    }

    /**
     * 升级审核失败
     *
     * @throws IOException
     */
    public void auditUserUpgradeFail() throws IOException {
        String applyCode = request.getParameter("applyCode");
        String merchantCode = request.getParameter("merchantCode");
        String failMsg = request.getParameter("failMsg");
        UserUpgrade userUpgrade = new UserUpgrade();
        userUpgrade.setApplyCode(applyCode);
        userUpgrade.setApplyStatus(-1);// 未通过
        JSONObject json = new JSONObject();
        try {
            userUpgradeService.updateUserUpgrade(userUpgrade);
            new Thread(new countProfitThread(merchantCode, failMsg, "升级失败")).start();
            json.put("resultCode", Constant.RESULT_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("resultCode", Constant.RESULT_CODE_FAIL);
        }
        this.sendMessages(json.toJSONString());
    }

    /**
     * 完成用户升级
     *
     * @throws IOException
     */
    public void finishUserUpgrade() throws IOException {
        String applyCode = request.getParameter("applyCode");
        String merchantCode = request.getParameter("merchantCode");
        UserUpgrade userUpgrade = new UserUpgrade();
        userUpgrade.setApplyCode(applyCode);
        List<UserUpgrade> queryUserUpgradeList = userUpgradeService.queryUserUpgradeList(userUpgrade);
        if (queryUserUpgradeList != null && queryUserUpgradeList.size() > 0) {
            userUpgrade = queryUserUpgradeList.get(0);
        }

        // 根据等级CODE 查询 等级名
        DimDicInfo dimDicInfo = new DimDicInfo();
        dimDicInfo.setCode("level");
        dimDicInfo.setAttrCode(userUpgrade.getToLevelCode());
        List<DimDicInfo> queryDimDicAttrValue = dimDicInfoService.queryDimDicAttrValue(dimDicInfo);
        if (queryDimDicAttrValue != null && queryDimDicAttrValue.size() > 0) {
            dimDicInfo = queryDimDicAttrValue.get(0);
        }
        userUpgrade.setApplyStatus(2);// 完成
        userUpgrade.setFinishTime(new Date());
        JSONObject json = new JSONObject();
        try {
            // 更改等级
            MerchantInfo merchantInfo = new MerchantInfo();
            merchantInfo.setMerchantCode(merchantCode);
            merchantInfo.setLevel(userUpgrade.getToLevelCode());
            merchantInfoService.updateMerchantInfo(merchantInfo);
            userUpgradeService.updateUserUpgrade(userUpgrade);
            new Thread(new countProfitThread(merchantCode, (userUpgrade.getMerchantName() == null ? "" : userUpgrade.getMerchantName()) + "您好！您已经升级为" + dimDicInfo.getAttrName(), "升级成功")).start();
            //删除临时数据
            MerchantRate merchantRate = new MerchantRate();
            merchantRate.setMerchantCode(merchantCode + "_temp");
            merchantRateService.deleteMerchantRate(merchantRate);
            json.put("resultCode", Constant.RESULT_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("resultCode", Constant.RESULT_CODE_FAIL);
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
                e.printStackTrace();
                pushLogger.debug("========>countProfit service error!");
            }
        }
    }

    public UserUpgradeService getUserUpgradeService() {
        return userUpgradeService;
    }

    public void setUserUpgradeService(UserUpgradeService userUpgradeService) {
        this.userUpgradeService = userUpgradeService;
    }

    public DimDicInfoService getDimDicInfoService() {
        return dimDicInfoService;
    }

    public void setDimDicInfoService(DimDicInfoService dimDicInfoService) {
        this.dimDicInfoService = dimDicInfoService;
    }

    public MerchantInfoService getMerchantInfoService() {
        return merchantInfoService;
    }

    public void setMerchantInfoService(MerchantInfoService merchantInfoService) {
        this.merchantInfoService = merchantInfoService;
    }

    public RateDimAttrService getRateDimAttrService() {
        return rateDimAttrService;
    }

    public void setRateDimAttrService(RateDimAttrService rateDimAttrService) {
        this.rateDimAttrService = rateDimAttrService;
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
