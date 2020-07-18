package com.udacity.jdnd.course3.critter.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.models.PetDTO;
import com.udacity.jdnd.course3.critter.services.PetService;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

	private PetService petService;

	public PetController(PetService petService) {
		this.petService = petService;
	}

	@PostMapping
	public PetDTO savePet(@RequestBody PetDTO petDTO) {
		Pet pet = petService.save(convertPetDtoToPet(petDTO), petDTO.getOwnerId());
		return convertPetToPetDto(pet);
	}

	@GetMapping("/{petId}")
	public PetDTO getPet(@PathVariable long petId) {
		return convertPetToPetDto(petService.findById(petId));
	}

	@GetMapping
	public List<PetDTO> getPets() {
		List<Pet> pets = petService.findAll();
		return pets.stream().map(pet -> convertPetToPetDto(pet)).collect(Collectors.toList());
	}

	@GetMapping("/owner/{ownerId}")
	public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
		List<Pet> pets = petService.findByOwnerId(ownerId);
		return pets.stream().map(pet -> convertPetToPetDto(pet)).collect(Collectors.toList());
	}

	private Pet convertPetDtoToPet(PetDTO petDto) {
		Pet pet = new Pet();
		BeanUtils.copyProperties(petDto, pet);
		Customer customer = new Customer();
		customer.setId(petDto.getOwnerId());
		pet.setOwner(customer);
		return pet;
	}

	private PetDTO convertPetToPetDto(Pet pet) {
		PetDTO petDto = new PetDTO();
		BeanUtils.copyProperties(pet, petDto);
		petDto.setOwnerId(pet.getOwner().getId());
		return petDto;
	}
}
