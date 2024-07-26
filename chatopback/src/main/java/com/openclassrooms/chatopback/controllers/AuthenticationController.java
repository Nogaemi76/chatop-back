package com.openclassrooms.chatopback.controllers;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.chatopback.dtos.UserDto;
import com.openclassrooms.chatopback.entities.User;
import com.openclassrooms.chatopback.responses.TokenResponse;
import com.openclassrooms.chatopback.responses.UserResponse;
import com.openclassrooms.chatopback.services.AuthenticationService;
import com.openclassrooms.chatopback.services.JwtService;
import com.openclassrooms.chatopback.services.UserService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

	private final JwtService jwtService;

	private final AuthenticationService authenticationService;

	private final ModelMapper modelMapper;

	private final UserService userService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserDto registeredUserDto) {

		if (registeredUserDto.getName() == null || registeredUserDto.getName() == ""
				|| registeredUserDto.getEmail() == null || registeredUserDto.getEmail() == ""
				|| registeredUserDto.getPassword() == null || registeredUserDto.getPassword() == "") {

			return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
		}

		User registeredUser = convertToEntity(registeredUserDto);

		User authenticatedUser = authenticationService.register(registeredUser);

		String jwtToken = jwtService.generateToken(authenticatedUser);

		TokenResponse tokenResponse = new TokenResponse();
		tokenResponse.setToken(jwtToken);

		return ResponseEntity.ok(tokenResponse);
	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody UserDto loggedUserDto) {

		User loggedUser = convertToEntity(loggedUserDto);

		try {
			User authenticatedUser = authenticationService.authenticate(loggedUser);
			String jwtToken = jwtService.generateToken(authenticatedUser);

			TokenResponse tokenResponse = new TokenResponse();
			tokenResponse.setToken(jwtToken);

			return ResponseEntity.ok(tokenResponse);

		} catch (BadCredentialsException e) {

			String errorMessage = e.getMessage();

			return new ResponseEntity<String>("{\"message\":\"" + errorMessage + "\"}", HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/me")
	public ResponseEntity<?> authenticatedUser() {

		try {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			String userName = authentication.getName();

			Optional<User> currentUser = userService.getUserByEmail(userName);

			UserDto currentUserDto = convertToDto(currentUser);

			UserResponse currentUserResponse = convertToUserResponse(currentUserDto);

			return ResponseEntity.ok(currentUserResponse);

		} catch (Exception e) {

			return new ResponseEntity<String>("{}", HttpStatus.UNAUTHORIZED);
		}
	}

	private User convertToEntity(UserDto userDto) {

		User user = modelMapper.map(userDto, User.class);

		user.setCreatedAt(userDto.getCreated_at());
		user.setUpdatedAt(userDto.getUpdated_at());

		return user;
	}

	private UserDto convertToDto(Optional<User> currentUser) {
		UserDto userDto = modelMapper.map(currentUser, UserDto.class);

		userDto.setCreated_at(currentUser.get().getCreatedAt());
		userDto.setUpdated_at(currentUser.get().getUpdatedAt());

		return userDto;
	}

	private UserResponse convertToUserResponse(UserDto userDto) {
		UserResponse userResponse = new UserResponse();

		userResponse.setId(userDto.getId());
		userResponse.setName(userDto.getName());
		userResponse.setEmail(userDto.getEmail());

		userResponse.setCreated_at(userDto.getCreated_at());
		userResponse.setUpdated_at(userDto.getUpdated_at());

		return userResponse;
	}
}
