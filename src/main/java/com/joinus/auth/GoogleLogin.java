package com.joinus.auth;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.model.Response;
import com.joinus.domain.MembersVo;

public class GoogleLogin extends AuthLogin{

	public static final String SERVICE = "google";
	
	private static final Logger log = LoggerFactory.getLogger(GoogleLogin.class);

	public GoogleLogin(AuthInfo autoInfo) {
		super(autoInfo);
	}
	
	@Override
	public MembersVo parseResponse(Response response) throws JsonProcessingException, IOException {
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode node = mapper.readTree(response.getBody());
	    log.info("response : {}", node);
	    System.out.println("googleLogin - parseResponse");
	    System.out.println("resopnse : " + response.toString());
	    String email = node.get("email") == null ? "미동의" : node.get("email").textValue();
	    String name = node.get("name") == null ? email.split("@")[0] : node.get("name").textValue();
	    String picture = node.get("picture") == null ? "default.jpg" : node.get("picture").textValue();
	    log.info("email =  {}", email);
	    log.info("name =  {}", name);
	    log.info("picture =  {}", picture);
	    return MembersVo.builder()
	    		.member_email(email)
	    		.member_name(name)
	    		.member_image(picture)
	    		.member_signup_type(SERVICE)
	    		.build();
	}


	@Override
	public String getProfileEndPoint() {
		return "https://www.googleapis.com/oauth2/v3/userinfo";
	}


}
