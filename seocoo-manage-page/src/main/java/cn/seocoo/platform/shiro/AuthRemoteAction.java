package cn.seocoo.platform.shiro;


import cn.seocoo.platform.common.base.BaseAction;
import com.alibaba.fastjson.JSON;
import com.odchina.micro.auth.entity.MenuInfo;
import com.odchina.micro.auth.service.AuthRemoteService;
import com.odchina.micro.shiro.ShiroUser;
import com.odchina.micro.util.HttpUtils;
import com.odchina.micro.util.StringTools;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AuthRemoteAction  extends BaseAction{

	private AuthRemoteService authRemoteService;
	private String ticket;
	private List<MenuInfo> menus;
	private String service;
	private String authInnerUrl="http://115.28.94.228:8093/sso";
//	private String authInnerUrl="http://172.17.6.70:8091/sso";
	public String checkTicket() {
		try {
			if (StringTools.isEmpty(ticket)) {
				response.sendRedirect(getServiceUrl());
				return null;
			}
			UsernamePasswordToken token = new UsernamePasswordToken(ticket, ticket, false);
			SecurityUtils.getSubject().login(token);
			WebUtils.redirectToSavedRequest(request, response, getServiceUrl());
		} catch (IOException e) {
		}
		return null;
	}
	
	
	public void getPermissionMenusOnceAll() throws IOException {
		 menus = new ArrayList<MenuInfo>();
		 //
		ShiroUser  su= queryCurrentShiroUser();
		String text = rest(request, authInnerUrl + "/auth!getPermissionMenusOnceAll.do?loginName="+su.getLoginName());
		MenuInfo[] array = JSON.parseObject(text, MenuInfo[].class);
		for (int i = 0; array != null && i < array.length; i++) {
			MenuInfo menu = array[i];
			menus.add(menu);
		}
		String json=JSON.toJSONString(menus);
		System.out.println(json);
		this.sendMessages(json);
	}
	
	public void getRemotAuthUrl() throws IOException {
		String url=	 authRemoteService.getAuthUrl();
		System.out.println(url);
		String json=JSON.toJSONString(url);
		this.sendMessages(json);
	}

	
	public String rest(HttpServletRequest request, String url) {
		try {
			Map<String, String> params = HttpUtils.getParams(request);
			return new String(HttpUtils.post(url, params), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public String logout() throws IOException {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			subject.logout();
		}
		String redirect = "/";
		if (StringUtils.hasText(service)) {
			redirect = service;
		}
		if (StringUtils.hasText(getAuthUrl())) {
			redirect = getAuthUrl() + "/logout";
			if (StringUtils.hasText(service)) {
				redirect += "?service=" + service;
			}
		}
		this.response.sendRedirect(redirect);
		return null;
	}
	
	public String getServiceUrl() {
		return authRemoteService.getServiceUrl();
	}

	public AuthRemoteService getAuthRemoteService() {
		return authRemoteService;
	}

	public void setAuthRemoteService(AuthRemoteService authRemoteService) {
		this.authRemoteService = authRemoteService;
	}
	public String getAuthUrl() {
		return authRemoteService.getAuthUrl();
	}
	
	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}


	public String getService() {
		return service;
	}


	public void setService(String service) {
		this.service = service;
	}
	
}
