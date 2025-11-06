package com.example.authentication_server.component;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

import com.example.authentication_server.FreelanceWebAuthenticationDetails;

@Component
public class FreelanceAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, FreelanceWebAuthenticationDetails> {
	@Override
	public FreelanceWebAuthenticationDetails buildDetails(HttpServletRequest request) {
		return new FreelanceWebAuthenticationDetails(request);
	}
}
