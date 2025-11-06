package com.example.authentication_server.enums;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum UserStatus {
	CREATED("created"),
	ACTIVE("active"),
	INACTIVE("inactive");

	private final String value;

	UserStatus(String value) {
		this.value = value;
	}

	public static UserStatus getValue(String value) {
		for (UserStatus type : UserStatus.values()) {
			if (type.getValue().equalsIgnoreCase(value)) {
				return type;
			}
		}

		throw new IllegalArgumentException("Unknown enum value: %s. Valid values are [%s]".formatted(value,
				Arrays.stream(UserStatus.values())
						.map(UserStatus::getValue)  // Get the lowercase values from the enum
						.reduce((v1, v2) -> v1 + ", " + v2)  // Join the values with a comma separator
						.orElse("")));
	}
}
