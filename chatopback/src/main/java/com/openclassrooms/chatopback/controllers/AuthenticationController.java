package com.openclassrooms.chatopback.controllers;

import java.text.SimpleDateFormat;

import org.modelmapper.ModelMapper;
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

//@Log
@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {

	private final JwtService jwtService;

	private final AuthenticationService authenticationService;

	private final ModelMapper modelMapper;

	public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService,
			ModelMapper modelMapper) {
		this.jwtService = jwtService;
		this.authenticationService = authenticationService;
		this.modelMapper = new ModelMapper();
	}

	@PostMapping("/register")
	public ResponseEntity<TokenResponse> register(@RequestBody RegisterUserDto registerUserDto) {

		User registeredUser = authenticationService.register(registerUserDto);

		String jwtToken = jwtService.generateToken(registeredUser);

		TokenResponse tokenResponse = new TokenResponse();
		tokenResponse.setToken(jwtToken);

		return ResponseEntity.ok(tokenResponse);
	}

	@PostMapping("/login")
	public ResponseEntity<TokenResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
		User authenticatedUser = authenticationService.authenticate(loginUserDto);

		String jwtToken = jwtService.generateToken(authenticatedUser);

		TokenResponse tokenResponse = new TokenResponse();
		tokenResponse.setToken(jwtToken);

		return ResponseEntity.ok(tokenResponse);
	}

	// TODO Implement userDto
	@GetMapping("/me")
	public ResponseEntity<UserResponse> authenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		User currentUser = (User) authentication.getPrincipal();
		UserDto currentUserDto = convertToDto(currentUser);

		UserResponse userResponse = new UserResponse();
		// userResponse.setId(currentUser.getId());
		// userResponse.setName(currentUser.getName());
		// userResponse.setEmail(currentUser.getEmail());
		userResponse.setId(currentUserDto.getId());
		userResponse.setName(currentUserDto.getName());
		userResponse.setEmail(currentUserDto.getEmail());

		// userResponse.setCreatedAt(currentUser.getCreatedAt());
		// userResponse.setUpdatedAt(currentUser.getUpdatedAt());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

		// String createdDatedFormatted =
		// simpleDateFormat.format(currentUser.getCreatedAt());
		// String updatedDatedFormatted =
		// simpleDateFormat.format(currentUser.getUpdatedAt());
		String createdDatedFormatted = simpleDateFormat.format(currentUserDto.getCreatedAt());
		String updatedDatedFormatted = simpleDateFormat.format(currentUserDto.getUpdatedAt());

		userResponse.setCreatedAt(createdDatedFormatted);
		userResponse.setUpdatedAt(updatedDatedFormatted);

		return ResponseEntity.ok(userResponse);
	}

	private UserDto convertToDto(User user) {
		UserDto userDto = modelMapper.map(user, UserDto.class);
		return userDto;
	}
}
