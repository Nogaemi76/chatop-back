package com.openclassrooms.chatopback.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.openclassrooms.chatopback.entities.Rental;
import com.openclassrooms.chatopback.repositories.RentalRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Service
@RequiredArgsConstructor
public class RentalService {

	private final RentalRepository rentalRepository;

	public Optional<Rental> getRentalById(final Long id) {
		return rentalRepository.findById(id);
	}

	public List<Rental> getRentals() {
		return (List<Rental>) rentalRepository.findAll();
	}

	public Rental saveRental(Rental rental) {
		Rental savedRental = rentalRepository.save(rental);
		return savedRental;
	}

	public Optional<Rental> getRentalByPictureName(String picture_name) {
		return rentalRepository.findByPictureName(picture_name);
	}
}
