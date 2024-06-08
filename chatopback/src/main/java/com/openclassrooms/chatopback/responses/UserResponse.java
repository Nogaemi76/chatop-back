package com.openclassrooms.chatopback.responses;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserResponse {

	private long id;

	private String name;

	private String email;

	// private String createdAt;
	private LocalDate created_at;

	// private String updatedAt;
	private LocalDate updated_at;
}
