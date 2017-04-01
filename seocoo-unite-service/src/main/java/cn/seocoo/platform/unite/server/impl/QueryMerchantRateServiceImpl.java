package cn.seocoo.platform.unite.server.impl;

import java.util.List;
import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.StringUtil;
import cn.seocoo.platform.model.MerchantRate;
import cn.seocoo.platform.service.merchantRate.inf.MerchantRateService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.unite.Result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 查询商户费率
 * @author wangyuan
 *
 */
public class QueryMerchantRateServiceImpl  implements Service{
	private MerchantRateService merchantRateService;
	@Override
	public Object sevice(String param) {
		MerchantRate rate  = JSONObject.parseObject(param, MerchantRate.class);
		Result reslut=new Result();
		//入参检验
	    String checkMsg=checkParam(rate);
	    if("OK".equals(checkMsg)){
	       List<MerchantRate> rateList=merchantRateService.queryMerchantRateList(rate);
	       if(rateList !=null && rateList.size() >0){
	    	   rate=rateList.get(0);
	       }
	       reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
		   reslut.setResultMsg("查询成功");
		   reslut.setResultData(rate);
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
	public String checkParam(MerchantRate rate) {
		if(rate ==null){
			return "入参错误";
		}
		if(StringUtil.isEmpty(rate.getMerchantCode())){
			return "用户商户号不能为空";
		}
		return "OK";
	}


	public MerchantRateService getMerchantRateService() {
		return merchantRateService;
	}


	public void setMerchantRateService(MerchantRateService merchantRateService) {
		this.merchantRateService = merchantRateService;
	}
	

	
}
