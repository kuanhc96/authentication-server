package com.example.authentication_server.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

import com.example.authentication_server.enums.Gender;
import com.example.authentication_server.enums.UserRole;

@Data
@Builder
public class CreateUserRequest {
	private String email;
	private String password;
	private UserRole role;
	private String name;
	private LocalDate birthday;
	private Gender gender;
	private String description;
	private String profilePicture;
}
