package com.openclassrooms.chatopback.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.chatopback.dtos.LoginUserDto;
import com.openclassrooms.chatopback.dtos.RegisterUserDto;
import com.openclassrooms.chatopback.entities.User;
import com.openclassrooms.chatopback.repositories.UserRepository;

//@Log
@Service
public class AuthenticationService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User register(RegisterUserDto registerDto) {
		User user = new User();

		user.setName(registerDto.getName());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

		// log.info("auth service");
		// log.info(user.getEmail());
		return userRepository.save(user);

	}

	public User authenticate(LoginUserDto loginDto) {

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

		return userRepository.findByEmail(loginDto.getEmail()).orElseThrow();
	}
}
