package com.joinus.config;

import com.joinus.auth.AuthInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuthConfig {

    @Bean
    public AuthInfo kakaoAuthInfo(
        @Value("${oauth.kakao.clientId}") String clientId,
        @Value("${oauth.kakao.clientSecret}") String clientSecret,
        @Value("${oauth.kakao.redirectUrl}") String redirectUrl
        ) {
        return new AuthInfo("kakao", clientId, clientSecret, redirectUrl);

    }

    @Bean
    public AuthInfo googleAuthInfo(
        @Value("${oauth.google.clientId}") String clientId,
        @Value("${oauth.google.clientSecret}") String clientSecret,
        @Value("${oauth.google.redirectUrl}") String redirectUrl
    ) {
        return new AuthInfo("google", clientId, clientSecret, redirectUrl);

    }

}
