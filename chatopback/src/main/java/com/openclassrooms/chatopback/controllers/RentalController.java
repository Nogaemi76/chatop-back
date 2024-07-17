package com.openclassrooms.chatopback.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.chatopback.dtos.RentalDto;
import com.openclassrooms.chatopback.entities.Rental;
import com.openclassrooms.chatopback.entities.User;
import com.openclassrooms.chatopback.responses.RentalResponse;
import com.openclassrooms.chatopback.services.RentalService;
import com.openclassrooms.chatopback.services.UserService;

import lombok.RequiredArgsConstructor;

//@Log
@RequestMapping("/api/rentals")
@RestController
@RequiredArgsConstructor
public class RentalController {

	private final RentalService rentalService;

	private final ModelMapper modelMapper;

	private final UserService userService;

	private final String urlPath = "http://localhost:4200/api/rentals/image/";

	@PostMapping
	ResponseEntity<String> addRental(@ModelAttribute RentalDto rentalDto) {

		Rental rental = convertToEntity(rentalDto);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();

		Optional<User> user = userService.getUserByEmail(userName);
		Long userId = user.get().getId();
		rental.setOwner_id(userId);

		UUID uuid = UUID.randomUUID();

		try {
			rental.setPicture(rentalDto.getPicture().getBytes());
			rental.setPictureName(uuid + "." + rentalDto.getPicture().getContentType().substring(6));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rentalService.saveRental(rental);

		return new ResponseEntity<String>("{\"message\":\"Rental created !\"}", HttpStatus.OK);
	}

	@GetMapping
	public Map<String, List<RentalResponse>> getAllRentals() {
		List<Rental> rentals = rentalService.getRentals();

		List<RentalResponse> rentalResponses = rentals.stream().map(this::convertToRentalResponse)
				.collect(Collectors.toList());

		if (rentalResponses.isEmpty()) {
			return null;

		} else {
			Map<String, List<RentalResponse>> allRentals = new HashMap<String, List<RentalResponse>>();
			allRentals.put("rentals", rentalResponses);
			return allRentals;
		}
	}

	@GetMapping("/image/{picture}")
	public byte[] getPicture(@PathVariable("picture") String picture) {
		Optional<Rental> rental = rentalService.getRentalByPictureName(picture);
		byte[] image = rental.get().getPicture();
		return image;
	}

	@GetMapping("/{id}")
	public RentalResponse getRentalById(@PathVariable("id") final Long id) {
		Optional<Rental> retrievedRental = rentalService.getRentalById(id);

		if (retrievedRental.isEmpty()) {
			return null;
		} else {
			RentalResponse rentalResponse = modelMapper.map(retrievedRental, RentalResponse.class);
			rentalResponse.setPicture(urlPath + retrievedRental.get().getPictureName());
			return rentalResponse;
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateRental(@PathVariable("id") final Long id, @ModelAttribute RentalDto rentalDto) {

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

	private Rental convertToEntity(RentalDto rentalDto) {
		Rental rental = modelMapper.map(rentalDto, Rental.class);
		return rental;
	}

	private RentalResponse convertToRentalResponse(Rental rental) {
		RentalResponse rentalResponse = modelMapper.map(rental, RentalResponse.class);
		rentalResponse.setPicture(urlPath + rental.getPictureName());
		return rentalResponse;
	}
}
