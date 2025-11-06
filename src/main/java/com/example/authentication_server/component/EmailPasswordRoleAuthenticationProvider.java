package com.example.authentication_server.component;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.example.authentication_server.FreelanceWebAuthenticationDetails;
import com.example.authentication_server.client.UserManagementServerClient;
import com.example.authentication_server.enums.UserRole;

@Component
@RequiredArgsConstructor
public class EmailPasswordRoleAuthenticationProvider implements AuthenticationProvider {
	private final UserManagementServerClient userManagementServerClient;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName();
		String password = authentication.getCredentials().toString();
		FreelanceWebAuthenticationDetails details = (FreelanceWebAuthenticationDetails) authentication.getDetails();
		UserRole role = details.getRole();
		ResponseEntity<Boolean> authenticationResponse = userManagementServerClient.authenticate(email, role, password);
		if (authenticationResponse.getBody() != null && authenticationResponse.getBody()) {
			return new UsernamePasswordAuthenticationToken(email, password, List.of(new SimpleGrantedAuthority(role.name())));
		} else {
			throw new BadCredentialsException("Invalid password!");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
}
