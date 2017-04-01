package cn.seocoo.platform.unite.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.StringUtil;
import cn.seocoo.platform.model.PaymentProfit;
import cn.seocoo.platform.service.paymentProfit.inf.PaymentProfitService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.unite.Result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 查询分润明细
 * @author wangyuan
 *
 */
public class QueryPayProfitServiceImpl  implements Service{
	private PaymentProfitService paymentProfitService;
	@Override
	public Object sevice(String param) {
		PaymentProfit profit  = JSONObject.parseObject(param, PaymentProfit.class);
		Result reslut=new Result();
		//入参检验
	    String checkMsg=checkParam(profit);
	    if("OK".equals(checkMsg)){
	       Map  map=new HashMap();
	       map.put("merchantCode",profit.getMerchantCode() );
	       map.put("profitUser", profit.getProfitUser());
	      /* map.put("offset", profit.getOffset());
	       map.put("pageSize", profit.getPageSize());
	       map.put("startTime", profit.getStartTime());
	       map.put("endTime", profit.getEndTime());*/
	       
	       List<PaymentProfit> proList=paymentProfitService.queryPaymentProfitPage(map);
	       
	       reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
		   reslut.setResultMsg("查询成功");
		   reslut.setResultData(proList);
			
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
		if(StringUtil.isEmpty(profit.getMerchantCode())){
			return "用户商户号不能为空";
		}
		if(StringUtil.isEmpty(profit.getProfitUser())){
			return "用户登录账号不能为空";
		}
	/*	if(StringUtil.isEmpty(profit.getOffset())){
			return "分页开始页不能为空";
		}
		if(StringUtil.isEmpty(profit.getPageSize())){
			return "分页每页记录数不能为空";
		}*/
		return "OK";
	}
	
	
	public PaymentProfitService getPaymentProfitService() {
		return paymentProfitService;
	}
	public void setPaymentProfitService(PaymentProfitService paymentProfitService) {
		this.paymentProfitService = paymentProfitService;
	}
	
}
