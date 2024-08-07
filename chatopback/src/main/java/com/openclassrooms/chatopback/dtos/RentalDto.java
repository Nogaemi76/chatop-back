package com.openclassrooms.chatopback.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class RentalDto {

	private long id;

	private String name;

	private BigDecimal surface;

	private BigDecimal price;

	private String picture_name;

	private MultipartFile picture;

	private String description;

	private Long owner_id;

	private LocalDate created_at;

	private LocalDate updated_at;
}
