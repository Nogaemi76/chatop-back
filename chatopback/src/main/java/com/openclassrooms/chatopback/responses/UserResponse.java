package com.openclassrooms.chatopback.responses;

import java.util.Date;

import lombok.Data;

@Data
public class UserResponse {

	private long id;

	private String name;

	private String email;

	private Date createdAt;

	private Date updatedAt;
}
