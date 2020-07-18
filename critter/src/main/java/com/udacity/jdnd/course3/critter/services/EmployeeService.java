package com.udacity.jdnd.course3.critter.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.udacity.jdnd.course3.critter.dao.EmployeeRepository;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.models.EmployeeRequestDTO;

@Service
@Transactional
public class EmployeeService {

	private EmployeeRepository employeeRepository;

	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public Employee save(Employee employee) {
		return employeeRepository.save(employee);
	}

	public Employee findById(long employeeId) {
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if (ObjectUtils.isEmpty(employee)) {
			throw new EntityNotFoundException("Employee with id: " + employeeId + " not found");
		}
		return employee.get();
	}

	public List<Employee> findEmployeeforService(EmployeeRequestDTO employeeDTO) {
		return employeeRepository.findAllBydaysAvailable(employeeDTO.getDate().getDayOfWeek()).stream()
				.filter(employee -> employee.getSkills().containsAll(employeeDTO.getSkills()))
				.collect(Collectors.toList());
	}

}
