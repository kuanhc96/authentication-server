package com.example.authentication_server.config;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import com.example.authentication_server.component.FreelanceAuthenticationDetailsSource;
import com.example.authentication_server.client.UserManagementServerClient;
import com.example.authentication_server.component.EmailPasswordRoleAuthenticationProvider;
import com.example.authentication_server.enums.UserRole;

@Configuration
@EnableWebSecurity
public class ProjectSecurityConfig {
	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, FreelanceAuthenticationDetailsSource freelanceAuthenticationDetailsSource) throws Exception {
		http
				.securityMatcher("/login")
				.cors(cors -> cors.disable())
				.csrf(csrf -> csrf.disable())
				.formLogin(form -> form
						.loginPage("http://localhost:5173/login")
						.usernameParameter("email")
						.loginProcessingUrl("/login")
						.authenticationDetailsSource(freelanceAuthenticationDetailsSource)
						.successHandler((req, res, auth) -> {
							res.resetBuffer();
							res.setStatus(HttpStatus.OK.value());
							res.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

							var savedReq = new HttpSessionRequestCache().getRequest(req, res);
							res.getWriter()
									.append("{\"redirectUrl\": \"")
									.append(savedReq == null ? "": savedReq.getRedirectUrl())
									.append("\"}");
							res.flushBuffer();
						})
						.failureHandler((req, res, ex) -> res.setStatus(HttpStatus.UNAUTHORIZED.value()))
				)
				.logout(logout -> logout.logoutSuccessUrl("http://localhost:5173/login?logout"))
				.exceptionHandling(handler -> handler.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.FORBIDDEN)))
				.authorizeHttpRequests(
						(authorize) -> authorize.requestMatchers(
								"/actuator/**",
								"/user/create",
								"/user/delete/**"
						).permitAll().anyRequest().authenticated()
				);

//				.authorizeHttpRequests(
//						(authorize) -> authorize.requestMatchers(
//								"/login",
//								"/actuator/**",
//								"/user/create",
//								"/user/delete/**"
//						).permitAll()
//				)
//				.cors(cors -> cors.disable())
//				.csrf(csrf -> csrf.disable())
//				.formLogin(form -> form
//						.loginPage("http://localhost:8080/login")
//						.authenticationDetailsSource(freelanceAuthenticationDetailsSource)
//				);
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(UserManagementServerClient userManagementServerClient) {
		EmailPasswordRoleAuthenticationProvider provider = new EmailPasswordRoleAuthenticationProvider(userManagementServerClient);
		ProviderManager providerManager = new ProviderManager(provider);
		providerManager.setEraseCredentialsAfterAuthentication(false);
		return providerManager;
	}

	@Bean
	public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
		return (context) -> {
			var principal = context.getPrincipal();
			OAuth2TokenType tokenType = context.getTokenType();
			if (tokenType.equals(OAuth2TokenType.ACCESS_TOKEN)) {
				Set<String> roles = principal.getAuthorities().stream()
						.map(role -> "ROLE_" + role.getAuthority())
						.collect(Collectors.toSet());
				context.getClaims().claims((claims) -> {
					claims.put("roles", roles);
				});
			} else if (tokenType.equals(new OAuth2TokenType(OidcParameterNames.ID_TOKEN))) {
				UserRole role = principal.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority)
						.map(UserRole::valueOf)
						.findFirst()
						.orElseThrow(() -> new BadCredentialsException("Invalid role")); // Default role if not found
				context.getClaims().claims((claims) -> {
					claims.put("role", role);
				});

			}
		};
	}
}
