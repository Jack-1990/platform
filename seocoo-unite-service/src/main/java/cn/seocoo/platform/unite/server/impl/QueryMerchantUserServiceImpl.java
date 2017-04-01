package cn.seocoo.platform.unite.server.impl;

import java.util.List;

import org.apache.log4j.Logger;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.model.MerchantUser;
import cn.seocoo.platform.model.UserIdinfo;
import cn.seocoo.platform.service.merchantUser.inf.MerchantUserService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.service.userIdinfo.inf.UserIdinfoService;
import cn.seocoo.platform.unite.Result;

import com.alibaba.fastjson.JSON;
import com.tydic.framework.util.StringUtil;

public class QueryMerchantUserServiceImpl implements Service{
	private static final Logger logger = Logger.getLogger(QueryMerchantUserServiceImpl.class);
	private MerchantUserService merchantUserService;
	private UserIdinfoService userIdinfoService;
	
	/**
	 * 商户信息查询
	 */
	public Object sevice(String param) {
		logger.debug("QueryMerchantUserServiceImpl 请求报文 :"+param);
		Result result=new Result();
		UserIdinfo user = JSON.parseObject(param, UserIdinfo.class);
		
		// 参数验证
		String validateRes = validateParam(user);
		result.setResultCode(Constant.RESULT_CODE_FAIL);

		if ("OK".equals(validateRes)){
			String loginName=user.getLoginName();
			String merchantCode = getMerchantCode(loginName);
			user.setMerchantCode(merchantCode);
			//获取列表
			List<UserIdinfo> userList=userIdinfoService.queryUserIdinfoList(user);
			if(userList !=null && userList.size() >0){
				user=userList.get(0);
				user.setLoginName(loginName);
				
				result.setResultCode(Constant.RESULT_CODE_SUCCESS);
				result.setResultMsg("获取成功");
				result.setResultData(user);
			}else{
				result.setResultMsg("无此用户个人信息");
			}
		}else{
			result.setResultMsg(validateRes);
		}
		String msg = JSON.toJSONString(result);
		logger.debug("QueryMerchantUserServiceImpl 返回报文 :"+msg);
		return msg;
	}

	
	
	/**
	 * 验证参数
	 * @param user
	 * @return
	 */
	public String validateParam(UserIdinfo user){
		if(user ==null){
			return "入参错误";
		}
		if(StringUtil.isEmpty(user.getLoginName())){
			return "用户账号不能为空";
		}
		return "OK";
	}



	/**
	 * 获取门店编码
	 * @param loginName
	 * @return
	 */
	public String getMerchantCode(String loginName){
		String merchantCode="";
		MerchantUser user=new MerchantUser();
		user.setLoginName(loginName);
		List<MerchantUser> list=merchantUserService.queryMerchantUserList(user);
		if(list !=null && list.size() >0){
			merchantCode=list.get(0).getMerchantCode();
		}
		return merchantCode;
	}

	public MerchantUserService getMerchantUserService() {
		return merchantUserService;
	}

	public void setMerchantUserService(MerchantUserService merchantUserService) {
		this.merchantUserService = merchantUserService;
	}

	public UserIdinfoService getUserIdinfoService() {
		return userIdinfoService;
	}

	public void setUserIdinfoService(UserIdinfoService userIdinfoService) {
		this.userIdinfoService = userIdinfoService;
	}
	
	
}
