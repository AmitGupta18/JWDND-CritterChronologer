package com.udacity.jdnd.course3.critter.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.dao.CustomerRepository;
import com.udacity.jdnd.course3.critter.dao.EmployeeRepository;
import com.udacity.jdnd.course3.critter.dao.PetRepository;
import com.udacity.jdnd.course3.critter.dao.ScheduleRepository;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;

@Service
@Transactional
public class ScheduleService {
	private ScheduleRepository scheduleRepository;

	private EmployeeRepository employeeRepository;

	private CustomerRepository customerRepository;

	private PetRepository petRepository;

	public ScheduleService(ScheduleRepository scheduleRepository, EmployeeRepository employeeRepository,
			PetRepository petRepository, CustomerRepository customerRepository) {
		this.scheduleRepository = scheduleRepository;
		this.employeeRepository = employeeRepository;
		this.customerRepository = customerRepository;
		this.petRepository = petRepository;
	}

	public Schedule save(Schedule schedule, List<Long> employeeIds, List<Long> petIds) {
		Set<Employee> employees = employeeIds.stream().map(id -> {
			return employeeRepository.getOne(id);
		}).collect(Collectors.toSet());

		Set<Pet> pets = petIds.stream().map(id -> {
			return petRepository.getOne(id);
		}).collect(Collectors.toSet());

		schedule.setEmployees(employees);
		schedule.setPets(pets);
		return scheduleRepository.save(schedule);
	}

	public List<Schedule> findAll() {
		return scheduleRepository.findAll();
	}

	public List<Schedule> getScheduleByPetId(long petId) {
		return scheduleRepository.findAllByPetsId(petId);
	}

	public List<Schedule> getScheduleByEmployeeId(long employeeId) {
		return scheduleRepository.findAllByEmployeesId(employeeId);
	}

	public List<Schedule> getScheduleByCustomerId(long customerId) {
		Customer owner = customerRepository.getOne(customerId);
		return scheduleRepository.getAllByPetsIn(owner.getPets());
	}

}
