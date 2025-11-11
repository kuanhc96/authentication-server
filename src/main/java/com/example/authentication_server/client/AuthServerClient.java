package com.example.authentication_server.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.authentication_server.component.AuthServerClientConfig;
import com.example.authentication_server.dto.GetAccessTokenRequest;

@FeignClient(name = "freelance-authserver", configuration = AuthServerClientConfig.class)
public interface AuthServerClient {
	@PostMapping(value = "/oauth2/token", consumes = "application/x-www-form-urlencoded")
	TokenResponse getToken(GetAccessTokenRequest getAccessTokenRequest);

	class TokenResponse {
		public String access_token;
		public String token_type;
		public Long expires_in;
		public String scope;
	}

}
