package cn.seocoo.platform.unite.server.impl;

import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSON;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.model.MerchantNetSn;
import cn.seocoo.platform.service.merchantNetSn.inf.MerchantNetSnService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.unite.Result;

/**
 * 根据.net插件sn码 获取商户信息
 *
 */
public class QueryMerchantInfoServiceImpl implements Service
{
	private Logger logger = Logger.getLogger(this.getClass());
	private MerchantNetSnService merchantNetSnService;

	 
	@Override
	public Object sevice(String param)
	{
		logger.debug("QueryMerchantInfoServiceImpl 根据.net插件sn码获取商家请求参数：" + param);
		// 参数验证
		String validateRes = validateParam(param);
		// json-->bean
		MerchantNetSn merchantsn = JSON.parseObject(param, MerchantNetSn.class);
		Result result = new Result();
		result.setResultCode(Constant.RESULT_CODE_FAIL);

		if ("ok".equals(validateRes))
		{
			merchantsn=merchantNetSnService.queryMerchantNetSnBySnCode(merchantsn);
		    if(merchantsn!=null){
		    	
		    	result.setResultCode(Constant.RESULT_CODE_SUCCESS);
				result.setResultData(merchantsn);
		    }else{
		    	result.setResultCode(Constant.RESULT_CODE_FAIL);
		    }
		} else
		{
			result.setResultMsg(validateRes);
		}

		String res = JSON.toJSONString(result);
		logger.debug("QueryMerchantInfoServiceImpl 根据.net插件sn码获取商家信息报文：" + res);
		return res;
	 }
	
	
	
	// 验证参数
	public String validateParam(String param)
	{
		MerchantNetSn merchantsn = JSON.parseObject(param, MerchantNetSn.class);
		if (merchantsn == null)
		{
			return "param is null";
		}
		if(merchantsn.getSnCode()==null||"".equals(merchantsn.getSnCode()))
		{
			return "插件sn码为空";
		}
		return "ok";
	}

	
	public MerchantNetSnService getMerchantNetSnService() {
		return merchantNetSnService;
	}

	public void setMerchantNetSnService(MerchantNetSnService merchantNetSnService) {
		this.merchantNetSnService = merchantNetSnService;
	}
}
