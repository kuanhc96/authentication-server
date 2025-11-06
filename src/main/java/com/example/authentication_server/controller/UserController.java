package com.example.authentication_server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.example.authentication_server.client.UserManagementServerClient;
import com.example.authentication_server.dto.CreateUserRequest;
import com.example.authentication_server.dto.CreateUserResponse;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	private final UserManagementServerClient userManagementServerClient;

	@PostMapping("/create")
//	@PreAuthorize("INTEGRATION_TEST")
	public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest request) {
		return userManagementServerClient.createUser(request);
	}

	@DeleteMapping("/delete/{userGUID}")
//	@PreAuthorize("INTEGRATION_TEST")
	public ResponseEntity<Void> deleteUser(@PathVariable String userGUID) {
		userManagementServerClient.deleteUser(userGUID);
		return ResponseEntity.noContent().build();
	}
}
