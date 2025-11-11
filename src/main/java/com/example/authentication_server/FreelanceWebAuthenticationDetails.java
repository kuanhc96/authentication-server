package com.example.authentication_server;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import jakarta.servlet.http.HttpServletRequest;

import com.example.authentication_server.enums.UserRole;

public class FreelanceWebAuthenticationDetails extends WebAuthenticationDetails {
	private final UserRole role;

	public FreelanceWebAuthenticationDetails(HttpServletRequest request) {
		super(request);
		String temp = request.getParameter("role");
		this.role = UserRole.valueOf(temp);
	}

	public UserRole getRole() {
		return role;
	}
}
