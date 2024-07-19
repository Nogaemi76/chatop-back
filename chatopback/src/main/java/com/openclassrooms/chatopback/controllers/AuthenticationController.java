package com.openclassrooms.chatopback.controllers;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.chatopback.dtos.LoginUserDto;
import com.openclassrooms.chatopback.dtos.RegisterUserDto;
import com.openclassrooms.chatopback.dtos.UserDto;
import com.openclassrooms.chatopback.entities.User;
import com.openclassrooms.chatopback.responses.TokenResponse;
import com.openclassrooms.chatopback.responses.UserResponse;
import com.openclassrooms.chatopback.services.AuthenticationService;
import com.openclassrooms.chatopback.services.JwtService;
import com.openclassrooms.chatopback.services.UserService;

import lombok.RequiredArgsConstructor;

//@Log
@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

	private final JwtService jwtService;

	private final AuthenticationService authenticationService;

	private final ModelMapper modelMapper;

	private final UserService userService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserDto) {

		if (registerUserDto.getName() == null || registerUserDto.getName() == "" || registerUserDto.getEmail() == null
				|| registerUserDto.getEmail() == "" || registerUserDto.getPassword() == null
				|| registerUserDto.getPassword() == "") {

			return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
		}

		User registeredUser = authenticationService.register(registerUserDto);

		String jwtToken = jwtService.generateToken(registeredUser);

		TokenResponse tokenResponse = new TokenResponse();
		tokenResponse.setToken(jwtToken);

		return ResponseEntity.ok(tokenResponse);

	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody LoginUserDto loginUserDto) {

		User authenticatedUser = authenticationService.authenticate(loginUserDto);

		String jwtToken = jwtService.generateToken(authenticatedUser);

		TokenResponse tokenResponse = new TokenResponse();
		tokenResponse.setToken(jwtToken);

		return ResponseEntity.ok(tokenResponse);
	}

	@GetMapping("/me")
	public ResponseEntity<UserResponse> authenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String userName = authentication.getName();

		Optional<User> currentUser = userService.getUserByEmail(userName);

		UserDto currentUserDto = modelMapper.map(currentUser, UserDto.class);

		currentUserDto.setCreated_at(currentUser.get().getCreatedAt());
		currentUserDto.setUpdated_at(currentUser.get().getUpdatedAt());

		UserResponse userResponse = new UserResponse();
		userResponse.setId(currentUserDto.getId());
		userResponse.setName(currentUserDto.getName());
		userResponse.setEmail(currentUserDto.getEmail());

		userResponse.setCreated_at(currentUserDto.getCreated_at());
		userResponse.setUpdated_at(currentUserDto.getUpdated_at());

		return ResponseEntity.ok(userResponse);
	}
}
