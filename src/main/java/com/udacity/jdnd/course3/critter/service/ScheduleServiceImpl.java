package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.Repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.Repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.Repository.PetRepository;
import com.udacity.jdnd.course3.critter.Repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService{
     @Autowired
     ScheduleRepository scheduleRepository;

     @Autowired
     PetRepository petRepository;

     @Autowired
     EmployeeRepository employeeRepository;

     @Autowired
     CustomerRepository customerRepository;

     @PersistenceContext
     EntityManager entityManager;

    private static final String FIND_ALL_SCHEDULE=
            "SELECT s FROM Schedule s ";

    private static final String FIND_SCHEDULE_FOR_PET=
            "SELECT s FROM Schedule s WHERE :pet MEMBER OF s.pets";

    private  static final String FIND_SCHEDULE_FOR_EMPLOYEE =
            "SELECT s FROM Schedule s WHERE :employee MEMBER OF s.employees";

    private static final String FIND_SCHEDULE_FOR_CUSTOMER =
            "SELECT s FROM Schedule s INNER JOIN s.pets p WHERE p.customer = :customer";

     public Schedule createNewSchedule(Schedule schedule){
         return scheduleRepository.save(schedule);
     }

     public List<Schedule> getAllSchedules(){
         List<Schedule> schedules = scheduleRepository.findAll();
        return schedules;

     }

     public List<Schedule> getScheduleForPet(Long petId){

         Optional<Pet> pet = petRepository.findById(petId);

         List<Schedule> schedules = new ArrayList<>();
         if (pet.isPresent()){
             schedules =
                     scheduleRepository.findAll()
                             .stream()
                             .filter(x -> x.getPets().contains(pet.get()))
                             .collect(Collectors.toList());
         }

         return schedules;
     }


    public List<Schedule> getScheduleForEmployee(Long employeeId){
        Employee employee = employeeRepository.findById(employeeId).get();
        return entityManager
                .createQuery(FIND_SCHEDULE_FOR_EMPLOYEE, Schedule.class)
                .setParameter("employee", employee)
                .getResultList();
    }

    public List<Schedule> getScheduleForCustomer(Long customerId){
        Customer customer = customerRepository.getById(customerId);
//        Customer customer = new Customer();

        return entityManager
                 .createQuery(FIND_SCHEDULE_FOR_CUSTOMER, Schedule.class)
                 .setParameter("customer", customer)
                 .getResultList();
    }


}
