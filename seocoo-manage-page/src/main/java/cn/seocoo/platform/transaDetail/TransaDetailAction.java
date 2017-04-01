package cn.seocoo.platform.transaDetail;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.alibaba.fastjson.JSONObject;
import com.odchina.micro.shiro.ShiroUser;
import com.odchina.micro.util.DateUtils;
import com.odchina.micro.util.HttpUtils;
import com.odchina.micro.util.PayCoreUtil;
import com.tydic.framework.base.exception.ServiceException;
import com.tydic.framework.util.StringUtil;

import cn.seocoo.platform.common.base.BaseAction;
import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.Order;
import cn.seocoo.platform.model.PaymentProfit;
import cn.seocoo.platform.model.UserRelationship;
import cn.seocoo.platform.msbank.CancelTrans;
import cn.seocoo.platform.msbank.OrderResultMsg;
import cn.seocoo.platform.msbank.PayResultMsg;
import cn.seocoo.platform.msbank.PublicRequestMsg;
import cn.seocoo.platform.msbank.PublicReturnMsg;
import cn.seocoo.platform.msbank.QueryPayResult;
import cn.seocoo.platform.msbank.ResponseMsg;
import cn.seocoo.platform.service.order.inf.OrderService;
import cn.seocoo.platform.service.paymentProfit.inf.PaymentProfitService;
import cn.seocoo.platform.service.userRelationship.inf.UserRelationshipService;

public class TransaDetailAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(TransaDetailAction.class);
    private OrderService orderService;
	private UserRelationshipService userRelationshipService;
	private PaymentProfitService paymentProfitService;


	/*
	 * 首次第一次加载
	 */
	public String transaDetail() throws ServiceException
	{
		// 获取用户信息
		ShiroUser su = queryCurrentShiroUser();
		
		return "transaDetail";
	}

	
	/**
	 * 查询列表
	 * @return
	 */
	public String queryList(){
		String pageIndex=request.getParameter("pageIndex");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		
		int pageSize = Constant.PAGESIZE_TEN;
		int beginRow = Integer.parseInt(pageIndex);
		// 获取用户信息
		ShiroUser su = queryCurrentShiroUser();
		
		Map map=new HashMap();
		
		if(!"admin".equals(su.getLoginName())){
			UserRelationship p=new UserRelationship();
			p.setLoginName(su.getLoginName());
			List<UserRelationship> uList=userRelationshipService.queryUserRelationshipList(p);
			if(uList !=null && uList.size()>0){
				map.put("merchantCode", uList.get(0).getMerchantCode());
			}else{
				map.put("merchantCode", "P");
			}
		}
		map.put("status", Constant.COMMON_STATUS);
		map.put("pageSize", pageSize);
		map.put("offset", beginRow * pageSize);
		if(!StringUtil.isEmpty(startDate)){
			map.put("startDate", startDate +" "+"00-00-00");
		}
		if(!StringUtil.isEmpty(endDate)){
			map.put("endDate", endDate +" "+"23-59-59");
		}
		
		List<Order> orderList=orderService.queryOrderPage(map);
		
		int total=0;
		total=orderService.queryOrderPageCount(map);
		// 求余 获取分页总数
		int totalPage = 0;
		int remainder = total % pageSize;
		if (remainder == 0)
		{
			totalPage = total / pageSize;
		} else
		{
			totalPage = total / pageSize + 1;
		}
		int datetime = Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime())); // 获取系统时间
		// 判断是否可以退款： 如果超过今天时间则不能退款
		for (Order order : orderList)
		{
			Date transTime = order.getTransTime();
			int tt = Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(transTime));
			if (datetime - tt != 0)
			{
				order.setIsShowTuiKuan(false);
			} else
			{
				order.setIsShowTuiKuan(true);
			}

		}
		request.setAttribute("orderList", orderList);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("nowTime", datetime);
		return "transaDetailList";
	}


	
	/**
	 * 导出Excel
	 * @throws IOException
	 */
	public void exportEx() throws IOException {
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");

		// 获取用户信息
		ShiroUser su = queryCurrentShiroUser();
		Map map=new HashMap();
		if(!"admin".equals(su.getLoginName())){
			UserRelationship p=new UserRelationship();
			p.setLoginName(su.getLoginName());
			List<UserRelationship> uList=userRelationshipService.queryUserRelationshipList(p);
			if(uList !=null && uList.size()>0){
				map.put("merchantCode", uList.get(0).getMerchantCode());
			}else{
				map.put("merchantCode", "P");
			}
		}
		map.put("status", Constant.COMMON_STATUS);
		map.put("pageSize", 100);
		map.put("offset", 0);
		if(!StringUtil.isEmpty(startDate)){
			map.put("startDate", startDate +" "+"00-00-00");
		}
		if(!StringUtil.isEmpty(endDate)){
			map.put("endDate", endDate +" "+"23-59-59");
		}
		
		List<Order> orderList=orderService.queryOrderPage(map);
		
		exportExcel(response, orderList);
	}
	
	
	 public void exportExcel(HttpServletResponse response,List<Order> list) 
	   {   
	    HSSFWorkbook wb = new HSSFWorkbook();  
	    HSSFSheet sheet = wb.createSheet("表");  
	    HSSFRow row = sheet.createRow((int) 0);  
	    HSSFCellStyle style = wb.createCellStyle();  
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

	    HSSFCell cell = row.createCell((short) 0);  
	    cell.setCellValue("交易时间");  
	    cell.setCellStyle(style);  
	    cell = row.createCell((short) 1);  
	    cell.setCellValue("金额（元）");  
	    cell.setCellStyle(style);  
	    cell = row.createCell((short) 2);  
	    cell.setCellValue("支付通道");  
	    cell.setCellStyle(style);  
	    cell = row.createCell((short) 3);  
	    cell.setCellValue("订单号");  
	    cell.setCellStyle(style);
	    cell = row.createCell((short) 4);  
	    cell.setCellValue("流水号");  
	    cell.setCellStyle(style);

	    for (int i = 0; i < list.size(); i++)  
	    {  
	        row = sheet.createRow((int) i + 1);  
	        Order st = (Order) list.get(i); 
	        row.createCell((short) 0).setCellValue(DateUtils.toString(st.getTransTime(), "yyyy-MM-dd HH:mm:ss")); 
	        row.createCell((short) 1).setCellValue( Double.parseDouble(st.getAmount())/100);  
	        row.createCell((short) 2).setCellValue(getPayName(st.getSelectTradeType())); 
	        row.createCell((short) 3).setCellValue( st.getOrderNumber());  
	        row.createCell((short) 4).setCellValue(st.getMerchantSeq());
	    }  
	    
	    sheet.autoSizeColumn((short)0); 
        sheet.autoSizeColumn((short)1); 
        sheet.autoSizeColumn((short)2); 
        sheet.autoSizeColumn((short)3); 
        sheet.autoSizeColumn((short)4);
        
	    try  
	    {  
	      //输出Excel文件
	        OutputStream output=response.getOutputStream();
	        response.reset();
	        String dateTime = DateUtils.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
	        response.setHeader("Content-disposition", "attachment; filename=Transaction_Detail_"+ dateTime +".xls");
	        response.setContentType("application/msexcel");        
	        wb.write(output);
	        output.close();
	    }  
	    catch (Exception e)  
	    {  
	        e.printStackTrace();  
	    }  
	}
	 
	 /**
	 * 订单退款操作
	 * @throws IOException 
	 */
	public void cancelTrans() throws IOException{
		 String merchantSeq=request.getParameter("merchantSeq");
		 String merchantNo=request.getParameter("merchantNo");
		 String reserve=request.getParameter("reserve");
		 ShiroUser su = queryCurrentShiroUser();
		 
		 Order order=new Order();
		 order.setMerchantNo(merchantNo);
		 order.setMerchantSeq(merchantSeq);
         order=orderService.queryOrder(order);	
         JSONObject json = new JSONObject();
         if(order!=null){
        	 CancelTrans ct=new CancelTrans();
        	 ct.setMerchantNo(merchantNo);
        	 ct.setMerchantSeq(merchantSeq);
        	 ct.setOrderAmount(order.getAmount());
        	 ct.setOrderNote("退款");
        	 ct.setReserve(reserve);
        	 ct.setPlatformId(order.getPlatformId());
        	 OrderResultMsg resultMsg=cancelTransToMs(ct);
        	
        	 boolean isSuccess=resultMsg.getIsSuccess();
        	 if(isSuccess)
        	 {
				order.setStatus(2);//已退款
				order.setOrderAmount(ct.getOrderAmount());//退款金额
				order.setOrderNote(ct.getOrderNote());//退款说明
				order.setReserve(ct.getReserve());//退款备注信息
				order.setVoucherNo(resultMsg.getVoucherNo());
				order.setRefundOperator(su.getLoginName());//退款操作人
				orderService.updateTradeByMno(order);
				
				//将订单对应的分润状态变为1,不参加第二天的分润统计计算
				String orderNumber=order.getOrderNumber();
				PaymentProfit profit=new PaymentProfit();
				profit.setOrderNumber(orderNumber);
				profit.setProfitStatus(1);//状态为1 表示已退款订单的分润
				paymentProfitService.updateProfitByOrderNumber(profit);				
				 
        		json.put("resultCode", Constant.RESULT_CODE_SUCCESS); 

        	 }else{
        		 json.put("resultCode", Constant.RESULT_CODE_FAIL);
        		 json.put("resultMsg", resultMsg.getFailMsg());
        	 }
         }else{
        	 json.put("resultCode", Constant.RESULT_CODE_FAIL);
    		 json.put("resultMsg", "没有该订单信息");
         }
         this.sendMessages(json.toJSONString());
	 }
	
	 
	/**
	 * 提交订单退款信息给民生银行
	 * 
	 * @param bindOrder
	 * @return
	 */
	public OrderResultMsg cancelTransToMs(CancelTrans ct) {
		
		String context = JSONObject.toJSONString(ct);
		logger.info(context); 
		
		String sign = PayCoreUtil.getSign(context);
		logger.info("--------------------------------------");
		logger.info("签名：");
		logger.info(sign);

		String signContext = PayCoreUtil.sign(sign, context);
		logger.info("--------------------------------------");
		logger.info("加密前：");
		logger.info(signContext);

		String encryptContext = PayCoreUtil.encrypt(signContext);
		logger.info("--------------------------------------");
		logger.info("加密后：");
		logger.info(encryptContext);

		PublicRequestMsg MerchantMsg = new PublicRequestMsg();
		MerchantMsg.setBusinessContext(encryptContext);

		logger.info("报文信息======" + JSONObject.toJSONString(MerchantMsg) + "\n");
		
		String submitOrderUrl=SystemConfigUtil.getSingleProperty("cancelTrans_ms_url").getPropertyValue();
		String str = HttpUtils.sendPost(submitOrderUrl,JSONObject.toJSONString(MerchantMsg));

		logger.info("银行返回=" + str);
		PublicReturnMsg prm = JSONObject.parseObject(str, PublicReturnMsg.class);

		String gateReturnType = prm.getGateReturnType();
		//gateReturnType为S时，处理返回字段
		if ("S".equals(gateReturnType)) {
			String dncryptContext = PayCoreUtil.dncrypt(prm.getBusinessContext());
			logger.info("--------------------------------------");
			logger.info("解密后：");
			logger.info(dncryptContext);

			ResponseMsg rm = JSONObject.parseObject(dncryptContext, ResponseMsg.class);
			String body = rm.getBody();
			logger.info("最终结果：");
			logger.info(body);

			OrderResultMsg orm = JSONObject.parseObject(body, OrderResultMsg.class);
			String signChkResult = PayCoreUtil.signCheck(dncryptContext);
			logger.info("--------------------------------------");
			logger.info("验证签名结果：");
			logger.info(signChkResult);

			// String remark=orm.getRemark();
			String tradeStatus = orm.getTradeStatus();// S订单退款成功
			if ("验签通过".equals(signChkResult) && "S".equals(tradeStatus)) {//验签通过并且返回tradeStatus为R时，返回结果
				 
				orm.setIsSuccess(true);
				return orm;
			} else {
				orm.setIsSuccess(false);
				orm.setFailMsg("验签失败");
				return orm;
			}
		} else {
			OrderResultMsg orm1=new OrderResultMsg();
			orm1.setIsSuccess(false);
			orm1.setFailMsg(prm.getGateReturnMessage());
			return orm1;
		}

	}
	
	
	    /**
		 * 查询支付结果
		 * @throws IOException 
		 */
		public void queryPayResult() throws IOException{
			 String merchantSeq=request.getParameter("merchantSeq");
			 String merchantNo=request.getParameter("merchantNo");
			 String  tradeType=request.getParameter("tradeType");//支付：1  退款：2
			 ShiroUser su = queryCurrentShiroUser();
			 
			 Order order=new Order();
			 order.setMerchantNo(merchantNo);
			 order.setMerchantSeq(merchantSeq);
	         order=orderService.queryOrder(order);	
	         JSONObject json = new JSONObject();
	         if(order!=null){
        	 QueryPayResult qpr=new QueryPayResult();
        	 qpr.setPlatformId(order.getPlatformId());
        	 qpr.setMerchantNo(merchantNo);
        	 qpr.setMerchantSeq(merchantSeq);
        	 
        	 if(tradeType!=null&&"2".equals(tradeType)){
        		 qpr.setTradeType("2");
        		 qpr.setOrgvoucherNo(order.getVoucherNo());
        		 qpr.setReserve("查询退款");
        	 }else{
        		 qpr.setTradeType("1");
        		 qpr.setReserve("查询支付");
        	 }
        	    PayResultMsg payResultMsg=queryPayResultToMs(qpr);   
	        	 
	        	 boolean isSuccess=payResultMsg.getIsSuccess();
	        	 if(isSuccess)
	        	 {
	        		json.put("resultCode", Constant.RESULT_CODE_SUCCESS);
	        		json.put("PayResultMsg",payResultMsg);
	        	 
	        	 }else{
	        		 json.put("resultCode", Constant.RESULT_CODE_FAIL);
	        		 json.put("resultMsg", payResultMsg.getFailMsg());
	        	 }
	         }else{
	        	 json.put("resultCode", Constant.RESULT_CODE_FAIL);
	    		 json.put("resultMsg", "没有该订单信息");
	         }
	         this.sendMessages(json.toJSONString());
		 }
		
	
	
	
		/**
		 * 提交订单退款信息给民生银行
		 * 
		 * @param bindOrder
		 * @return
		 */
		public PayResultMsg queryPayResultToMs(QueryPayResult qpr) {
			
			String context = JSONObject.toJSONString(qpr);
			logger.info(context); 
			
			String sign = PayCoreUtil.getSign(context);
			logger.info("--------------------------------------");
			logger.info("签名：");
			logger.info(sign);

			String signContext = PayCoreUtil.sign(sign, context);
			logger.info("--------------------------------------");
			logger.info("加密前：");
			logger.info(signContext);

			String encryptContext = PayCoreUtil.encrypt(signContext);
			logger.info("--------------------------------------");
			logger.info("加密后：");
			logger.info(encryptContext);

			PublicRequestMsg MerchantMsg = new PublicRequestMsg();
			MerchantMsg.setBusinessContext(encryptContext);

			logger.info("报文信息======" + JSONObject.toJSONString(MerchantMsg) + "\n");
			
			String submitOrderUrl=SystemConfigUtil.getSingleProperty("queryPayresult_ms_url").getPropertyValue();
			String str = HttpUtils.sendPost(submitOrderUrl,JSONObject.toJSONString(MerchantMsg));

			logger.info("银行返回=" + str);
			PublicReturnMsg prm = JSONObject.parseObject(str, PublicReturnMsg.class);

			String gateReturnType = prm.getGateReturnType();
			//gateReturnType为S时，处理返回字段
			if ("S".equals(gateReturnType)) {
				String dncryptContext = PayCoreUtil.dncrypt(prm.getBusinessContext());
				logger.info("--------------------------------------");
				logger.info("解密后：");
				logger.info(dncryptContext);

				ResponseMsg rm = JSONObject.parseObject(dncryptContext, ResponseMsg.class);
				String body = rm.getBody();
				logger.info("最终结果：");
				logger.info(body);

				PayResultMsg orm = JSONObject.parseObject(body, PayResultMsg.class);
				String signChkResult = PayCoreUtil.signCheck(dncryptContext);
				logger.info("--------------------------------------");
				logger.info("验证签名结果：");
				logger.info(signChkResult);
				 
				if ("验签通过".equals(signChkResult)) {
					 
					orm.setIsSuccess(true);
					return orm;
				} else {
					orm.setIsSuccess(false);
					orm.setFailMsg("验签失败");
					return orm;
				}
			} else {
				PayResultMsg orm1=new PayResultMsg();
				orm1.setIsSuccess(false);
				orm1.setFailMsg(prm.getGateReturnMessage());
				return orm1;
			}
		}

		
		
	private String getPayName(String type) {
	    String name=""; 
		if("API_WXQRCODE".equals(type)){
			name="微信"; 
		}else if("API_WXSCAN".equals(type)){
			name="微信"; 
		}else if( "H5_WXJSAPI".equals(type)){
			name="微信"; 
		}else if("API_ZFBQRCODE".equals(type)){
			name="支付宝"; 
		}else if("API_ZFBSCAN".equals(type)){
			name="支付宝"; 
		}else if("H5_ZFBJSAPI".equals(type)){
			name="支付宝"; 
		}else if("H5_ZFBJSAPI".equals(type)){
			name="QQ钱包"; 
		}else if("API_QQSCAN".equals(type)){
			name="QQ钱包"; 
		}else if("H5_QQJSAPI".equals(type)){
			name="QQ钱包"; 
		}
		return name;
	}


	public OrderService getOrderService() {
		return orderService;
	}


	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public UserRelationshipService getUserRelationshipService() {
		return userRelationshipService;
	}

	public void setUserRelationshipService(
			UserRelationshipService userRelationshipService) {
		this.userRelationshipService = userRelationshipService;
	}


	public PaymentProfitService getPaymentProfitService() {
		return paymentProfitService;
	}

	public void setPaymentProfitService(PaymentProfitService paymentProfitService) {
		this.paymentProfitService = paymentProfitService;
	}

}
