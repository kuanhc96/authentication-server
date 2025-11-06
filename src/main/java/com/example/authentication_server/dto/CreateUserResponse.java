package com.example.authentication_server.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import com.example.authentication_server.enums.Gender;
import com.example.authentication_server.enums.UserRole;
import com.example.authentication_server.enums.UserStatus;

@Builder
@Getter
public class CreateUserResponse {
	private String userGUID;
	private String name;
	private String email;
	private LocalDate birthday;
	private Gender gender;
	private String description;
	private UserStatus status;
	private UserRole role;
	private String profilePicture;
}
