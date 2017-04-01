package com.odchina.micro.shiro;

import com.alibaba.fastjson.JSON;
import com.odchina.micro.auth.service.AuthRemoteService;
import com.odchina.micro.util.HttpUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class SSORealm extends AuthorizingRealm {
	
	private AuthRemoteService authRemoteService;
	private String authUrl="http://115.28.94.228:8093/sso";
	//private String authUrl="http://115.28.136.205:8091/sso";
	/**
	 * 封装登录用户信息,权限信息
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		String loginName = shiroUser.getLoginName();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Collection<String> roleNames = getRoleNames(loginName);
		if (roleNames == null) {
			roleNames = new HashSet<String>();
		}
		Collection<String> permissionNames = getPermissionNames(loginName);
		if (permissionNames == null) {
			permissionNames = new HashSet<String>();
		}
		for (String roleName : roleNames) {
			info.addRole(roleName);
		}
		info.addStringPermissions(permissionNames);
		return info;
	}

	/**
	 * ticket  换取用户信息
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		if (token instanceof UsernamePasswordToken) {
			UsernamePasswordToken upt = (UsernamePasswordToken) token;
			char[] pwd = upt.getPassword();
			String uname = upt.getUsername();
			Map<String, String> params = new HashMap<String, String>();
			params.put("ticket", uname);
			String responseText = rest(authUrl
					+ "/auth!getUserByTicket.do", params);
			if (StringUtils.hasText(responseText)) {
				ShiroUser su = JSON.parseObject(responseText, ShiroUser.class);
				if (su != null) {
					return new SimpleAuthenticationInfo(su, pwd, getName());
				}
			}
		}
		return null;
	}

	/**
	 * 获取角色名称
	 * @param loginName
	 * @return
	 */
	protected Collection<String> getRoleNames(String loginName) {
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

	/**
	 * 获取用户可执行的操作
	 * @param loginName
	 * @return
	 */
	protected Collection<String> getPermissionNames(String loginName) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("loginName", loginName);
		Collection<String> permissions = new HashSet<String>();
		String data = rest(authUrl + "/auth!getPermissionNames.do", params);
		String[] array = JSON.parseObject(data, String[].class);
		for (int i = 0; array != null && i < array.length; i++) {
			String p = array[i];
			if(StringUtils.hasText(p)){
				permissions.add(p);
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

	public AuthRemoteService getAuthRemoteService() {
		return authRemoteService;
	}

	public void setAuthRemoteService(AuthRemoteService authRemoteService) {
		this.authRemoteService = authRemoteService;
	}

}
