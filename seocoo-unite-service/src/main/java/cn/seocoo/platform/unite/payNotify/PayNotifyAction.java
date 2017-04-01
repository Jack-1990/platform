package cn.seocoo.platform.unite.payNotify;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.odchina.micro.util.StringTools;
import com.tydic.framework.base.exception.ServiceException;
import com.tydic.framework.util.date.DateUtil;

import cn.seocoo.platform.common.alipay.config.AlipayConfig;
import cn.seocoo.platform.common.alipay.util.AlipayNotify;
import cn.seocoo.platform.common.base.BaseAction;
import cn.seocoo.platform.common.util.CharsetUtil;
import cn.seocoo.platform.model.Upgrade;
import cn.seocoo.platform.model.UserIdinfo;
import cn.seocoo.platform.model.UserUpgrade;
import cn.seocoo.platform.model.UserUpgradeOrder;
import cn.seocoo.platform.service.upgrade.inf.UpgradeService;
import cn.seocoo.platform.service.userIdinfo.inf.UserIdinfoService;
import cn.seocoo.platform.service.userUpgrade.inf.UserUpgradeService;
import cn.seocoo.platform.service.userUpgradeOrder.inf.UserUpgradeOrderService;
 

public class PayNotifyAction extends BaseAction {

	private static final String TRADE_FINISHED = "TRADE_FINISHED"; // 交易成功且结束，即不可再做任何操作
	private static final String TRADE_SUCCESS = "TRADE_SUCCESS"; // 交易成功，且可对该交易做操作，如：多级分润、退款等。
	private static final String RSP_SUCCESS = "success"; // 响应处理成功
	private static final String RSP_FAIL = "fail"; // 响应处理失败
	private static Logger log = Logger.getLogger(PayNotifyAction.class);
	private UserUpgradeOrderService userUpgradeOrderService;
	private UpgradeService upgradeService;
	private UserUpgradeService userUpgradeService;
	private UserIdinfoService  userIdinfoService;
	
	/** 
	 * 支付宝服务器异步通知
	 */
	public void alipayNotify_load_pass() throws ServiceException {
		try {
			
			String req_url = request.getScheme() + "://";
			req_url += request.getHeader("host");
			req_url += request.getRequestURI();
	        if (request.getQueryString() != null){	        	
	        	req_url += "?" + request.getQueryString();
	        }
			log.info("===================支付宝请求回调url=====================" + req_url);
			//获取支付宝POST过来反馈信息
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				params.put(name, valueStr);
			}
			log.info("-=====================获取支付宝POST过来反馈信息=============" + JSON.toJSONString(params));
			
			if (verifyParam(params,request)) {//参数合法
				log.info("==================支付宝回调参数验证通过=====================");
				//商户订单号
				String out_trade_no = CharsetUtil.changeCharset(request.getParameter("out_trade_no"), CharsetUtil.ISO_8859_1, CharsetUtil.UTF_8);
				//支付宝交易号
				String trade_no = CharsetUtil.changeCharset(request.getParameter("trade_no"), CharsetUtil.ISO_8859_1, CharsetUtil.UTF_8);
				//通知时间
				String notify_time = CharsetUtil.changeCharset(request.getParameter("notify_time"), CharsetUtil.ISO_8859_1, CharsetUtil.UTF_8);
				log.info("===============订单号================ " + out_trade_no);
				
				//修改订单状态和支付完成的相关信息
				UserUpgradeOrder order=new UserUpgradeOrder();
				order.setOrderNumber(out_trade_no);
				order.setStatus("success");
				order.setCallbackDate(DateUtil.getCurrentDate());
				order.setPaydate(DateUtil.getDateFormat(notify_time));
				order.setTransSeq(trade_no);
				userUpgradeOrderService.updateOrderByOrderNumber(order);
				
				//生成升级申请单
				applyUpgradeOrder(out_trade_no);
				
				
				// 反馈结果给支付宝
				sendMessages(RSP_SUCCESS);
			} else {
				log.info("==================支付宝回调参数验证不通过=====================");
				// 反馈结果给支付宝
				sendMessages(RSP_FAIL);
			}
		} catch (Exception e) {
			log.error("支付宝服务器支付异步通知错误", e);
		}
	}
	
	
	/**
	 * 根据支付完成结果生成升级申请单
	 * @param orderNumber
	 */
	public void  applyUpgradeOrder(String orderNumber){
		UserUpgradeOrder order=new UserUpgradeOrder();
		order.setOrderNumber(orderNumber);
		List<UserUpgradeOrder> upOrders=userUpgradeOrderService.queryUserUpgradeOrderList(order);
		
		if (upOrders != null && upOrders.size()>0) {
			order=upOrders.get(0);
			
			Integer upGrade_id=order.getUpGrade_id();
			String merchantCode=order.getMerchantCode();
			String applyUser=order.getOrderUser();
			Date duesTime=order.getOrderDate();
			
			Upgrade grade=new Upgrade();
			grade.setId(upGrade_id);
			grade=upgradeService.queryUpgrade(grade);
			log.info("============grade_id============"+upGrade_id);
			if(grade!=null){
			  UserUpgrade userUpgrade=new UserUpgrade();
			  String applyCode=StringTools.getRandomString(10);
			  userUpgrade.setApplyCode(applyCode);
			  userUpgrade.setFromLevelCode(grade.getFromLevelCode());
			  userUpgrade.setToLevelCode(grade.getToLevelCode());
			  userUpgrade.setAttrValue(grade.getAttrValue());
			  userUpgrade.setAttrStyle(grade.getAttrStyle());
			  userUpgrade.setMerchantCode(merchantCode);
			  userUpgrade.setDescription(grade.getDescription());
			  userUpgrade.setApplyUser(applyUser);
			  userUpgrade.setApplyTime(new Date());
			  userUpgrade.setApplyStatus(0);
			  userUpgrade.setDuesTime(duesTime);
			  userUpgrade.setOrderNumber(orderNumber);
			  
			  //获取商户名称
			  UserIdinfo idInfo=new UserIdinfo();
			  idInfo.setMerchantCode(merchantCode);
			  List<UserIdinfo> idInfos=userIdinfoService.queryUserIdinfoList(idInfo);
			  if(idInfos!=null&&idInfos.size()>0){
				  userUpgrade.setMerchantName(idInfos.get(0).getRealName());
			  }
			  
              userUpgradeService.saveUserUpgrade(userUpgrade);
			}
		}
	} 
	

	 
	public boolean verifyParam(Map<String,String> params, HttpServletRequest request){
		try {
			//验证是否是支付宝请求参数
			log.info("===========支付宝参数验证：============" + AlipayNotify.verify(params));
			//商户订单号
			String out_trade_no = CharsetUtil.changeCharset(request.getParameter("out_trade_no"), CharsetUtil.ISO_8859_1, CharsetUtil.UTF_8);
			//交易状态
			String trade_status = CharsetUtil.changeCharset(request.getParameter("trade_status"), CharsetUtil.ISO_8859_1, CharsetUtil.UTF_8);
			//订单金额
			String total_fee = CharsetUtil.changeCharset(request.getParameter("total_fee"), CharsetUtil.ISO_8859_1, CharsetUtil.UTF_8);
			
			UserUpgradeOrder order=new UserUpgradeOrder();
			order.setOrderNumber(out_trade_no);
			List<UserUpgradeOrder> upOrders=userUpgradeOrderService.queryUserUpgradeOrderList(order);
			
			String totalPrice = "";
			if (upOrders != null && upOrders.size()>0) {
				totalPrice = String.valueOf(upOrders.get(0).getOrderPrice());
			}
			
			String privateKey="MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKzW+wJJqLK/2fp9hzaF/dDphmR4qf+rcMagdWVc9GQUIKRPumcQkezgqxRrQprcuDHldHthBYQxV6zWRbrxKG9MCuPsMEj3ByFs/3eSDzQ5yTB5OJBoM0pxAOhg+eB9rblv+yyWxzot9dGpB2uePANDKYpRY9j1fnFkW0d3/O/lAgMBAAECgYBiI39LAUAEnuLKVFReJR7FbJOeoNUGEvZqEdoWv/0UhkkygwK4WrRA6CC761taS1FbI4pMM/7J3KqHvPLC/wORn4mjlCR8DV7pn8+fACCAFj9aaRGa5lXca9YAU/uxHWfAePLh2EjFH1PnBYu8cG2lm6Pf9hQYI4HyoFaiMYPYAQJBANgFYDNAhnk8NjyiSLm2KNpFuFr6nget0HoGzU0fGgdTwIjPNTPoZI5OAKDx+mChm4XovG65n04C902bdpHUI2UCQQDM08SgCEblL1a88eYBtfikVwXqXtS9lQFQa900E/pFayFupbbcus3AuaHM+882p31jGA2FZ/JUjmUFmM46khKBAkEAtwKG56TIyDkMsf3CoyMCJTlf4CPmchb9QgQ1NhsdUAvSV5VEO3+sgSrwOWoHdoozWhU8Xon/vnWg0izdHNqeSQJBALKjx4tjbKGaReYIe3fmg3KhS1F7X8Pw3vKLAKPZAJ/mrYPZF8EvUx/RRuKGg9TBA0SXx8MgQ2OnxUe6W7MNjwECQQCxD5gqsNnh+9esThG8HoarKom7TdX20nfU6P3MKfVRY0BbwqUsVOVYgLQ7Y/YxBen3L3RKrHfvXYVTCmSG6hU1";//支付宝商户秘钥
			String partnerId="2088412523706213";//支付宝商户 id
		 
			//为支付验证核心配置类赋值
			AlipayConfig.partner = partnerId;
			AlipayConfig.private_key = privateKey;
			
			if (AlipayNotify.verify(params)) {
				if (TRADE_SUCCESS.equals(trade_status)) {//交易成功
					//根据支付宝回调信息的订单号，查找本系统订单信息，比对两者的交易金额
					if (totalPrice.equals(total_fee)) {//金额相等
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			log.error("支付宝服务器支付异步通知参数验证错误", e);
		}
		return false;
	}


	public UserUpgradeOrderService getUserUpgradeOrderService() {
		return userUpgradeOrderService;
	}

	public void setUserUpgradeOrderService(UserUpgradeOrderService userUpgradeOrderService) {
		this.userUpgradeOrderService = userUpgradeOrderService;
	}

	public UpgradeService getUpgradeService() {
		return upgradeService;
	}

	public void setUpgradeService(UpgradeService upgradeService) {
		this.upgradeService = upgradeService;
	}

	public UserUpgradeService getUserUpgradeService() {
		return userUpgradeService;
	}

	public void setUserUpgradeService(UserUpgradeService userUpgradeService) {
		this.userUpgradeService = userUpgradeService;
	}

	public UserIdinfoService getUserIdinfoService() {
		return userIdinfoService;
	}
	public void setUserIdinfoService(UserIdinfoService userIdinfoService) {
		this.userIdinfoService = userIdinfoService;
	}
}
