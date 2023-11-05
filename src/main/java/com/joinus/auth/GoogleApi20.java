package com.joinus.auth;

import com.github.scribejava.core.builder.api.DefaultApi20;

public class GoogleApi20 extends DefaultApi20 {
	private GoogleApi20() {

	}

	// inner 클래스를 활용한 싱글톤 구현
	private static class InstanceHolder {
		private static final GoogleApi20 instance = new GoogleApi20();
	}

	public static GoogleApi20 instance() {
		return InstanceHolder.instance;
	}

	@Override
	public String getAccessTokenEndpoint() {
		return "https://oauth2.googleapis.com/token";
	}

	@Override
	protected String getAuthorizationBaseUrl() {
		return "https://accounts.google.com/o/oauth2/auth";
	}

}