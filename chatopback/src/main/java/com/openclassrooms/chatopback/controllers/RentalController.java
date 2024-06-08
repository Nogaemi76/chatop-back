package com.openclassrooms.chatopback.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.chatopback.dtos.RentalDto;
import com.openclassrooms.chatopback.entities.Rental;
import com.openclassrooms.chatopback.services.RentalService;

import lombok.RequiredArgsConstructor;

//@Log
@RequestMapping("/api/rentals")
@RestController
@RequiredArgsConstructor
public class RentalController {

	private final RentalService rentalService;

	private final ModelMapper modelMapper;

	@PostMapping
	ResponseEntity<String> addRental(@RequestBody RentalDto rentalDto) {

		Rental rental = convertToEntity(rentalDto);
		rentalService.saveRental(rental);

		return new ResponseEntity<String>("{\"message\":\"Rental created !\"}", HttpStatus.OK);
	}

	@GetMapping
	public HashMap<String, ArrayList<RentalDto>> getAllRentals() {
		List<Rental> rentals = rentalService.getRentals();

		List<RentalDto> rentalDtos = rentals.stream().map(this::convertToDto).collect(Collectors.toList());

		if (rentalDtos.isEmpty()) {
			return null;
		} else {

			// log.info(rentalDtos.getClass().getSimpleName());

			HashMap<String, ArrayList<RentalDto>> allRentals = new HashMap<String, ArrayList<RentalDto>>();
			allRentals.put("rentals", (ArrayList<RentalDto>) rentalDtos);
			return allRentals;
		}
	}

	@GetMapping("/{id}")
	public RentalDto getRentalById(@PathVariable("id") final Long id) {
		Optional<Rental> retrievedRental = rentalService.getRentalById(id);

		RentalDto retrievedRentalDto = convertToDto(retrievedRental.get());

		return retrievedRentalDto;
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateRental(@PathVariable("id") final Long id, @RequestBody RentalDto rentalDto) {

		Rental rental = convertToEntity(rentalDto);

		Optional<Rental> retrievedRental = rentalService.getRentalById(id);

		if (retrievedRental.isPresent()) {
			Rental currentRental = retrievedRental.get();

			String name = rental.getName();
			if (name != null) {
				currentRental.setName(name);
			}

			BigDecimal surface = rental.getSurface();
			if (surface != null) {
				currentRental.setSurface(surface);
			}

			BigDecimal price = rental.getPrice();
			if (price != null) {
				currentRental.setPrice(price);
			}

			String description = rental.getDescription();
			if (description != null) {
				currentRental.setDescription(description);
			}

			currentRental.setUpdated_at(LocalDate.now());

			rentalService.saveRental(currentRental);

			return new ResponseEntity<String>("{\"message\":\"Rental Updated !\"}", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	private RentalDto convertToDto(Rental rental) {
		RentalDto rentalDto = modelMapper.map(rental, RentalDto.class);
		return rentalDto;
	}

	private Rental convertToEntity(RentalDto rentalDto) {
		Rental rental = modelMapper.map(rentalDto, Rental.class);
		return rental;
	}
}
