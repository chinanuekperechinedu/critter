package com.udacity.jdnd.course3.critter.Repository;

import com.udacity.jdnd.course3.critter.entity.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
