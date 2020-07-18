package com.udacity.jdnd.course3.critter.entities;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;

import com.udacity.jdnd.course3.critter.models.EmployeeSkill;

@Entity(name = "employees")
public class Employee extends User {

	@ElementCollection(targetClass = EmployeeSkill.class)
	@Enumerated(EnumType.STRING)
	private Set<EmployeeSkill> skills;

	@ElementCollection(targetClass = DayOfWeek.class)
	@Enumerated(EnumType.STRING)
	private Set<DayOfWeek> daysAvailable;

	@ManyToMany(targetEntity = Schedule.class, mappedBy = "employees", cascade = { CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REFRESH })
	public Set<Schedule> schedules = new HashSet<>();

	public Set<EmployeeSkill> getSkills() {
		return skills;
	}

	public void setSkills(Set<EmployeeSkill> skills) {
		this.skills = skills;
	}

	public Set<DayOfWeek> getDaysAvailable() {
		return daysAvailable;
	}

	public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
		this.daysAvailable = daysAvailable;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((daysAvailable == null) ? 0 : daysAvailable.hashCode());
		result = prime * result + ((skills == null) ? 0 : skills.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (daysAvailable == null) {
			if (other.daysAvailable != null)
				return false;
		} else if (!daysAvailable.equals(other.daysAvailable))
			return false;
		if (skills == null) {
			if (other.skills != null)
				return false;
		} else if (!skills.equals(other.skills))
			return false;
		return true;
	}

}
