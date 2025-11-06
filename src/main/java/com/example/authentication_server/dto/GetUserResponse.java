package com.example.authentication_server.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

import com.example.authentication_server.enums.Gender;
import com.example.authentication_server.enums.UserRole;

@Builder
@Data
public class GetUserResponse {
	private String userGUID;
	private String name;
	private String email;
	private UserRole role;
	private Gender gender;
	private String description;
	private LocalDate birthday;
	private String profilePicture;
}
