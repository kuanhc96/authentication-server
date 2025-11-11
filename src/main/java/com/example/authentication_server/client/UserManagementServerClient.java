package com.example.authentication_server.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.authentication_server.dto.CreateUserRequest;
import com.example.authentication_server.dto.CreateUserResponse;
import com.example.authentication_server.dto.GetUserResponse;
import com.example.authentication_server.enums.UserRole;

@FeignClient(name = "freelance-user-management-server", configuration = OAuth2FeignConfig.class)
public interface UserManagementServerClient {
	@PostMapping(value = "/user/create", consumes = "application/json")
	ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest request);

	@DeleteMapping(value = "/user/delete/{userGUID}")
	ResponseEntity<Void> deleteUser(@PathVariable String userGUID);

	@GetMapping(value = "/user")
	ResponseEntity<GetUserResponse> getUserByEmailAndRole(@RequestParam String email, @RequestParam UserRole role);

	@PostMapping(value = "/user/authenticate", consumes = "application/json")
	ResponseEntity<Boolean> authenticate(@RequestParam String email, @RequestParam UserRole role, @RequestParam String password);
}
