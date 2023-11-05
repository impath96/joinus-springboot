package com.joinus.auth;

import com.github.scribejava.core.builder.api.DefaultApi20;

import lombok.Data;

@Data
public class AuthInfo {
	
	private String service;
	private String clientId;
	private String clientSecret;
	private String redirectUrl;
	private DefaultApi20 api20Instance;
	
	public AuthInfo(String service, String clientId, String clientSecret, String redirectUrl) {
		this.service = service;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.redirectUrl = redirectUrl;
		
		if(service.equals("kakao")) {
			this.api20Instance = KakaoApi20.instance();
		}else {
			this.api20Instance = GoogleApi20.instance();
		}
	}
	

}
