package com.openclassrooms.chatopback.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class UserDto {

	private long id;

	private String email;

	private String name;

	private String password;

	private Date createdAt;

	private Date updatedAt;
}
