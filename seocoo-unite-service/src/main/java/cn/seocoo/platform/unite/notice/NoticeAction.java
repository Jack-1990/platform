package cn.seocoo.platform.unite.notice;

 

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.odchina.micro.util.PayCoreUtil;

import cn.seocoo.platform.common.base.BaseAction;
import cn.seocoo.platform.common.util.BapUtil;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.Order;
import cn.seocoo.platform.msbank.NoticeMsg;
import cn.seocoo.platform.msbank.ResponseMsg;
import cn.seocoo.platform.service.order.inf.OrderService;

public class NoticeAction extends BaseAction{
	private static final Logger logger = Logger.getLogger(NoticeAction.class);
	private OrderService orderService;
	
	/**
	 * 接收到异步通知后的信息处理
	 * @param request  带有异步通知内容的请求对象
	 * @param response  向异步通知响应的响应对象
	 * @throws Exception
	 */
	public void acceptNoticeContext() throws Exception {
		logger.info("================asynNotice=======start==========");
	    // 接收异步通知的关键代码
	    StringBuffer noticeContext = new StringBuffer();
	    BufferedReader reader = request.getReader();
	    String data = null;
	    while ((data = reader.readLine()) != null) {
	        noticeContext.append(data);
	    }
	    logger.info("result="+noticeContext.toString());
	    
	    String msg=noticeContext.toString();//notify_url返回的json字符串
	    logger.info("================asynNotice=======msg="+msg);
	    if(msg!=null&&!"".equals(msg))
	    {
	    	Gson gson = new Gson();
			Map<String, Object> paraMap = gson.fromJson(msg, Map.class);
			String context = paraMap.get("context").toString();
			String dncryptContext = PayCoreUtil.dncrypt(context);
			
			 ResponseMsg rm=JSONObject.parseObject(dncryptContext, ResponseMsg.class);
			 String body = rm.getBody();
			 logger.info("acceptNoticeContext 最终结果：");
			 logger.info(body);
				
			 String signChkResult = PayCoreUtil.signCheck(dncryptContext);
			 logger.info("-----acceptNoticeContext---------------------------------");
			 logger.info("验证签名结果：");
			 logger.info(signChkResult);
			 
			 if("验签通过".equals(signChkResult)){
				 NoticeMsg nm=JSONObject.parseObject(body, NoticeMsg.class);
				 String tradeStatus=nm.getTradeStatus();
				 if("S".equals(tradeStatus)){
					 String orderNo=nm.getOrderNo();
					 String merchantNo=nm.getMerchantNo();
					 Order order=new Order();
					 order.setMerchantSeq(orderNo);
					 order.setMerchantNo(merchantNo);
					 order.setStatus(1);//订单已支付状态
					 order.setFinnishTime(new Date());//订单完成时间
					 
					 order.setBankTradeNo(nm.getBankTradeNo());//银行流水号
					 order.setCardNo(nm.getCardNo());//卡号 (前六后四中间*)
					 order.setCbCode(nm.getCbCode());//发卡行行号
					 order.setCardName(nm.getCardName());//发卡行行名
					 order.setCenterInfo(nm.getCenterInfo());//其他信息
					 order.setSerialNo(nm.getSerialNo());//收单流水号
					 order.setVoucherNo(nm.getVoucherNo());// 收单凭证号
					 
					 orderService.updateOrderByMno(order);
					 
				     // 给通知返回结果的输出流
				     PrintWriter out = response.getWriter();
				     // 此处对通知密文进行解密，通过后返回字符串”SUCCESS”，否则返回”E” 
				     try {
				        out.print("SUCCESS");
				        out.close();
				        logger.info("================asynNotice=======end==========");
				      } catch (Exception e){
				        e.printStackTrace();
				      }
				     
				     //计算分润
				    // countProfit(orderNo,merchantNo);
				 	new Thread(new countProfitThread(orderNo,merchantNo)).start();
					logger.debug("======================>countProfit thread  start ==========================");
				 }
			 }	
	    }
	}
	
	
	class countProfitThread  implements Runnable {
		private  final Logger pushLogger = Logger.getLogger(NoticeAction.class);
		private String  orderNo =null;
		private String  merchantNo =null;
		
		public countProfitThread(String  orderNo,String  merchantNo) {
			this.orderNo = orderNo;
			this.merchantNo=merchantNo;		
		}
 
		public void run() {
			try {
				pushLogger.debug("======================>countProfit thread  in ==========================");
				countProfit(orderNo, merchantNo);
			} catch (Exception e) {
				pushLogger.debug("========>countProfit service error!");
			}
		}
	}
	
	

	/**
	 * 计算给定订单分润信息的接口
	 */
	public void countProfit(String orderNo,String merchantNo){
		
		 Order order=new Order();
		 order.setMerchantSeq(orderNo);
		 order.setMerchantNo(merchantNo);
		 order=orderService.queryOrder(order);
		
		Order newOrder=new Order();
		newOrder.setOrderNumber(order.getOrderNumber());
		newOrder.setAmount(order.getAmount());
		newOrder.setMerchantCode(order.getMerchantCode());
		newOrder.setPayChannel(order.getPayChannel());
		
		
		String param = JSONObject.toJSONString(newOrder);
		String msg = BapUtil.generateRequestMessage(param,"countPayProfit");
		String unite_url=SystemConfigUtil.getSingleProperty("unite_interface_url").getPropertyValue();
		       //push_url="http://127.0.0.1:8092/push/service/home.do";
		BapUtil.httpSendJson(unite_url, msg);
	}	

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	
}
