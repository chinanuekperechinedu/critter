package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;

    @PostMapping
    @Transactional
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertToScheduleEntity(scheduleDTO);
        ScheduleDTO newScheduleDTO = convertToScheduleDTO(scheduleService.createNewSchedule(schedule));
        return newScheduleDTO;
    }

    @Transactional
    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return schedules
                .stream()
                .map(this::convertToScheduleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getScheduleForPet(petId);
        return schedules.stream()
                .map(this::convertToScheduleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.getScheduleForEmployee(employeeId)
                .stream()
                .map(this::convertToScheduleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleService.getScheduleForCustomer(customerId)
                .stream()
                .map(this::convertToScheduleDTO)
                .collect(Collectors.toList());
    }

    private Schedule convertToScheduleEntity(ScheduleDTO scheduleDTO){
        List<Pet> pets =  petService.getPetsByIds(scheduleDTO.getPetIds());
        List<Employee> employees = employeeService.getEmployeesByIds(scheduleDTO.getEmployeeIds());
        LocalDate date = scheduleDTO.getDate();
        Set<EmployeeSkill> activities = scheduleDTO.getActivities();
        Schedule schedule = new Schedule(employees, pets, date, activities);
        return schedule;
    }

    private ScheduleDTO convertToScheduleDTO(Schedule schedule){
        List<Long> employeeIds =
                schedule.getEmployees().size() != 0? schedule.getEmployees()
                        .stream()
                        .map(x -> x.getId())
                        .collect(Collectors.toList())
                        : null;

        List<Long> petIds =
                schedule.getPets().size() != 0 ? schedule.getPets()
                        .stream()
                        .map(x -> x.getId())
                        .collect(Collectors.toList())
                        : null;

        LocalDate date = schedule.getDate();

        Set<EmployeeSkill> activities = schedule.getActivities();

        ScheduleDTO scheduleDTO = new ScheduleDTO(employeeIds, petIds, date, activities);
        return scheduleDTO;
    }
}
