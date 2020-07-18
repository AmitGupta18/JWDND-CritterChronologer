package com.udacity.jdnd.course3.critter.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udacity.jdnd.course3.critter.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
