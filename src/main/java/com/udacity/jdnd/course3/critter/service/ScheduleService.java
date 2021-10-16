package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;


import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ScheduleService {

    Schedule createNewSchedule(Schedule schedule);

    List<Schedule> getAllSchedules();

    List<Schedule> getScheduleForPet(Long petId);

    List<Schedule> getScheduleForEmployee(Long employeeId);

    List<Schedule> getScheduleForCustomer(Long customerId);
}
