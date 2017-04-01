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

public class AndroidVersionServiceImpl implements Service{
	private static final Logger logger = Logger.getLogger(AndroidVersionServiceImpl.class);

	@Override
	public Object sevice(String param) {
		logger.debug("sendMessageService 请求报文 :"+param);
		Result reslut=new Result();
		String versionName="";
		String versionCode="";
		//安卓版本号 
		versionCode=SystemConfigUtil.getSingleProperty("android_version_code").getPropertyValue();
		versionName=SystemConfigUtil.getSingleProperty("android_version_name").getPropertyValue();
		
		if(StringUtil.isEmpty(versionCode) || StringUtil.isEmpty(versionName)){
			reslut.setResultCode(Constant.RESULT_CODE_FAIL);
			reslut.setResultMsg("获取错误");
		}else{
			Map map=new HashMap();
			map.put("versionName", versionName);
			map.put("versionCode", versionCode);
			reslut.setResultData(map);
			
			reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
			reslut.setResultMsg("获取成功");
		}
		String msg = JSON.toJSONString(reslut);
		return msg;
	}

}
