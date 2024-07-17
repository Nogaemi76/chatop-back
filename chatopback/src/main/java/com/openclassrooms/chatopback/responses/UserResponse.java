package com.openclassrooms.chatopback.responses;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserResponse {

	private long id;

	private String name;

	private String email;

	private LocalDate created_at;

	private LocalDate updated_at;
}
