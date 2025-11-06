package com.example.authentication_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.example.authentication_server.FreelanceAuthDetailsSource;

@Configuration
@EnableWebSecurity
public class ProjectSecurityConfig {
	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, FreelanceAuthDetailsSource freelanceAuthDetailsSource) throws Exception {
		http
				.authorizeHttpRequests(
						(authorize) -> authorize.requestMatchers(
								"/login",
								"/actuator/**",
								"/user/create",
								"/user/delete/**"
						).permitAll()
				)
				.cors(cors -> cors.disable())
				.csrf(csrf -> csrf.disable())
				.formLogin(form -> form
						.loginPage("http://localhost:8080/login")
						.authenticationDetailsSource(freelanceAuthDetailsSource)
				);
		return http.build();
	}
}
