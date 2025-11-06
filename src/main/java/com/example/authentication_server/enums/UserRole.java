package com.example.authentication_server.enums;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum UserRole {
	STUDENT("student"),
	INSTRUCTOR("instructor");

	private final String value;

	UserRole(String value) {
		this.value = value;
	}

	public static UserRole getValue(String value) {
		for (UserRole type : UserRole.values()) {
			if (type.getValue().equalsIgnoreCase(value)) {
				return type;
			}
		}

		throw new IllegalArgumentException("Unknown enum value: %s. Valid values are [%s]".formatted(value,
				Arrays.stream(UserRole.values())
						.map(UserRole::getValue)  // Get the lowercase values from the enum
						.reduce((v1, v2) -> v1 + ", " + v2)  // Join the values with a comma separator
						.orElse("")));
	}
}
