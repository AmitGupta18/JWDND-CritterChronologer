package com.udacity.jdnd.course3.critter.controllers;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.models.CustomerDTO;
import com.udacity.jdnd.course3.critter.models.EmployeeDTO;
import com.udacity.jdnd.course3.critter.models.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.EmployeeService;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into
 * separate user and customer controllers would be fine too, though that is not
 * part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

	private CustomerService customerService;
	private EmployeeService employeeService;

	public UserController(CustomerService customerService, EmployeeService employeeService) {
		this.customerService = customerService;
		this.employeeService = employeeService;
	}

	@PostMapping("/customer")
	public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
		Customer customer = customerService.save(convertCustomerDtoToCustomer(customerDTO));
		return convertCustomerToCustomerDto(customer);
	}

	@GetMapping("/customer")
	public List<CustomerDTO> getAllCustomers() {
		List<Customer> customers = customerService.getAllCustomers();
		return customers.stream().map(customer -> convertCustomerToCustomerDto(customer)).collect(Collectors.toList());
	}

	@GetMapping("/customer/pet/{petId}")
	public CustomerDTO getOwnerByPet(@PathVariable long petId) {
		return convertCustomerToCustomerDto(customerService.getOwnerByPet(petId));
	}

	@PostMapping("/employee")
	public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
		Employee employee = employeeService.save(convertEmployeeDtoToEmployee(employeeDTO));
		return convertEmployeeToEmployeeDto(employee);
	}

	@PostMapping("/employee/{employeeId}")
	public EmployeeDTO getEmployee(@PathVariable long employeeId) {
		Employee employee = employeeService.findById(employeeId);
		return convertEmployeeToEmployeeDto(employee);
	}

	@PutMapping("/employee/{employeeId}")
	public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
		Employee employee = employeeService.findById(employeeId);
		employee.setDaysAvailable(daysAvailable);

		employeeService.save(employee);
	}

	@GetMapping("/employee/availability")
	public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
		List<Employee> employees = employeeService.findEmployeeForService(employeeDTO.getDate(), employeeDTO.getSkills());
		return employees.stream().map(employee -> convertEmployeeToEmployeeDto(employee)).collect(Collectors.toList());
	}

	private Customer convertCustomerDtoToCustomer(CustomerDTO customerDto) {
		Customer customer = new Customer();
		BeanUtils.copyProperties(customerDto, customer);
		return customer;
	}

	private CustomerDTO convertCustomerToCustomerDto(Customer customer) {
		CustomerDTO customerDto = new CustomerDTO();
		BeanUtils.copyProperties(customer, customerDto);
		if (customer.getPets() != null) {
			customerDto.setPetIds(customer.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList()));
		}
		return customerDto;
	}

	private Employee convertEmployeeDtoToEmployee(EmployeeDTO employeeDto) {
		Employee employee = new Employee();
		BeanUtils.copyProperties(employeeDto, employee);
		return employee;
	}

	private EmployeeDTO convertEmployeeToEmployeeDto(Employee employee) {
		EmployeeDTO employeeDto = new EmployeeDTO();
		BeanUtils.copyProperties(employee, employeeDto);
		return employeeDto;
	}

}
