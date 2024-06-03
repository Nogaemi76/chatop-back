package com.openclassrooms.chatopback.services;

import org.springframework.stereotype.Service;

import com.openclassrooms.chatopback.entities.Message;
import com.openclassrooms.chatopback.repositories.MessageRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Service
@RequiredArgsConstructor
public class MessageService {

	private final MessageRepository messageRepository;

	public Message saveMessage(Message message) {
		Message savedMessage = messageRepository.save(message);

		return savedMessage;
	}
}
