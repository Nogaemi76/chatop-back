package com.openclassrooms.chatopback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.chatopback.entities.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

}
