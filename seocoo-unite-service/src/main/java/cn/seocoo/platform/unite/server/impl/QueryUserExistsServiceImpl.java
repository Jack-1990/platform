package cn.seocoo.platform.unite.server.impl;


import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.StringUtil;
import cn.seocoo.platform.model.MerchantUser;
import cn.seocoo.platform.service.merchantUser.inf.MerchantUserService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.unite.Result;

public class QueryUserExistsServiceImpl implements Service{
	private static final Logger logger = Logger.getLogger(QueryUserExistsServiceImpl.class);
	private MerchantUserService merchantUserService;

	@Override
	public Object sevice(String param) {
		logger.debug("QueryUserExistsServiceImpl 请求报文 :"+param);
		Result reslut=new Result();
		MerchantUser user=JSONObject.parseObject(param, MerchantUser.class);
		//入参检验
		String checkMsg=checkParam(user);
		reslut.setResultCode(Constant.RESULT_CODE_FAIL);
		if("OK".equals(checkMsg)){
			List<MerchantUser>  userList = merchantUserService.queryMerchantUserList(user);
			if(userList !=null && userList.size()>0){
				reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
				reslut.setResultMsg("用户存在");
			}else{
				reslut.setResultMsg("用户不存在");
			}
		}else{
			reslut.setResultMsg(checkMsg);
		}
		String msg = JSON.toJSONString(reslut);
		return msg;
	}

	
	
	/**
	 * 检验入参
	 * @param user
	 * @return
	 */
	public String checkParam(MerchantUser user) {
		if(user ==null){
			return "入参错误";
		}
		if(StringUtil.isEmpty(user.getLoginName())){
			return "用户登录名不能为空";
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
