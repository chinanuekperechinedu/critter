package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;


@Transactional
public interface EmployeeService {

    Employee createNewEmployee(Employee employee);

    Employee getEmployeeById(long employeeId);

    void updateEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId);

    Stream<Employee> getEmployeeForService(DayOfWeek dayOfWeek, EmployeeRequestDTO employeeDTO);

    List<Employee> getEmployeesByIds(List<Long> employeeIds);
}
