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

import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.models.ScheduleDTO;
import com.udacity.jdnd.course3.critter.services.ScheduleService;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
	private ScheduleService scheduleService;

	public ScheduleController(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	@PostMapping
	public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
		Schedule schedule = scheduleService.save(convertScheduleDtoToSchedule(scheduleDTO),
				scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds());
		return convertScheduleToScheduleDto(schedule);
	}

	@GetMapping
	public List<ScheduleDTO> getAllSchedules() {
		List<Schedule> schedules = scheduleService.findAll();
		return schedules.stream().map(schedule -> convertScheduleToScheduleDto(schedule)).collect(Collectors.toList());
	}

	@GetMapping("/pet/{petId}")
	public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
		List<Schedule> schedules = scheduleService.getScheduleByPetId(petId);
		return schedules.stream().map(schedule -> convertScheduleToScheduleDto(schedule)).collect(Collectors.toList());
	}

	@GetMapping("/employee/{employeeId}")
	public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
		List<Schedule> schedules = scheduleService.getScheduleByEmployeeId(employeeId);
		return schedules.stream().map(schedule -> convertScheduleToScheduleDto(schedule)).collect(Collectors.toList());
	}

	@GetMapping("/customer/{customerId}")
	public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
		List<Schedule> schedules = scheduleService.getScheduleByCustomerId(customerId);
		return schedules.stream().map(schedule -> convertScheduleToScheduleDto(schedule)).collect(Collectors.toList());
	}

	private Schedule convertScheduleDtoToSchedule(ScheduleDTO scheduleDto) {
		Schedule schedule = new Schedule();
		BeanUtils.copyProperties(scheduleDto, schedule);
		return schedule;
	}

	private ScheduleDTO convertScheduleToScheduleDto(Schedule schedule) {
		ScheduleDTO scheduleDto = new ScheduleDTO();
		BeanUtils.copyProperties(schedule, scheduleDto);

		if (schedule.getEmployees() != null) {
			scheduleDto.setEmployeeIds(
					schedule.getEmployees().stream().map(employee -> employee.getId()).collect(Collectors.toList()));
		}
		if (schedule.getPets() != null) {
			scheduleDto.setPetIds(schedule.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList()));
		}
		return scheduleDto;
	}
}
