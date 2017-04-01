package cn.seocoo.platform.unite.server.impl;


import org.apache.log4j.Logger;
import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.model.MerchantUser;
import cn.seocoo.platform.service.merchantUser.inf.MerchantUserService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.unite.Result;

import com.alibaba.fastjson.JSON;
import com.tydic.framework.util.StringUtil;

public class AppLogoutServiceImpl implements Service{
	private static final Logger logger = Logger.getLogger(AppLogoutServiceImpl.class);
	private MerchantUserService merchantUserService;
	
	/**
	 * app 退出登陆
	 */
	public Object sevice(String param) {
		logger.debug("AppLogoutServiceImpl 请求报文 :"+param);
		Result result=new Result();
		MerchantUser user = JSON.parseObject(param, MerchantUser.class);
		
		// 参数验证
		String validateRes = validateParam(user);
		result.setResultCode(Constant.RESULT_CODE_FAIL);

		if ("OK".equals(validateRes)){
				
			    merchantUserService.updateTokenByLoginName(user);
			
				result.setResultCode(Constant.RESULT_CODE_SUCCESS);
				result.setResultMsg("退出成功");
		}else{
			result.setResultMsg(validateRes);
		}
		String msg = JSON.toJSONString(result);
		logger.debug("AppLogoutServiceImpl 返回报文 :"+msg);
		return msg;
	}

	
	
	/**
	 * 验证参数
	 * @param user
	 * @return
	 */
	public String validateParam(MerchantUser user){
		if(user ==null){
			return "入参错误";
		}
		if(StringUtil.isEmpty(user.getLoginName())){
			return "用户账号不能为空";
		}
		return "OK";
	}



	public MerchantUserService getMerchantUserService() {
		return merchantUserService;
	}

	public void setMerchantUserService(MerchantUserService merchantUserService) {
		this.merchantUserService = merchantUserService;
	}
	
	
}
