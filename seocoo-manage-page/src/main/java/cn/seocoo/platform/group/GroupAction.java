package cn.seocoo.platform.group;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.odchina.micro.auth.service.AuthRemoteService;
import com.odchina.micro.shiro.ShiroUser;
import com.odchina.micro.util.HttpUtils;
import com.odchina.micro.util.StringTools;
import com.tydic.framework.base.exception.ServiceException;
import com.tydic.framework.util.StringUtil;

import cn.seocoo.platform.common.base.BaseAction;
import cn.seocoo.platform.common.util.BapUtil;
import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.Area;
import cn.seocoo.platform.model.Group;
import cn.seocoo.platform.model.UserInfo;
import cn.seocoo.platform.model.UserRelationship;
import cn.seocoo.platform.service.area.inf.AreaService;
import cn.seocoo.platform.service.group.inf.GroupService;
import cn.seocoo.platform.service.userRelationship.inf.UserRelationshipService;

public class GroupAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(GroupAction.class);
    private AreaService areaService;
	private AuthRemoteService authRemoteService;
	private GroupService groupService;
	private UserRelationshipService userRelationshipService;


	/*
	 * 首次第一次加载
	 */
	public String group() throws ServiceException
	{
		// 获取用户信息
		ShiroUser su = queryCurrentShiroUser();
		
		return "group";
	}
	

	/**
	 * 查询代理商列表
	 * @return
	 */
	public String queryList(){
		String pageIndex=request.getParameter("pageIndex");
		String name=request.getParameter("name");
		String nameval=request.getParameter("nameval");
		
		int pageSize = Constant.PAGESIZE_TEN;
		int beginRow = Integer.parseInt(pageIndex);
		// 获取用户信息
		ShiroUser su = queryCurrentShiroUser();
		
		Map map=new HashMap();
		if("groupCode".equals(name)){
			map.put("groupCodeLike", nameval);
		}else if("fullName".equals(name)){
			map.put("fullNameLike", nameval);
		}
		map.put("status", "0");
		map.put("pageSize", pageSize);
		map.put("offset", beginRow * pageSize);
		//map.put("createUser", su.getLoginName());
		
		//是否是审核员
		int isAudito = 0;
		String key = SystemConfigUtil.getSingleProperty("payAuditorKey").getPropertyValue();
		Collection<String> roleNames = getRoleNames(su.getLoginName());
		if (roleNames == null) {
			roleNames = new HashSet<String>();
		}
		for (String roleName : roleNames) {
			if(roleName.contains(key)){
				isAudito = 1;
			}
		}
		
		List<Group> groupList= null;
		int total=0;
		
		//审核员显示所有
		if(isAudito ==1){
			groupList=groupService.queryGroupPage(map);
			total=groupService.queryGroupPageCount(map);
		}else{
			UserRelationship  u=new UserRelationship();
			u.setLoginName(su.getLoginName());
			List<UserRelationship>  list =userRelationshipService.queryUserRelationshipList(u);
			if(list !=null && list.size()>0 && StringUtil.isEmpty(list.get(0).getMerchantCode())){
				//获取当前登陆人的代理商的 groupCode
				map.put("parentCode",list.get(0).getGroupCode());
			}else{
				map.put("parentCode","P");
			}
			
			 groupList=groupService.queryGetChild(map);
			 total=groupService.queryGetChildCount(map);
		}
		
		// 求余 获取分页总数
		int totalPage = 0;
		int remainder = total % pageSize;
		if (remainder == 0)
		{
			totalPage = total / pageSize;
		} else
		{
			totalPage = total / pageSize + 1;
		}

		String areaCode="";
    	for(Group p:groupList){
    	    areaCode+="'"+p.getLantCode()+"',";
    	}
    	if(areaCode !=""){
    		areaCode=areaCode.substring(0, areaCode.length()-1);
	    	//查询地区中文的集合list
	    	Area area = new Area();
	    	area.setCode(areaCode);
			List<Area> provinceList = areaService.queryMergerName(area);
			request.setAttribute("provinceList", provinceList);
    	}	
		request.setAttribute("groupList", groupList);
		request.setAttribute("totalPage", totalPage);
		return "groupList";
	}
	
	
	/**
	 * 进入  新增 代理商页面
	 */
	public String addGroup(){
		String groupCode="P"+StringTools.getRandomString(14);
		request.setAttribute("groupCode", groupCode);
		
		Area area = new Area();
		area.setPcode(Constant.CHINA_CODE);
		List<Area> provinceList = areaService.queryAreaList(area);
		request.setAttribute("provinceList", provinceList);
		
		// 获取用户信息
		ShiroUser su = queryCurrentShiroUser();
		request.setAttribute("loginName", su.getLoginName());
		
		//是否是审核员
		int isAudito = 0;
		String key = SystemConfigUtil.getSingleProperty("payAuditorKey").getPropertyValue();
		Collection<String> roleNames = getRoleNames(su.getLoginName());
		if (roleNames == null) {
			roleNames = new HashSet<String>();
		}
		for (String roleName : roleNames) {
			if(roleName.contains(key)){
				isAudito = 1;
			}
		}
		
		//不是审核员的，代理商等级只能选择比自己低的。最后一级是平级
		int groupLevel=5;
		if(isAudito ==0){
			//获取当前用户的代理商code
			UserRelationship ur=new UserRelationship();
			ur.setLoginName(su.getLoginName());
			List<UserRelationship> urList=userRelationshipService.queryUser(ur);
			if(urList !=null && urList.size()>0){
				String group=urList.get(0).getGroupCode();
				//获取当前代理商等级
				Group g=new Group();
				g.setGroupCode(group);
				List<Group> gList=groupService.queryGroupList(g);
			    if(gList !=null && gList.size()>0){
			    	groupLevel =gList.get(0).getGroupLevel();
			    }
			}
		}
		request.setAttribute("isAudito", isAudito);
		request.setAttribute("groupLevel", groupLevel);
		return "groupAdd";
	}
	
	
	/**
	 *   新增
	 * @throws IOException 
	 */
	public void save() throws IOException{
		JSONObject json=new JSONObject();
		String groupObj=request.getParameter("groupObj");
		
		Group  group=JSONObject.parseObject(groupObj, Group.class);
		ShiroUser su = queryCurrentShiroUser();
		
		//获取当前用户的代理商code
		UserRelationship ur=new UserRelationship();
		ur.setLoginName(su.getLoginName());
		List<UserRelationship> urList=userRelationshipService.queryUser(ur);
		if(urList !=null && urList.size()>0){
			group.setParentCode(urList.get(0).getGroupCode());
		}
		
		//默认值
		group.setStatus("0");
		group.setCreateTime(new Date());
		group.setCreateUser(su.getLoginName());
		
		groupService.saveGroup(group);
		json.put("result", Constant.RESULT_CODE_SUCCESS);
		
		this.sendMessages(json.toJSONString());
	}
	
	
	/**
	 * 进入  编辑 代理商页面
	 */
	public String editGroup(){
		String groupId=request.getParameter("groupId");
		Group  group=new Group();
		if(!StringUtil.isEmpty(groupId)){
			group.setId(Integer.parseInt(groupId));
			
			group=groupService.queryGroup(group);
			
			//地区回显
			queryAreaMassage(request,group.getLantCode());
		}
		//是否是审核员
		int isAudito = 0;
		String key = SystemConfigUtil.getSingleProperty("payAuditorKey").getPropertyValue();
		// 获取用户信息
	    ShiroUser su = queryCurrentShiroUser();
		Collection<String> roleNames = getRoleNames(su.getLoginName());
		if (roleNames == null) {
			roleNames = new HashSet<String>();
		}
		for (String roleName : roleNames) {
			if(roleName.contains(key)){
				isAudito = 1;
			}
		}
		request.setAttribute("isAudito", isAudito);
		request.setAttribute("group", group);
		return "groupEdit";
	}
	
	
	/**
	 *   保存   编辑内容
	 * @throws IOException 
	 */
	public void saveEdit() throws IOException{
		JSONObject json=new JSONObject();
		String groupObj=request.getParameter("groupObj");
		String groupId=request.getParameter("groupId");
		
		Group  group=JSONObject.parseObject(groupObj, Group.class);
		if(!StringUtil.isEmpty(groupId)){
			group.setId(Integer.parseInt(groupId));
			
			groupService.updateGroup(group);
			
			json.put("result", Constant.RESULT_CODE_SUCCESS);
		}else{
			json.put("result", Constant.RESULT_CODE_FAIL);
		}
		this.sendMessages(json.toJSONString());
	}
	
	/**
	 *   查询账号
	 * @throws IOException 
	 */
	public void queryAccount() throws IOException{
		JSONObject json=new JSONObject();
		String groupCode=request.getParameter("groupCode");
		
		UserRelationship p=new UserRelationship();
		if(!StringUtil.isEmpty(groupCode)){
			p.setGroupCode(groupCode);
			List<UserRelationship>  list=userRelationshipService.queryMerCodeEmpty(p);
			if(list !=null && list.size()>0){
				p=list.get(0);
				json.put("result", Constant.RESULT_CODE_SUCCESS);
				json.put("loginName", p.getLoginName());
			}
		}else{
			json.put("result", Constant.RESULT_CODE_FAIL);
		}
		json.put("groupCode", groupCode);
		this.sendMessages(json.toJSONString());
	}
	
	
	/**
	 *   保存账号
	 * @throws IOException 
	 */
	public void saveUser() throws IOException{
		JSONObject json=new JSONObject();
		String groupCode=request.getParameter("groupCode");
		String userCode=request.getParameter("userCode");
		String password=request.getParameter("password");
		
		//检验账号名称是否重复
		if(checkUserCode(userCode)){
			if(!StringUtil.isEmpty(groupCode)){
				//本地保存
	    		UserRelationship p=new UserRelationship();
	    		p.setGroupCode(groupCode);
	    		List<UserRelationship> pList= userRelationshipService.queryMerCodeEmpty(p);
	    		p.setLoginName(userCode);
	    		String resourcePath = SystemConfigUtil.getSingleProperty("user_server_path").getPropertyValue();
	    		if(pList !=null && pList.size()>0){
	    			p.setId(pList.get(0).getId());
	    			
	    			String url=resourcePath+"/auth!queryUserInfo.do";
	    			String bodyContent="loginName="+userCode;
	    	    	String str=BapUtil.httpSendMsg(url, bodyContent);
	    	    	JSONObject  jsonObject = JSONObject.parseObject(str);
	    	    	Map map = (Map)jsonObject;
	    	    	if("haveData".equals(map.get("msg").toString())){
	    	    		List<UserInfo> userInfoList=JSONObject.parseArray(map.get("managerInfo").toString(), UserInfo.class);
	    	    		Long userId = null;
	    	    		if(userInfoList !=null && userInfoList.size()>0){
	    	    			userId=userInfoList.get(0).getId();
	    	    		}
	    	    		
	    	    		String urlsso=resourcePath+"/auth!updatePassword.do";
	    	    		String bodyContentsso="id="+userId+"&password="+password;
	    	    		String stri=BapUtil.httpSendMsg(urlsso, bodyContentsso);
	    	    		if(stri.contains("SUCCESS")){
	    	    			json.put("result", Constant.RESULT_CODE_SUCCESS);
	    	    		}else{
	    	    			json.put("result", Constant.RESULT_CODE_FAIL);
	    	    		}
	    	    	}else{
    	    			json.put("result", Constant.RESULT_CODE_FAIL);
    	    		}
	    		}else{
	    			String trueName=userCode;
	    			String areaCode="";
	    			Group g=new Group();
	    			g.setGroupCode(groupCode);
	    			List<Group>  list=groupService.queryGroupList(g);
	    			if(list !=null && list.size()>0){
	    				trueName=list.get(0).getFullName();
	    				areaCode=list.get(0).getLantCode();
	    			}
	    			String role = SystemConfigUtil.getSingleProperty("payGroupRole").getPropertyValue();
	    			// 获取用户信息
					ShiroUser su = queryCurrentShiroUser();
					//保存  账号信息    
					String url=resourcePath+"/auth!saveRegisterUser.do";
					String bodyContent="loginName="+userCode+"&password="+password+"&phone=&trueName="+trueName+"&status=1&roleIds="+role+"&creator="+su.getLoginName()+"&areaCode="+areaCode;
			    	String str=BapUtil.httpSendMsg(url, bodyContent);
			    	if(str.contains(Constant.RESULT_CODE_SUCCESS)){
			    			userRelationshipService.saveUserRelationship(p);
			    			json.put("result", Constant.RESULT_CODE_SUCCESS);
			    	}else{
			    		json.put("result", Constant.RESULT_CODE_FAIL);
			    	}
			    }
			}else{
				json.put("result", Constant.RESULT_CODE_FAIL);
			}
		}else{
			json.put("result", "REPEAT");
		}
    	
		this.sendMessages(json.toJSONString());
	}


	
	
	/**
	 *   保存账号  编辑
	 * @throws IOException 
	 */
	public void saveUserEdit() throws IOException{
		JSONObject json=new JSONObject();
		String groupCode=request.getParameter("groupCode");
		String userCode=request.getParameter("userCode");
		String password=request.getParameter("password");
		
		if(!StringUtil.isEmpty(groupCode)){
			//本地保存
    		UserRelationship p=new UserRelationship();
    		p.setGroupCode(groupCode);
    		List<UserRelationship> pList= userRelationshipService.queryMerCodeEmpty(p);
    		p.setLoginName(userCode);
    		String resourcePath = SystemConfigUtil.getSingleProperty("user_server_path").getPropertyValue();
    		if(pList !=null && pList.size()>0){
    			p.setId(pList.get(0).getId());
    			
    			String url=resourcePath+"/auth!queryUserInfo.do";
    			String bodyContent="loginName="+userCode;
    	    	String str=BapUtil.httpSendMsg(url, bodyContent);
    	    	JSONObject  jsonObject = JSONObject.parseObject(str);
    	    	Map map = (Map)jsonObject;
    	    	if("haveData".equals(map.get("msg").toString())){
    	    		List<UserInfo> userInfoList=JSONObject.parseArray(map.get("managerInfo").toString(), UserInfo.class);
    	    		Long userId = null;
    	    		if(userInfoList !=null && userInfoList.size()>0){
    	    			userId=userInfoList.get(0).getId();
    	    		}
    	    		//修改密码
    	    		String urlsso=resourcePath+"/auth!updatePassword.do";
    	    		String bodyContentsso="id="+userId+"&password="+password;
    	    		String stri=BapUtil.httpSendMsg(urlsso, bodyContentsso);
    	    		if(stri.contains("SUCCESS")){
    	    			json.put("result", Constant.RESULT_CODE_SUCCESS);
    	    		}else{
    	    			json.put("result", Constant.RESULT_CODE_FAIL);
    	    		}
    	    	}
    		}
		}else{
			json.put("result", Constant.RESULT_CODE_FAIL);
		}
		this.sendMessages(json.toJSONString());
	}
	
	
	
	
	/**
	 * 检验账号是否重复
	 * @param userCode
	 * @return
	 * @throws IOException
	 */
	private boolean checkUserCode(String userCode) throws IOException {
		String resourcePath = SystemConfigUtil.getSingleProperty("user_server_path").getPropertyValue();
		String url=resourcePath+"/auth!validateRegisterUser.do";
		String bodyContent="loginName="+userCode;
    	String str=BapUtil.httpSendMsg(url, bodyContent);
    	if("haveUser".equals(str)){ 
    		return false;
		}else{
			return true;
		}
	}


	
	/**
	 * 获取角色名称
	 * @param loginName
	 * @return
	 */
	protected Collection<String> getRoleNames(String loginName) {
		String authUrl = SystemConfigUtil.getSingleProperty("user_server_path").getPropertyValue();
		Map<String, String> params = new HashMap<String, String>();
		params.put("loginName", loginName);
		Collection<String> permissions = new HashSet<String>();
		String data = rest(authUrl+ "/auth!getRoleNames.do", params);
		String[] array = JSON.parseObject(data, String[].class);
		for (int i = 0; array != null && i < array.length; i++) {
			String role = array[i];
			if(StringUtils.hasText(role)){
				permissions.add(role);
			}
		}
		return permissions;
	}
	
	private String rest(String url, Map<String, String> params) {
		try {
			return new String(HttpUtils.post(url, params), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 获取省份下的所有城市列表
	 * 
	 * @param provinceCode
	 * @throws IOException
	 */
	public void queryCityList() throws IOException
	{
		String provinceCode = request.getParameter("provinceCode");
		
		List<Area> provinceList =null;
		
		if(provinceCode !=null && provinceCode !="" && !"0".equals(provinceCode)){
			Area area = new Area();
			area.setPcode(provinceCode);
			provinceList = areaService.queryAreaList(area);
		}
		String json = JSON.toJSONString(provinceList);
		this.sendMessages(json);
	}
	
	/***
	 * 省市区 回显
	 * 
	 * @param request
	 */
	public void queryAreaMassage(HttpServletRequest request, String areaCode)
	{
		String provinceCode = "";//省区域编码
		String cityCode = "";//市区域编码
		String districtCode = "";//区县编码
		List<Area> provinceList = new ArrayList<Area>();
		List<Area> cityList = new ArrayList<Area>();
		List<Area> districtList = new ArrayList<Area>();
		
		// 查询 传入Code 信息
		Area area = new Area();
		if(areaCode==null||"".equals(areaCode))
	    areaCode="0";
		area.setCode(areaCode);
		List<Area> areas = areaService.queryAreaList(area);
		if (areas != null && areas.size() > 0)
		{
		  Area currentArea=areas.get(0);
		  int level=currentArea.getLevel();
		  
		  if(level==3)//区县回显
		  {
			    districtCode = currentArea.getCode();
				cityCode = currentArea.getPcode();
				provinceCode = currentArea.getPcode().substring(0, 2) + "0000";
				
				//获取对应市list集合
				area.setPcode(provinceCode);
				area.setLevel(2);
				cityList=areaService.queryAreaListByPcode(area);
						
				 //获取县区的list集合
				area.setPcode(cityCode);
				area.setLevel(3);
				districtList=areaService.queryAreaListByPcode(area);
				 
		  }
		  else if(level==2)//市回显
		  {
			  cityCode=currentArea.getCode();
			  provinceCode=currentArea.getPcode();
			 
		      //获取对应市list集合
			  area.setPcode(provinceCode);
			  area.setLevel(2);
			  cityList=areaService.queryAreaListByPcode(area);
			  
		      //获取县区的list集合
			  area.setPcode(cityCode);
			  area.setLevel(3);
			  districtList=areaService.queryAreaListByPcode(area);
		  }
		  else if(level==1)
		  {
			  provinceCode=currentArea.getCode();
			  
			  //获取市list集合
			  area.setPcode(provinceCode);
			  area.setLevel(2);
			  cityList=areaService.queryAreaListByPcode(area);
		  }
		}
		
		//获取省list
		area.setPcode(Constant.CHINA_CODE);
		area.setLevel(1);
		provinceList=areaService.queryAreaListByPcode(area);

		request.setAttribute("provinceList", provinceList);
		request.setAttribute("cityList", cityList);
		request.setAttribute("areaList", districtList);
		
		request.setAttribute("provinceCode", provinceCode);
		request.setAttribute("cityCode", cityCode);
		request.setAttribute("areaCode", districtCode);
	}


	public AreaService getAreaService() {
		return areaService;
	}


	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}


	public AuthRemoteService getAuthRemoteService() {
		return authRemoteService;
	}


	public void setAuthRemoteService(AuthRemoteService authRemoteService) {
		this.authRemoteService = authRemoteService;
	}


	public GroupService getGroupService() {
		return groupService;
	}


	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	
	
	public UserRelationshipService getUserRelationshipService() {
		return userRelationshipService;
	}


	public void setUserRelationshipService(
			UserRelationshipService userRelationshipService) {
		this.userRelationshipService = userRelationshipService;
	}


}
