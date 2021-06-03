package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import com.udacity.jdnd.course3.critter.services.PetService;
import com.udacity.jdnd.course3.critter.services.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertToSchedule(scheduleDTO);
        Schedule saved = scheduleService.save(schedule);
        return convertToScheduleDTO(saved);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.findAll();
        return schedules.stream().map(s -> convertToScheduleDTO(s)).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getScheduleByPetId(petId);
        return schedules.stream().map(s -> convertToScheduleDTO(s)).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getScheduleByEmployeeId(employeeId);
        return schedules.stream().map(s -> convertToScheduleDTO(s)).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getScheduleByCustomerId(customerId);
        return schedules.stream().map(s -> convertToScheduleDTO(s)).collect(Collectors.toList());
    }

    private ScheduleDTO convertToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule,scheduleDTO);

        scheduleDTO.setEmployeeIds(schedule.getEmployees()
            .stream().map(e -> e.getId())
            .collect(Collectors.toList()));

        scheduleDTO.setPetIds(schedule.getPets()
            .stream().map(p -> p.getId())
            .collect(Collectors.toList()));

        return scheduleDTO;
    }

    private Schedule convertToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO,schedule);

        schedule.setEmployees(scheduleDTO.getEmployeeIds()
            .stream().map(id -> employeeService.findById(id))
            .collect(Collectors.toList())
        );

        schedule.setPets(scheduleDTO.getPetIds()
            .stream().map(id -> petService.getOne(id))
            .collect(Collectors.toList())
        );

        return schedule;
    }
}
