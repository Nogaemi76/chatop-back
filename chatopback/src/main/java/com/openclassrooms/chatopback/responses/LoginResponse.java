package com.openclassrooms.chatopback.responses;

import lombok.Data;

@Data
public class LoginResponse {

	private String token;

	private long expiresIn;

	public String getToken() {
		return token;
	}

	@Override
	public String toString() {
		return "LoginResponse{" + "token='" + token + '\'' + ", expiresIn=" + expiresIn + '}';
	}
}
