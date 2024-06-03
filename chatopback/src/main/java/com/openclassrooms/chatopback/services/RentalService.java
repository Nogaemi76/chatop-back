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

	/*
	 * public Rental updateRental(Rental rental) { Rental existingRental =
	 * rentalRepository.findById(rental.getId()).get();
	 * 
	 * existingRental.setName(rental.getName());
	 * existingRental.setPrice(rental.getPrice());
	 * existingRental.setSurface(rental.getSurface());
	 * existingRental.setDescription(rental.getDescription());
	 * 
	 * Rental updatedRental = rentalRepository.save(existingRental);
	 * 
	 * return updatedRental; }
	 */
}
