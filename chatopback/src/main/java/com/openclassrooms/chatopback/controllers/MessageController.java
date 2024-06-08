package com.openclassrooms.chatopback.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.chatopback.dtos.MessageDto;
import com.openclassrooms.chatopback.entities.Message;
import com.openclassrooms.chatopback.services.MessageService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/messages")
@RestController
@RequiredArgsConstructor
public class MessageController {

	private final MessageService messageService;

	private final ModelMapper modelMapper;

	@PostMapping
	ResponseEntity<String> addMessage(@RequestBody MessageDto messageDto) {

		Message message = modelMapper.map(messageDto, Message.class);
		messageService.saveMessage(message);

		return new ResponseEntity<String>("{\"message\":\"Message sent with success\"}", HttpStatus.OK);
	}
}
