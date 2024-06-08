package com.openclassrooms.chatopback.controllers;

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

import lombok.RequiredArgsConstructor;

//@Log
@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

	private final JwtService jwtService;

	private final AuthenticationService authenticationService;

	private final ModelMapper modelMapper;

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

	@GetMapping("/me")
	public ResponseEntity<UserResponse> authenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		User currentUser = (User) authentication.getPrincipal();
		UserDto currentUserDto = modelMapper.map(currentUser, UserDto.class);

		UserResponse userResponse = new UserResponse();
		userResponse.setId(currentUserDto.getId());
		userResponse.setName(currentUserDto.getName());
		userResponse.setEmail(currentUserDto.getEmail());

		/*
		 * DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy/MM/dd"); String
		 * createdDatedFormatted = currentUserDto.getCreatedAt().format(pattern); String
		 * updatedDatedFormatted = currentUserDto.getUpdatedAt().format(pattern);
		 */

		/*
		 * userResponse.setCreatedAt(createdDatedFormatted);
		 * userResponse.setUpdatedAt(updatedDatedFormatted);
		 */

		userResponse.setCreated_at(currentUserDto.getCreated_at());
		userResponse.setUpdated_at(currentUserDto.getUpdated_at());

		return ResponseEntity.ok(userResponse);
	}
}
