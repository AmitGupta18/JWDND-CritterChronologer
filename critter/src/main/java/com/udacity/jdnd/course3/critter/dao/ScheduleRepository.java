package com.udacity.jdnd.course3.critter.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

	List<Schedule> findAllByPetsId(long petId);

	List<Schedule> findAllByEmployeesId(long employeeId);

	List<Schedule> getAllByPetsIn(Set<Pet> pets);
}
