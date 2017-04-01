package cn.seocoo.platform.unite.server.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.StringUtil;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.unite.Result;
import cn.seocoo.platform.service.unite.inf.Service;
 
 

public class NetPluginVersionServiceImpl implements Service{
	private static final Logger logger = Logger.getLogger(NetPluginVersionServiceImpl.class);

	@Override
	public Object sevice(String param) {
		  logger.debug("sendMessageService 请求报文 :"+param);
		    Result reslut=new Result();
		    String versionCode=SystemConfigUtil.getSingleProperty("netPlugin_version_code").getPropertyValue();
			
		    Map map=new HashMap();
			map.put("versionCode", versionCode);
			reslut.setResultData(map);
			
			if(StringUtil.isEmpty(versionCode)){
				reslut.setResultCode(Constant.RESULT_CODE_FAIL);
				reslut.setResultMsg("获取错误");
			}else{
				reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
				reslut.setResultMsg("获取成功");
			}
		 
		String msg = JSON.toJSONString(reslut);
		return msg;
	}

}
