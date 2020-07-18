package com.udacity.jdnd.course3.critter.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.udacity.jdnd.course3.critter.dao.CustomerRepository;
import com.udacity.jdnd.course3.critter.dao.PetRepository;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;

@Service
@Transactional
public class PetService {

	private PetRepository petRepository;

	private CustomerRepository customerRepository;

	public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
		this.petRepository = petRepository;
		this.customerRepository = customerRepository;
	}

	public Pet save(Pet pet, long ownerId) {
		Optional<Customer> customer = customerRepository.findById(ownerId);

		if (ObjectUtils.isEmpty(customer)) {
			throw new EntityNotFoundException("Customer with id: " + ownerId + " not found.");
		}
		Customer owner = customer.get();
		pet.setOwner(owner);
		pet = petRepository.save(pet);

		// Additional insert statement to pass test-case, since all the test-cases are
		// Transactional
		owner.getPets().add(pet);
		customerRepository.save(owner);

		return pet;
	}

	public List<Pet> findAll() {
		return petRepository.findAll();
	}

	public Pet findById(long petId) {
		Optional<Pet> pet = petRepository.findById(petId);
		if (ObjectUtils.isEmpty(pet)) {
			throw new EntityNotFoundException("Pet with id: " + petId + " not found.");
		}
		return pet.get();
	}

	public List<Pet> findByOwnerId(long ownerId) {
		return petRepository.findAllByOwnerId(ownerId);
	}

}
