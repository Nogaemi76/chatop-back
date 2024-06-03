package com.openclassrooms.chatopback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.chatopback.entities.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
