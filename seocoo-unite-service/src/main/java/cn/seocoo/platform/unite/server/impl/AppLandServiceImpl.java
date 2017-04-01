package cn.seocoo.platform.unite.server.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.seocoo.platform.common.util.BapUtil;
import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.StringUtil;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.DimDicInfo;
import cn.seocoo.platform.model.MerchantInfo;
import cn.seocoo.platform.model.MerchantUser;
import cn.seocoo.platform.model.UserIdinfo;
import cn.seocoo.platform.model.UserInfo;
import cn.seocoo.platform.service.dimDicInfo.inf.DimDicInfoService;
import cn.seocoo.platform.service.merchantInfo.inf.MerchantInfoService;
import cn.seocoo.platform.service.merchantUser.inf.MerchantUserService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.service.userIdinfo.inf.UserIdinfoService;
import cn.seocoo.platform.unite.Result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tydic.framework.util.MD5Util;

public class AppLandServiceImpl implements Service{
	private static final Logger logger = Logger.getLogger(AppLandServiceImpl.class);
    private MerchantUserService merchantUserService;
    private DimDicInfoService dimDicInfoService;
    private MerchantInfoService merchantInfoService;
    private UserIdinfoService userIdinfoService;

	/**
     * APP 登陆
     */
	public Object sevice(String param) {
		logger.debug("sendMessageService 请求报文 :"+param);
		Result reslut=new Result();
		MerchantUser user=JSONObject.parseObject(param,MerchantUser.class);
		//入参检验
	    String checkMsg=checkParam(user);
	    if("OK".equals(checkMsg)){
	    	//检验密码是否正确，把传过来的密码md5加密
			String password=MD5Util.getMD5String(user.getPassWord()).toLowerCase();
			
			//查询此时SSO密码
			String resourcePath = SystemConfigUtil.getSingleProperty("user_server_path").getPropertyValue();
			String url=resourcePath+"/auth!queryUserInfo.do";
			String bodyContent="loginName="+user.getLoginName();
	    	String str =null;
			try {
				str = BapUtil.httpSendMsg(url, bodyContent);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	JSONObject  jsonObject = JSONObject.parseObject(str);
	    	Map map = (Map)jsonObject;
	    	if(map.get("msg").toString().contains("haveData")){
	    		List<UserInfo> userInfoList=JSONObject.parseArray(map.get("managerInfo").toString(), UserInfo.class);
	    	    String oldPassword=userInfoList.get(0).getPassword();
	    	    if(oldPassword.compareTo(password)==0){
	    	    	reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
					reslut.setResultMsg("登陆成功");
					
					//返回对象
					user.setHeadPic("");
					MerchantUser mu=new MerchantUser();
					mu.setLoginName(user.getLoginName());
					List<MerchantUser>  muLiat=merchantUserService.queryMerchantUserList(mu);
					if(muLiat!=null && muLiat.size()>0){
						user=muLiat.get(0);
						String visitPath=SystemConfigUtil.getSingleProperty("userpic_visit_path").getPropertyValue();
                        if(!StringUtil.isEmpty(user.getHeadPic())){
                        	user.setHeadPic(visitPath + user.getHeadPic());
                        }
				     }
					
					 //获取等级的名称
					 String levelName="";
					 String levelCode="";
					 user.setCertifyStatus(0);
					//返回对象
					 MerchantInfo mInfo=new MerchantInfo();
					 mInfo.setMerchantCode(user.getMerchantCode());
					 List<MerchantInfo>  mInfoLiat=merchantInfoService.queryMerchantInfoList(mInfo);
					 if(mInfoLiat!=null && mInfoLiat.size()>0){
						mInfo=mInfoLiat.get(0);
						user.setCertifyStatus(mInfo.getCertifyStatus());
						levelCode=mInfo.getLevel();
						
						DimDicInfo di=new DimDicInfo();
						di.setCode(Constant.DIMDIC_LEVEL);
						di.setAttrCode(mInfo.getLevel());
						List<DimDicInfo>  dimList=dimDicInfoService.queryDimDicInfoList(di);
						if(dimList !=null && dimList.size()>0){
							levelName=dimList.get(0).getAttrName();
						}
				     }
					
					//返回
					String realName="";
					UserIdinfo userId=new UserIdinfo();
					userId.setMerchantCode(user.getMerchantCode());
					List<UserIdinfo>  userIdList=userIdinfoService.queryUserIdinfoList(userId);
					if(userIdList!=null && userIdList.size()>0){
						realName=userIdList.get(0).getRealName();
				     }
					
					 user.setRealName(realName);
					 user.setLevelName(levelName);
					 user.setLevelCode(levelCode);
					 
				     reslut.setResultData(user);
	    	    }else{
	    	    	reslut.setResultCode(Constant.RESULT_CODE_FAIL);
	    	    	reslut.setResultMsg("密码错误");
	    	    }
	    	}else{
	    		reslut.setResultCode(Constant.RESULT_CODE_FAIL);
    	    	reslut.setResultMsg("用户名不存在");
	    	}
	    }else{
	    	reslut.setResultCode(Constant.RESULT_CODE_FAIL);
			reslut.setResultMsg(checkMsg);
	    }
		String msg = JSON.toJSONString(reslut);
		logger.debug("sendMessageService 返回报文 :"+msg);
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
		if(StringUtil.isEmpty(user.getPassWord())){
			return "用户登录密码不能为空";
		}
		if(!checkUser(user.getLoginName())){
			return "该账号不存在，请注册";
		}
		return "OK";
	}


	/**
	 * 检验账号是否有
	 * 
	 * @return
	 */
	public boolean checkUser(String loginName){
		MerchantUser mu=new MerchantUser();
		mu.setLoginName(loginName);
		List<MerchantUser>  muLiat=merchantUserService.queryMerchantUserList(mu);
		if(muLiat !=null && muLiat.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	
	public MerchantUserService getMerchantUserService() {
		return merchantUserService;
	}
	public void setMerchantUserService(MerchantUserService merchantUserService) {
		this.merchantUserService = merchantUserService;
	}
    public DimDicInfoService getDimDicInfoService() {
		return dimDicInfoService;
	}

	public void setDimDicInfoService(DimDicInfoService dimDicInfoService) {
		this.dimDicInfoService = dimDicInfoService;
	}

	public MerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}

	public void setMerchantInfoService(MerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}

	public UserIdinfoService getUserIdinfoService() {
		return userIdinfoService;
	}

	public void setUserIdinfoService(UserIdinfoService userIdinfoService) {
		this.userIdinfoService = userIdinfoService;
	}
	

}
