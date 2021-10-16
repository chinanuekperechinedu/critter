package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.Repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.exception.ObjectNotFoundException;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    EmployeeRepository employeeRepository;

    @PersistenceContext
    EntityManager entityManager;

    private static final String FIND_EMPLOYEE_FOR_SERVICE =
            "SELECT e FROM Employee e WHERE :dayOfWeek MEMBER OF e.daysAvailable";

    public Employee createNewEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    
    public Employee getEmployeeById(long employeeId){
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()){
            return employee.get();
        } else{
            throw new ObjectNotFoundException("Employee not found!!!");
        }
    }

    public void updateEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId){
        Optional<Employee> employee = employeeRepository.findById(employeeId);

        if (employee.isPresent()) {
            employee.get().setDaysAvailable(daysAvailable);
        }else{
            throw new ObjectNotFoundException("Employee not found!!!");
        }

        employeeRepository.save(employee.get());
    }

    public Stream<Employee> getEmployeeForService(DayOfWeek dayOfWeek, EmployeeRequestDTO employeeDTO){

        Stream<Employee> employees =
                employeeRepository.findAll()
                        .stream()
                        .filter(x -> x.getDaysAvailable().contains(dayOfWeek) && 
                                x.getSkills().containsAll(employeeDTO.getSkills()));

        return employees;

    }

    public List<Employee> getEmployeesByIds(List<Long> employeeIds){
        return employeeRepository.findAllById(employeeIds);
    }
}
