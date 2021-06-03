package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.exceptions.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.exceptions.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.exceptions.PetNotFoundException;
import com.udacity.jdnd.course3.critter.exceptions.ScheduleInvalidException;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import com.udacity.jdnd.course3.critter.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ScheduleService {
  @Autowired
  private ScheduleRepository scheduleRepository;

  @Autowired
  private PetRepository petRepository;

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private CustomerRepository customerRepository;

  public Schedule save(Schedule schedule) {
    if(schedule.getPets().stream().allMatch(p -> p.getOwner()!=null)) {
      return scheduleRepository.save(schedule);
    }else {
      throw new ScheduleInvalidException("Not all pets have an owner attached");
    }
  }

  public List<Schedule> findAll() {
    return scheduleRepository.findAll();
  }

  public List<Schedule> getScheduleByPetId(Long id) {
    Optional<Pet> pet = petRepository.findById(id);
    if(pet.isPresent()) {
      return scheduleRepository.findAllByPetsContains(pet.get());
    } else {
      throw new PetNotFoundException("No schedule found because pet " + id + " does not exist");
    }
  }

  public List<Schedule> getScheduleByEmployeeId(Long id) {
    Optional<Employee> employee = employeeRepository.findById(id);
    if(employee.isPresent()) {
      return scheduleRepository.findAllByEmployeesContains(employee.get());
    } else {
      throw new EmployeeNotFoundException("No schedule found because employee " + id + " does not exist");
    }
  }

  public List<Schedule> getScheduleByCustomerId(Long id) {
    Optional<Customer> customer = customerRepository.findById(id);
    if(!customer.isPresent()) {
      // customer id does not exist
      throw new CustomerNotFoundException("No schedule found because customer " + id + " does not exist");
    }

    List<Pet> petList = customer.get().getPets();

    List<Schedule> schedules = new ArrayList<>();
    for(Pet pet : petList) {
      List<Schedule> schedulesByPet = scheduleRepository.findAllByPetsContains(pet);
      schedules.addAll(schedulesByPet);
    }
    return schedules;
  }

}
