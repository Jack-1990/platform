package cn.seocoo.platform.unite.server.impl;

import org.apache.log4j.Logger;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.AllLevelRate;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.unite.Result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tydic.framework.util.StringUtil;

public class QueryLevelRateServiceImpl implements Service{
	private static final Logger logger = Logger.getLogger(QueryLevelRateServiceImpl.class);

	/**
	 * wap页费率链接查询
	 */
	public Object sevice(String param) {
		logger.debug("QueryLevelRateServiceImpl 请求报文 :"+param);
		Result result=new Result();
		AllLevelRate  rate = JSONObject.parseObject(param, AllLevelRate.class);
		
		String visitPath=SystemConfigUtil.getSingleProperty("manage_visit_path").getPropertyValue();
		String url="/wap!levelRate.do";
		url = visitPath + url;
			
		if(!StringUtil.isEmpty(rate.getLevelCode())){
			url = url + "?levelCode=" + rate.getLevelCode();
		}
		
		result.setResultCode(Constant.RESULT_CODE_SUCCESS);
		result.setResultMsg("查询成功");
		result.setResultData(url);
			
		String msg = JSON.toJSONString(result);
		logger.debug("QueryLevelRateServiceImpl 返回报文 :"+msg);
		return msg;
	}

	
}
