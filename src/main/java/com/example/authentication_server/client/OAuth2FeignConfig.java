package com.example.authentication_server.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;

import com.example.authentication_server.dto.GetAccessTokenRequest;

@Configuration
public class OAuth2FeignConfig {
	@Value("${spring.security.oauth2.client.registration.authentication-server.client-id}") String clientId;
	@Value("${spring.security.oauth2.client.registration.authentication-server.client-secret}") String clientSecret;
	@Value("${spring.security.oauth2.client.registration.authentication-server.scope}") String scope;

	@Bean
	public RequestInterceptor oauth2RequestInterceptor(AuthServerClient authServerClient) {
		return template -> {
			GetAccessTokenRequest getAccessTokenRequest = GetAccessTokenRequest.builder()
					.grant_type("client_credentials")
					.client_id(clientId)
					.client_secret(clientSecret)
					.scope(scope)
					.build();
			AuthServerClient.TokenResponse tokenResponse = authServerClient.getToken(getAccessTokenRequest);
			template.header("Authorization", "Bearer " + tokenResponse.access_token);
		};
	}
}
