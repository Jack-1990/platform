package com.odchina.micro.shiro;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.BeansException;

import com.odchina.micro.auth.service.AuthRemoteService;
import com.tydic.framework.util.StringUtil;

public class SSOShiroFilterFactoryBean extends ShiroFilterFactoryBean {

	private static final Log logger = LogFactory.getLog(SSOShiroFilterFactoryBean.class);

	private AuthRemoteService authRemoteService;

	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (StringUtil.isEmpty(authRemoteService.getAuthUrl())) {
			String loginUrl = getLoginUrl();
			Pattern pattern = Pattern.compile("^(.*?)\\?.*?service=(.*?)(&|$)");
			Matcher matcher = pattern.matcher(loginUrl);
			if (matcher.find()) {
				String authUrl = matcher.group(1);
				String serviceUrl = matcher.group(2);
				authRemoteService.setAuthUrl(authUrl);
				authRemoteService.setServiceUrl(serviceUrl);
				
				logger.info("sso auth url : " + authUrl);
				logger.info("sso service url : " + serviceUrl);
			}
		}
		return super.postProcessBeforeInitialization(bean, beanName);
	}

	public AuthRemoteService getAuthRemoteService() {
		return authRemoteService;
	}

	public void setAuthRemoteService(AuthRemoteService authRemoteService) {
		this.authRemoteService = authRemoteService;
	}

}
