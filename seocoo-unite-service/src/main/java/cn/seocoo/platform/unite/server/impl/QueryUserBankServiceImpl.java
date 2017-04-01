package cn.seocoo.platform.unite.server.impl;

import java.util.List;

import org.apache.log4j.Logger;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.model.MerchantUser;
import cn.seocoo.platform.model.UserBank;
import cn.seocoo.platform.service.merchantUser.inf.MerchantUserService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.service.userBank.inf.UserBankService;
import cn.seocoo.platform.unite.Result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tydic.framework.util.StringUtil;

public class QueryUserBankServiceImpl implements Service{
	private static final Logger logger = Logger.getLogger(QueryUserBankServiceImpl.class);
	private UserBankService userBankService;
    private MerchantUserService merchantUserService;

    
	/**
	 * 商户银行卡信息查询
	 */
	public Object sevice(String param) {
		logger.debug("QueryUserBankServiceImpl 请求报文 :"+param);
		Result result=new Result();
		UserBank  user = JSONObject.parseObject(param, UserBank.class);
		
		// 参数验证
		String validateRes = validateParam(user);
		result.setResultCode(Constant.RESULT_CODE_FAIL);

		if ("OK".equals(validateRes)){
			String merchantCode=getMerchantCode(user.getLoginName());
			user.setMerchantCode(merchantCode);
			//获取列表
			List<UserBank> userList=userBankService.queryUserBankList(user);
			if(userList !=null && userList.size() >0){
				user=userList.get(0);
				
				result.setResultCode(Constant.RESULT_CODE_SUCCESS);
				result.setResultMsg("获取成功");
				result.setResultData(user);
			}else{
				result.setResultMsg("无此用户的银行信息");
			}
		}else{
			result.setResultMsg(validateRes);
		}
		String msg = JSON.toJSONString(result);
		logger.debug("QueryUserBankServiceImpl 返回报文 :"+msg);
		return msg;
	}

	
	
	/**
	 * 验证参数
	 * @param user
	 * @return
	 */
	public String validateParam(UserBank user){
		if(user ==null){
			return "入参错误";
		}
		if(StringUtil.isEmpty(user.getLoginName())){
			return "用户账号不能为空";
		}
		return "OK";
	}



	public UserBankService getUserBankService() {
		return userBankService;
	}

	public void setUserBankService(UserBankService userBankService) {
		this.userBankService = userBankService;
	}

	public MerchantUserService getMerchantUserService() {
		return merchantUserService;
	}

	public void setMerchantUserService(MerchantUserService merchantUserService) {
		this.merchantUserService = merchantUserService;
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
	
	
}
