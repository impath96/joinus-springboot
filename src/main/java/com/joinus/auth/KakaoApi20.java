package com.joinus.auth;

import com.github.scribejava.core.builder.api.DefaultApi20;

public class KakaoApi20 extends DefaultApi20 {
	
	private KakaoApi20() {
		
	}
	// inner 클래스를 활용한 싱글톤 구현
	private static class InstanceHolder {
		private static final KakaoApi20 instance = new KakaoApi20();
	}
	
	public static KakaoApi20 instance() {
		return InstanceHolder.instance;
	}
	
	@Override
	public String getAccessTokenEndpoint() {
		return "https://kauth.kakao.com/oauth/token";
	}

	@Override
	protected String getAuthorizationBaseUrl() {
		return "https://kauth.kakao.com/oauth/authorize";
	}
}