package cn.seocoo.platform.home;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.odchina.micro.auth.service.AuthRemoteService;
import com.odchina.micro.shiro.ShiroUser;
import com.tydic.framework.base.exception.ServiceException;
import com.tydic.framework.util.MD5Util;
import com.tydic.framework.util.StringUtil;

import cn.seocoo.platform.common.base.BaseAction;
import cn.seocoo.platform.common.util.BapUtil;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.Group;
import cn.seocoo.platform.model.Merchant;
import cn.seocoo.platform.model.UserInfo;
import cn.seocoo.platform.model.UserRelationship;
import cn.seocoo.platform.service.group.inf.GroupService;
import cn.seocoo.platform.service.merchant.inf.MerchantService;
import cn.seocoo.platform.service.userRelationship.inf.UserRelationshipService;

public class IndexAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(IndexAction.class);
	private UserRelationshipService userRelationshipService;
	private AuthRemoteService authRemoteService;
	private GroupService  groupService;
	private MerchantService merchantService;



	/*
	 * 首次第一次加载
	 */
	public String index() throws ServiceException
	{
		// 获取用户信息
		ShiroUser su = queryCurrentShiroUser();
		//获取用户全称
		UserRelationship ur=new UserRelationship();
		ur.setLoginName(su.getLoginName());
		List<UserRelationship> urList=userRelationshipService.queryUser(ur);
		if(urList !=null && urList.size()>0){
			String groupCode=urList.get(0).getGroupCode();
			if(StringUtil.isEmpty(urList.get(0).getMerchantCode())){
				Group p=new Group();
				p.setGroupCode(groupCode);
				List<Group> glist=groupService.queryGroupList(p);
				if(glist !=null && glist.size()>0){
					su.setName(glist.get(0).getFullName());
				}
			}else{
				String merchantCode=urList.get(0).getMerchantCode();
				Merchant t=new Merchant();
				t.setGroupCode(groupCode);
				t.setOutMchntId(merchantCode);
				List<Merchant> mlist=merchantService.queryMerchantList(t);
				if(mlist !=null && mlist.size()>0){
					su.setName(mlist.get(0).getMchntFullName());
				}
			}
		}
		request.setAttribute("su", su);
		return "index";
	}

	
	/**
	 * 退出登录
	 * 
	 * @throws IOException
	 */
	public void logout() throws IOException
	{
		String redirect = authRemoteService.getAuthUrl() + "/home!logout.do";
		redirect += "?service=" + authRemoteService.getServiceUrl();
		this.response.sendRedirect(redirect);

	}
	

	public String accountSet(){
		//获取用户信息
	   ShiroUser su = queryCurrentShiroUser();
	   request.setAttribute("user", su);
	   return "accountSet";
   }


	/**
	 * 账号设置   确认修改
	 */
	public void surePassword() throws ServiceException, IOException{
		JSONObject json=new JSONObject();
		//获取用户信息
		ShiroUser su = queryCurrentShiroUser();
		String password=request.getParameter("password");
		String newPassword=request.getParameter("newPassword");

		//检验原密码是否正确
		if(password !=null && password.length()>0){
			String resourcePath = SystemConfigUtil.getSingleProperty("user_server_path").getPropertyValue();
			String url=resourcePath+"/auth!queryUserInfo.do";
			String bodyContent="loginName="+su.getLoginName();
	    	String str=BapUtil.httpSendMsg(url, bodyContent);
	    	JSONObject  jsonObject = JSONObject.parseObject(str);
	    	Map map = (Map)jsonObject;
	    	if(map.get("msg").toString().contains("haveData")){
	    		List<UserInfo> userInfoList=JSONObject.parseArray(map.get("managerInfo").toString(), UserInfo.class);
	    	    String oldPassword=userInfoList.get(0).getPassword();
	    	    Long userId=userInfoList.get(0).getId();
	    	   
    	    	password=MD5Util.getMD5String(password).toLowerCase();
	    	  
	    		if(oldPassword.compareTo(password)==0){
	    	    	//调用接口进sso 重置密码为 用户输入的密码
	    			String resource = SystemConfigUtil.getSingleProperty("user_server_path").getPropertyValue();
	    			String urlsso=resource+"/auth!updatePassword.do";
	    			String bodyContentsso="id="+userId+"&password="+newPassword;
	    	    	String stri=BapUtil.httpSendMsg(urlsso, bodyContentsso);
	    	    	if(stri.contains("SUCCESS")){
	    	    		json.put("resultCode", "SUCCESS");
	    	    	}else{
	    	    		json.put("resultCode", "FAIL");
	    	    		json.put("resultMsg", "修改密码失败");
	    	    	}
	    	    }else{
	    	    	json.put("resultCode", "FAIL");
					json.put("resultMsg", "您输入的原密码不正确");
	    	    }
	    	}else{
	    		json.put("resultCode", "FAIL");
				json.put("resultMsg", "没有此用户");
	    	}
		}else{
			json.put("resultCode", "FAIL");
			json.put("resultMsg", "原密码不能为空");
		}
		this.sendMessages(json.toString());
	}
	
	public AuthRemoteService getAuthRemoteService() {
		return authRemoteService;
	}


	public void setAuthRemoteService(AuthRemoteService authRemoteService) {
		this.authRemoteService = authRemoteService;
	}


	public UserRelationshipService getUserRelationshipService() {
		return userRelationshipService;
	}


	public void setUserRelationshipService(
			UserRelationshipService userRelationshipService) {
		this.userRelationshipService = userRelationshipService;
	}


	public GroupService getGroupService() {
		return groupService;
	}


	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}


	public MerchantService getMerchantService() {
		return merchantService;
	}


	public void setMerchantService(MerchantService merchantService) {
		this.merchantService = merchantService;
	}
	
	

}
