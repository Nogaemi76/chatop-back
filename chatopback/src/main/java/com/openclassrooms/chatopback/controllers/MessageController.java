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

		if (messageDto.getMessage() == null || messageDto.getMessage() == "" || messageDto.getUser_id() == null
				|| messageDto.getRental_id() == null) {

			return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
		}

		Message message = convertToEntity(messageDto);
		messageService.saveMessage(message);

		return new ResponseEntity<String>("{\"message\":\"Message sent with success\"}", HttpStatus.OK);
	}

	private Message convertToEntity(MessageDto messageDto) {

		Message message = modelMapper.map(messageDto, Message.class);

		message.setUserId(messageDto.getUser_id());
		message.setRentalId(messageDto.getRental_id());
		message.setCreatedAt(messageDto.getCreated_at());
		message.setUpdatedAt(messageDto.getUpdated_at());

		return message;
	}
}
