package com.example.authentication_server.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetAccessTokenRequest {
	private String grant_type;
	private String client_id;
	private String client_secret;
	private String scope;
}
