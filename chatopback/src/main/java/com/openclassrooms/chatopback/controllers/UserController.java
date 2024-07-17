package com.openclassrooms.chatopback.controllers;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.chatopback.dtos.UserDto;
import com.openclassrooms.chatopback.entities.User;
import com.openclassrooms.chatopback.responses.UserResponse;
import com.openclassrooms.chatopback.services.UserService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	private final ModelMapper modelMapper;

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable("id") final Long id) {
		Optional<User> retrievedUser = userService.getUser(id);
		UserDto retrievedUserDto = modelMapper.map(retrievedUser, UserDto.class);

		UserResponse userResponse = new UserResponse();

		userResponse.setId(retrievedUserDto.getId());
		userResponse.setName(retrievedUserDto.getName());
		userResponse.setEmail(retrievedUserDto.getEmail());
		userResponse.setCreated_at(retrievedUserDto.getCreated_at());
		userResponse.setUpdated_at(retrievedUserDto.getUpdated_at());

		return ResponseEntity.ok(userResponse);
	}
}
