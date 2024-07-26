package com.openclassrooms.chatopback.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.chatopback.entities.User;
import com.openclassrooms.chatopback.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	public User register(User registeredUser) {
		User user = new User();

		user.setName(registeredUser.getName());
		user.setEmail(registeredUser.getEmail());
		user.setPassword(passwordEncoder.encode(registeredUser.getPassword()));

		return userRepository.save(user);
	}

	public User authenticate(User loggedUser) {

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loggedUser.getEmail(), loggedUser.getPassword()));

		return userRepository.findByEmail(loggedUser.getEmail()).orElseThrow();

	}
}
