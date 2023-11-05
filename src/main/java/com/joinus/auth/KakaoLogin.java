package com.joinus.auth;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.oauth.AccessTokenRequestParams;
import com.joinus.domain.MembersVo;

public class KakaoLogin extends AuthLogin {

	public static final String SERVICE = "kakao";

	private static final Logger log = LoggerFactory.getLogger(KakaoLogin.class);

	public KakaoLogin(AuthInfo autoInfo) {
		super(autoInfo);
	}

	@Override
	public OAuth2AccessToken getAccessToken(String code) throws IOException, InterruptedException, ExecutionException {

//			OAuthRequest request = new OAuthRequest(Verb.POST, oauthService.getApi().getAccessTokenEndpoint());
//			request.addHeader(OAuthConstants.HEADER, OAuthConstants.BASIC + ' '
//                    + Base64.encode(String.format("%s:%s", oauthService.getApiKey(), oauthService.getApiSecret()).getBytes(Charset.forName("UTF-8"))));
//            request.addParameter(OAuthConstants.REDIRECT_URI, oauthService.getCallback());
//            request.addParameter(OAuthConstants.GRANT_TYPE, OAuthConstants.AUTHORIZATION_CODE);
//            
//          음... 카카오가 request를 파싱하는 방법이 다른건가? 왜 카카오만 안되지?
// 			request.addParameter를 해줘야 정상적으로  동작
//			음...?

		AccessTokenRequestParams params = new AccessTokenRequestParams(code);
		params.addExtraParameter("client_id", authInfo.getClientId());
		params.addExtraParameter("client_secret", authInfo.getClientSecret());
		
		return oauthService.getAccessToken(params);
	}

	@Override
	public MembersVo parseResponse(Response response) throws JsonProcessingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(response.getBody());
		
		log.info("node : {}", node);
		System.out.println("node : " +node.toString());
		String email = node.get("kakao_account").get("email") == null ? "미동의"
				: node.get("kakao_account").get("email").textValue();
		
		String name = node.get("kakao_account").get("profile").get("nickname") == null ? "미동의"
				: node.get("kakao_account").get("profile").get("nickname").textValue();
		
		String picture = node.get("kakao_account").get("profile").get("profile_image_url") == null ? "default.jpg"
				: node.get("kakao_account").get("profile").get("profile_image_url").textValue();
		
		return MembersVo.builder()
	    		.member_email(email)
	    		.member_name(name)
	    		.member_image(picture)
	    		.member_signup_type(SERVICE)
	    		.build();
	}

	@Override
	public String getProfileEndPoint() {
		return "https://kapi.kakao.com/v2/user/me";
	}

}
