package com.joinus.auth;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.joinus.domain.MembersVo;

public abstract class AuthLogin {
	
	protected AuthInfo authInfo;
	protected String profileEndPoint;
	protected String accessTokenEndPoint;

	protected OAuth20Service oauthService;
	
	public AuthLogin(AuthInfo authInfo) {
		
		this.oauthService = new ServiceBuilder(authInfo.getClientId())
				.apiSecret(authInfo.getClientSecret())
				.callback(authInfo.getRedirectUrl())
				.build(authInfo.getApi20Instance());
		this.authInfo = authInfo;
		
	}

	public OAuth2AccessToken getAccessToken(String code) throws IOException, InterruptedException, ExecutionException {

		return this.oauthService.getAccessToken(code);
	}

	public MembersVo getMemberInfo(String code, HttpSession session) throws InterruptedException, ExecutionException, IOException {

		// 1. code를 통해 accessToken 얻기
		OAuth2AccessToken accessToken = getAccessToken(code);

		// 2. accessToken을 통해 응답 반환
		Response response = requestMemberInfo(accessToken);

		return parseResponse(response);
	}

	public abstract MembersVo parseResponse(Response response) throws JsonProcessingException, IOException;

	public Response requestMemberInfo(OAuth2AccessToken accessToken)
			throws InterruptedException, ExecutionException, IOException {
		
		OAuthRequest request = new OAuthRequest(Verb.GET, getProfileEndPoint());
		oauthService.signRequest(accessToken, request);
		
		return oauthService.execute(request);
	}
	
	public abstract String getProfileEndPoint();

}
