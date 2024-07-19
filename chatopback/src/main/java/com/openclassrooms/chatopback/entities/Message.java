package com.openclassrooms.chatopback.entities;

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
@Table(name = "messages")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "rental_id")
	private Long rentalId;

	@Column(name = "user_id")
	private Long userId;

	private String message;

	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDate createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDate updatedAt;
}
