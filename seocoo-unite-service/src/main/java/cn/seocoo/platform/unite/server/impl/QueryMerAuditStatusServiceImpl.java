package cn.seocoo.platform.unite.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.model.MerchantInfo;
import cn.seocoo.platform.model.MerchantUser;
import cn.seocoo.platform.service.merchantInfo.inf.MerchantInfoService;
import cn.seocoo.platform.service.merchantUser.inf.MerchantUserService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.service.userBank.inf.UserBankService;
import cn.seocoo.platform.service.userImage.inf.UserImageService;
import cn.seocoo.platform.unite.Result;

import com.alibaba.fastjson.JSON;
import com.tydic.framework.util.StringUtil;

public class QueryMerAuditStatusServiceImpl implements Service{
	private Logger logger = Logger.getLogger(this.getClass());
	private MerchantUserService merchantUserService;
	private UserBankService userBankService;
	private UserImageService userImageService;
	private MerchantInfoService merchantInfoService;
	
	/**
	 * 查询实名子项审核状态接口
	 */
	public Object sevice(String param){
		logger.debug("QueryMerAuditStatusServiceImpl 请求参数：" + param);
		MerchantUser user = JSON.parseObject(param, MerchantUser.class);
		// 参数验证
		String validateRes = validateParam(user);
		Result result = new Result();
		result.setResultCode(Constant.RESULT_CODE_FAIL);

		if ("OK".equals(validateRes)){
			String merchantCode=getMerchantCode(user.getLoginName());

			//返回值  ：默认0未完善，1是审核中，2是已认证，3是未通过
			int status=0;
			String userStatus="未完善";
			
			//1.个人身份信息
			MerchantInfo u=new MerchantInfo();
			u.setMerchantCode(merchantCode);
			List<MerchantInfo> userList=merchantInfoService.queryMerchantInfoList(u);
			if(userList !=null && userList.size()>0){
				if(!StringUtil.isEmpty(userList.get(0).getCertifyStatus().toString())){
					status=userList.get(0).getCertifyStatus();
					if(status == 1){
						userStatus="审核中";
					}else if(status == 2){
						userStatus="已认证";
					}else if(status == 3){
						userStatus="未通过";
					}
				}
			}
			
			Map map=new HashMap();
			map.put("userStatus", userStatus);
			map.put("status", status);
			
	    	result.setResultCode(Constant.RESULT_CODE_SUCCESS);
	    	result.setResultMsg("查询成功");
	    	result.setResultData(map);
		} else{
			result.setResultMsg(validateRes);
		}
		String res = JSON.toJSONString(result);
		logger.debug("QueryMerAuditStatusServiceImpl 返回信息报文：" + res);
		return res;
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


	public UserBankService getUserBankService() {
		return userBankService;
	}


	public void setUserBankService(UserBankService userBankService) {
		this.userBankService = userBankService;
	}


	public UserImageService getUserImageService() {
		return userImageService;
	}


	public void setUserImageService(UserImageService userImageService) {
		this.userImageService = userImageService;
	}

	public MerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}

	public void setMerchantInfoService(MerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}

	
}
