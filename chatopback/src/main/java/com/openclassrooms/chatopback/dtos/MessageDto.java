package com.openclassrooms.chatopback.dtos;

import java.time.LocalDate;

import lombok.Data;

@Data
public class MessageDto {

	private Long id;

	private Long rental_id;

	private Long user_id;

	private String message;

	private LocalDate created_at;

	private LocalDate updated_at;
}
