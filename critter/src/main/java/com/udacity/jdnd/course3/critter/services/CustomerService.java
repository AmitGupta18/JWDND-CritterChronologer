package com.udacity.jdnd.course3.critter.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.dao.CustomerRepository;
import com.udacity.jdnd.course3.critter.dao.PetRepository;
import com.udacity.jdnd.course3.critter.entities.Customer;

@Service
@Transactional
public class CustomerService {
	private CustomerRepository customerRepository;
	private PetRepository petRepository;

	public CustomerService(CustomerRepository customerRepository, PetRepository petRepository) {
		this.customerRepository = customerRepository;
		this.petRepository = petRepository;
	}

	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}

	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	public Customer getOwnerByPet(long petId) {
		return petRepository.getOne(petId).getOwner();
	}

}
