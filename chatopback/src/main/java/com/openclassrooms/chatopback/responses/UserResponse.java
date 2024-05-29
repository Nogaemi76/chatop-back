package com.openclassrooms.chatopback.responses;

import lombok.Data;

@Data
public class UserResponse {

	private long id;

	private String name;

	private String email;

	// private Date createdAt;
	private String createdAt;

	// private Date updatedAt;
	private String updatedAt;
}
