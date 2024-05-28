package com.openclassrooms.chatopback.responses;

import lombok.Data;

@Data
public class TokenResponse {

	private String token;

	public String getToken() {
		return token;
	}
}
