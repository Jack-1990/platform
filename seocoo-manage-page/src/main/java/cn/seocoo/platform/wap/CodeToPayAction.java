package cn.seocoo.platform.wap;

import cn.seocoo.platform.common.base.BaseAction;
import cn.seocoo.platform.common.util.BapUtil;
import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.MerchantUser;
import cn.seocoo.platform.model.Order;
import cn.seocoo.platform.model.PushMsg;
import cn.seocoo.platform.push.AppPushMsg;
import cn.seocoo.platform.service.merchantUser.inf.MerchantUserService;
import cn.seocoo.platform.service.pushMsg.inf.PushMsgService;
import cn.seocoo.platform.unite.Result;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 扫描二维码 进入支付页面
 */
public class CodeToPayAction extends BaseAction {

    private final Logger codeToPayActionLogger = Logger.getLogger(CodeToPayAction.class);
    private MerchantUserService merchantUserService;

    private PushMsgService pushMsgService;
    /**
     * 显示 付款页面
     *
     * @return
     */
    public String codeToPay() {
        // 获取当前角色
        String merchantCode = request.getParameter("merchantCode");

        request.setAttribute("merchantCode", merchantCode);
        return "codeToPay";
    }

    /**
     * 生成 支付二维码
     */
    public String payCode() throws IOException {
        String selectTradeType = request.getParameter("type");
        String merchantCode = request.getParameter("merchantCode");
        Double amount = request.getParameter("amount") == "" ? 0.0 : Double.valueOf(request.getParameter("amount")) * 100;
        Result result = makeQRCode(selectTradeType, merchantCode, amount);
        request.setAttribute("merchantCode", merchantCode);
        if ("SUCCESS".equals(result.getResultCode())) {
            request.setAttribute("amount", amount / 100);
            request.setAttribute("type", selectTradeType);
            request.setAttribute("merchantCode", merchantCode);
            request.setAttribute("resultMsg", result.getResultData());
            return "payCode";
        } else {
            return "codeToPay";
        }
    }

    /**
     * 生成二维码
     *
     * @return
     */
    public Result makeQRCode(String selectTradeType, String merchantCode, Double amount) {
        Order order = new Order();
        order.setAmount(amount.intValue() + "");
        order.setMerchantCode(merchantCode);
        order.setScanStyle(0);
        order.setSelectTradeType(selectTradeType);
        String param = JSONObject.toJSONString(order);
        String message = BapUtil.generateRequestMessage(param, "submitOrder");
        String url = SystemConfigUtil.getSingleProperty("unite_interface_url").getPropertyValue();
        String res = BapUtil.httpSendJson(url, message);
        Result result = BapUtil.parseRes(res);

        return result;
    }

    /**
     * 查询 支付结果
     */
    public void getOrderMsg() throws IOException {
        String merchantCode = request.getParameter("merchantCode");
        String orderNumber = request.getParameter("orderNumber");
        Double amount = request.getParameter("amount") == "" ? 0.0 : Double.valueOf(request.getParameter("amount")) * 100;
        String selectTradeType = request.getParameter("type");
        Order order = new Order();
        order.setMerchantCode(merchantCode);
        order.setOrderNumber(orderNumber);
        String param = JSONObject.toJSONString(order);
        String message = BapUtil.generateRequestMessage(param, "queryPayResult");
        String url = SystemConfigUtil.getSingleProperty("unite_interface_url").getPropertyValue();
        String res = BapUtil.httpSendJson(url, message);
        Result result = BapUtil.parseRes(res);
        JSONObject jsonObject = JSONObject.parseObject(result.getResultData().toString());
        String status = jsonObject.get("status").toString();
        JSONObject json = new JSONObject();
        json.put("resultCode", Constant.RESULT_CODE_SUCCESS);
        if ("SUCCESS".equals(result.getResultCode())) {
            if (StringUtils.equals(status, "1")) {
                new Thread(new countProfitThread(merchantCode, "您的订单号:"+orderNumber+"已经支付成功！", "支付成功")).start();
                json.put("resultCode", Constant.RESULT_CODE_SUCCESS);
                json.put("resultMsg", result.getResultMsg());
            } else {
                json.put("resultCode", Constant.RESULT_CODE_FAIL);
                json.put("resultMsg", result.getResultMsg());
            }
        } else {
            json.put("resultCode", Constant.RESULT_CODE_FAIL);
            json.put("resultMsg", result.getResultMsg());
        }


        //    JSONObject jsonObject = JSONObject.parseObject(result.getResultData().toString());
        //    String status = jsonObject.get("status").toString();
        //    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //    String dateString = formatter.format(new Date());
        //    //判断当前订单 是否完成 1、完成订单
        //    if(StringUtils.equals(status,"1")){
        //
        //        request.setAttribute("merchantCode", merchantCode);
        //        request.setAttribute("resultMsg",result.getResultData());
        //        request.setAttribute("dateString",dateString);
        //        return "paySuccess";
        //    }else {
        //        //没有完成的订单 则 反复
        //        Result re = makeQRCode(selectTradeType, merchantCode, amount);
        //        if ("SUCCESS".equals(result.getResultCode())){
        //            request.setAttribute("amount",amount/100);
        //            request.setAttribute("type", selectTradeType);
        //            request.setAttribute("merchantCode", merchantCode);
        //            request.setAttribute("resultMsg",re.getResultData());
        //            return "payCode";
        //        }else{
        //            return "codeToPay";
        //        }
        //    }
        //
        //}else{
        //    //没有完成的订单 则 反复
        //    Result re = makeQRCode(selectTradeType, merchantCode, amount);
        //    if ("SUCCESS".equals(result.getResultCode())){
        //        request.setAttribute("amount",amount/100);
        //        request.setAttribute("type", selectTradeType);
        //        request.setAttribute("merchantCode", merchantCode);
        //        request.setAttribute("resultMsg",re.getResultData());
        //        return "payCode";
        //    }else{
        //        return "codeToPay";
        //    }
        this.sendMessages(json.toJSONString());
    }

    /**
     * 支付成功后返回 成功页面
     *
     * @return
     */
    public String resultSuccess() {
        String merchantCode = request.getParameter("merchantCode");
        String orderNumber = request.getParameter("orderNumber");
        Double amount = request.getParameter("amount") == "" ? 0.0 : Double.valueOf(request.getParameter("amount")) * 100;
        String selectTradeType = request.getParameter("type");
        Order order = new Order();
        order.setMerchantCode(merchantCode);
        order.setOrderNumber(orderNumber);
        String param = JSONObject.toJSONString(order);
        String message = BapUtil.generateRequestMessage(param, "queryPayResult");
        String url = SystemConfigUtil.getSingleProperty("unite_interface_url").getPropertyValue();
        String res = BapUtil.httpSendJson(url, message);
        Result result = BapUtil.parseRes(res);
        JSONObject jsonObject = JSONObject.parseObject(result.getResultData().toString());
        Long finnishTime = jsonObject.get("finnishTime").toString() == null ? 0 : Long.valueOf(jsonObject.get("finnishTime").toString());
        int status = jsonObject.get("status") == null ? 0 : Integer.valueOf(jsonObject.get("status").toString());

        if ("SUCCESS".equals(result.getResultCode())) {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(finnishTime);
            Date dateString = c.getTime();
            // 当 状态已支付则 发送消息
            if (status == 1) {
                //Object orderNumber1 = jsonObject.get("orderNumber");
                //new Thread(new countProfitThread(merchantCode, "您的订单号:"+orderNumber1+"已经支付成功！", "支付成功"));
            }

            request.setAttribute("merchantCode", merchantCode);
            request.setAttribute("resultMsg", result.getResultData());
            request.setAttribute("dateString", dateString);
        }
        return "paySuccess";
    }


    /**
     * 判断支付宝 是否付款
     */
    public void getZFBOrderStuts() throws IOException {

        String merchantCode = request.getParameter("merchantCode");
        String orderNumber = request.getParameter("orderNumber");
        codeToPayActionLogger.debug("======================>getZFBOrderStuts thread  in =========================="+merchantCode);
        new Thread(new checkOrderStuts(merchantCode,orderNumber)).start();
        JSONObject json = new JSONObject();
        json.put("resultCode", Constant.RESULT_CODE_SUCCESS);
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
        private final Logger pushLogger = Logger.getLogger(CodeToPayAction.class);
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

    class checkOrderStuts implements Runnable {
        private final Logger pushLogger = Logger.getLogger(CodeToPayAction.class);
        private String merchantCode = null;
        private String orderNumber = null;
        public checkOrderStuts(String merchantCode, String orderNumber) {
            this.merchantCode = merchantCode;
            this.orderNumber = orderNumber;
        }
        @Override
        public void run() {
            boolean flag = true ;
            int count = 0 ;
            pushLogger.debug("======================>checkOrderStuts thread  in ==========================");
            while(flag){
                Order order = new Order();
                order.setMerchantCode(merchantCode);
                order.setOrderNumber(orderNumber);
                String param = JSONObject.toJSONString(order);
                String message = BapUtil.generateRequestMessage(param, "queryPayResult");
                String url = SystemConfigUtil.getSingleProperty("unite_interface_url").getPropertyValue();
                String res = BapUtil.httpSendJson(url, message);
                Result result = BapUtil.parseRes(res);
                JSONObject jsonObject = JSONObject.parseObject(result.getResultData().toString());
                String status = jsonObject.get("status").toString();
                JSONObject json = new JSONObject();
                json.put("resultCode", Constant.RESULT_CODE_SUCCESS);
                if ("SUCCESS".equals(result.getResultCode())) {
                    if (StringUtils.equals(status, "1")) {
                        new Thread(new countProfitThread(merchantCode, "您的订单号:"+orderNumber+"已经支付成功！", "支付成功")).start();
                        flag = false;
                    }
                }
                try {
                    Thread.sleep(3000);
                    if (count >60){
                        flag = false;
                    }
                    count ++ ;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
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
