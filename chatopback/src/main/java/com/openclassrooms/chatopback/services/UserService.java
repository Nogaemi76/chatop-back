package com.openclassrooms.chatopback.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.openclassrooms.chatopback.entities.User;
import com.openclassrooms.chatopback.repositories.UserRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public Optional<User> getUser(final Long id) {
		return userRepository.findById(id);
	}

	public Optional<User> getUserByEmail(final String email) {
		return userRepository.findByEmail(email);
	}
}
