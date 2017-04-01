package com.odchina.micro.auth.service;

import org.springframework.stereotype.Service;

@Service("authRemoteService")
public class AuthRemoteService {
	
	private String serviceUrl;
	private String innerAuthUrl;
	private String authUrl;

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getInnerAuthUrl() {
		return innerAuthUrl;
	}

	public void setInnerAuthUrl(String innerAuthUrl) {
		this.innerAuthUrl = innerAuthUrl;
	}

	public String getAuthUrl() {
		return authUrl;
	}

	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}
}
