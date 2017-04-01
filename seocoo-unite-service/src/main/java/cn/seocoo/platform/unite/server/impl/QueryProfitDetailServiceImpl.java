package cn.seocoo.platform.unite.server.impl;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.StringUtil;
import cn.seocoo.platform.model.MerchantInfo;
import cn.seocoo.platform.model.PaymentProfit;
import cn.seocoo.platform.service.merchantInfo.inf.MerchantInfoService;
import cn.seocoo.platform.service.paymentProfit.inf.PaymentProfitService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.unite.Result;

/**
 * 我的分润 分润统计详情接口
 * @author cdw
 *
 */
public class QueryProfitDetailServiceImpl  implements Service{
	private PaymentProfitService paymentProfitService;
	private MerchantInfoService merchantInfoService;
	@Override
	public Object sevice(String param) {
		PaymentProfit profit  = JSONObject.parseObject(param, PaymentProfit.class);
		Result reslut=new Result();
		//入参检验
	    String checkMsg=checkParam(profit);
	    if("OK".equals(checkMsg)){
	    	String merchantCode=profit.getProfitUser();
	    	 
	    	MerchantInfo merchantInfo=new MerchantInfo();
	    	merchantInfo.setMerchantCode(merchantCode);
	    	merchantInfo=merchantInfoService.queryMerchantInfo(merchantInfo);
	    	
	    	//用户当前的累计分润
	    	Double currentTotalProfit=merchantInfo.getCurrentTotalProfit();
	    	//用户共累计历史分润
	    	Double totalProfit=merchantInfo.getTotalProfit();
	    	
	    	PaymentProfit payProfit=new PaymentProfit();
	    	payProfit.setProfitUser(merchantCode);
	    	//查询昨日分润
	    	Double yesterdayAmountProfit=paymentProfitService.queryYesterdayProfit(payProfit);
	    	       yesterdayAmountProfit=yesterdayAmountProfit==null?0:yesterdayAmountProfit;
	    	
	    	//查询今天分润
	    	Double todayAmountProfit=paymentProfitService.queryTodayProfit(payProfit);
	    	       todayAmountProfit=todayAmountProfit==null?0:todayAmountProfit;
	    	
	    	//查询本月分润
	    	Double monthAmountProfit=paymentProfitService.queryMonthProfit(payProfit);
	    	       monthAmountProfit=monthAmountProfit==null?0:monthAmountProfit;
	    	
	    	//当前可提现分润
	    	double enabledProfit=currentTotalProfit.doubleValue() - monthAmountProfit.doubleValue();
	        if(enabledProfit<0){//如果可提现金额小于0，则为0
	        	enabledProfit=0;
	        }
	        
	        Map resultMap=new HashMap();
	        resultMap.put("currentTotalProfit", currentTotalProfit);
	        resultMap.put("totalProfit", totalProfit);
	        resultMap.put("yesterdayAmountProfit", yesterdayAmountProfit);
	        resultMap.put("todayAmountProfit", todayAmountProfit);
	        resultMap.put("enabledProfit", enabledProfit);
	        
	        reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
		    reslut.setResultData(resultMap);
	    }else{
	    	reslut.setResultCode(Constant.RESULT_CODE_FAIL);
			reslut.setResultMsg(checkMsg);
	    }
		 String res = JSON.toJSONString(reslut);
		 return res;
	}
	
	/**
	 * 检验入参
	 * @param user
	 * @return
	*/
	public String checkParam(PaymentProfit profit) {
		if(profit ==null){
			return "入参错误";
		}
		if(StringUtil.isEmpty(profit.getProfitUser())){
			return "分润用户不能为空";
		}
		return "OK";
	}
	
	public PaymentProfitService getPaymentProfitService() {
		return paymentProfitService;
	}
	public void setPaymentProfitService(PaymentProfitService paymentProfitService) {
		this.paymentProfitService = paymentProfitService;
	}
	public MerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}
	public void setMerchantInfoService(MerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}
}
