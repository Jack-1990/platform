package com.odchina.micro.shiro;

import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;

/**
 * 
 * @author pengwu2
 * 
 */
public class CustomCookieRememberMeManager extends CookieRememberMeManager {

	public CustomCookieRememberMeManager() {
		super();
	}

	/**
	 * @param name 名称
	 * @param path 路径
	 * @param maxAge cookie保存天数,负数表示随浏览器关闭而清除
	 */
	public CustomCookieRememberMeManager(String name, String path, int maxAge) {
		Cookie cookie = new SimpleCookie(name);
		cookie.setPath(path);
		cookie.setHttpOnly(true);
		cookie.setMaxAge(maxAge * 24 * 60 * 60);
		setCookie(cookie);
	}

}
