package com.example.authentication_server;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class FreelanceAuthDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, FreelanceWebAuthenticationDetails> {
	@Override
	public FreelanceWebAuthenticationDetails buildDetails(HttpServletRequest request) {
		return new FreelanceWebAuthenticationDetails(request);
	}
}
