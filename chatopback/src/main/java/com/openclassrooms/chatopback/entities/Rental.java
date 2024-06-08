package com.openclassrooms.chatopback.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "rentals")
public class Rental {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	private BigDecimal surface;

	private BigDecimal price;

	private String description;

	@Column(name = "owner_id")
	private Long owner_id;

	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDate created_at;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDate updated_at;

}
