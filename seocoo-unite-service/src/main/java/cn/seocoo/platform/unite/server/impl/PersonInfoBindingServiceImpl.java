package cn.seocoo.platform.unite.server.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.model.MerchantInfo;
import cn.seocoo.platform.model.MerchantUser;
import cn.seocoo.platform.model.UserIdinfo;
import cn.seocoo.platform.service.merchantInfo.inf.MerchantInfoService;
import cn.seocoo.platform.service.merchantUser.inf.MerchantUserService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.service.userIdinfo.inf.UserIdinfoService;
import cn.seocoo.platform.unite.Result;

import com.alibaba.fastjson.JSON;
import com.tydic.framework.util.StringUtil;

public class PersonInfoBindingServiceImpl implements Service{
	private Logger logger = Logger.getLogger(this.getClass());
	private MerchantUserService merchantUserService;
	private UserIdinfoService userIdinfoService;
	private MerchantInfoService merchantInfoService;
	
	
	/**
	 * 用户基本信息绑定
	 */
	public Object sevice(String param){
		logger.debug("PersonInfoBindingServiceImpl 请求参数：" + param);
		UserIdinfo user = JSON.parseObject(param, UserIdinfo.class);
		// 参数验证
		String merchantCode=getMerchantCode(user.getLoginName());
		String validateRes = validateParam(user,merchantCode);
		Result result = new Result();
		result.setResultCode(Constant.RESULT_CODE_FAIL);

		if ("OK".equals(validateRes)){
			
			
			UserIdinfo m = new UserIdinfo();
			m.setMerchantCode(merchantCode);
			List<UserIdinfo> userList=userIdinfoService.queryUserIdinfoList(m);
			if(userList !=null && userList.size()>0){
				user.setId(userList.get(0).getId());
				
				userIdinfoService.updateUserIdinfo(user);
				
				result.setResultCode(Constant.RESULT_CODE_SUCCESS);
				result.setResultMsg("用户信息修改成功");
			}else{
				user.setMerchantCode(merchantCode);
				user.setAuditStatus(Constant.COMMON_STATUS_0);
				user.setCreateUser(user.getLoginName());
				user.setCreateTime(new Date());
				userIdinfoService.saveUserIdinfo(user);
				
				result.setResultCode(Constant.RESULT_CODE_SUCCESS);
				result.setResultMsg("用户信息绑定成功");
			}
		} else{
			result.setResultMsg(validateRes);
		}
		String res = JSON.toJSONString(result);
		logger.debug("PersonInfoBindingServiceImpl 返回信息报文：" + res);
		return res;
	 }
	
	
	/**
	 * 验证参数
	 * @param user
	 * @return
	 */
	public String validateParam(UserIdinfo user,String merchantCode){
		if(user ==null){
			return "入参错误";
		}
		if(StringUtil.isEmpty(user.getLoginName())){
			return "用户账号不能为空";
		}
		if(StringUtil.isEmpty(user.getRealName())){
			return "用户真实姓名不能为空";
		}
		if(StringUtil.isEmpty(user.getIDNumber())){
			return "用户身份证号码不能为空";
		}
		if(StringUtil.isEmpty(user.getAddress())){
			return "用户地址不能为空";
		}
		if(!validateIDCard(user.getIDNumber())){
			return "请输入正确的身份证号码";
		}
		if(getStatus(merchantCode)){
			return "只有未完善和未通过的信息才可以修改";
		}
		
		return "OK";
	}


	/**
	 * 检验身份证
	 * @param idNumber
	 * @return
	 */
	public boolean validateIDCard(String idNumber) {
		if(StringUtil.isEmpty(idNumber)){
			return false;
		}
		if(idNumber.length() !=18 && idNumber.length() !=15){
			return false;
		}
		if(idNumber.length() ==18 && !isDigital(idNumber.substring(0, 17))){
			return false;
		}
		return true;
	}

	
	 /** 
     * 数字验证 
     *  
     * @param str 
     * @return 
     */  
	public  boolean isDigital(String str) {  
        return str.matches("^[0-9]*$");  
    } 

	/**
	 * 判断状态
	 * @param merchantCode
	 * @return
	 */
    public boolean getStatus(String merchantCode) {
		//判断是否可以更改信息
		MerchantInfo u=new MerchantInfo();
		u.setMerchantCode(merchantCode);
		List<MerchantInfo> uList=merchantInfoService.queryMerchantInfoList(u);
        if(uList !=null && uList.size()>0){
        	if(!StringUtil.isEmpty(uList.get(0).getCertifyStatus().toString())){
        		int status=uList.get(0).getCertifyStatus();
        		if(status ==1 || status==2){
        			return true;
        		}else{
        			return false;
        		}
			}else{
				return true;
			}
        }else{
        	return true;
        }
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

	public MerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}

	public void setMerchantInfoService(MerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}
	
	
}
