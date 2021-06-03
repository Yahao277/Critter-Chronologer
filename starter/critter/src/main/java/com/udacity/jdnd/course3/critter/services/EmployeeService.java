package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {
  @Autowired
  private EmployeeRepository employeeRepository;

  public Long saveEmployee(Employee employee){
    return this.employeeRepository.save(employee).getId();
  }

  public Employee findById(Long id){
    Optional<Employee> employee = this.employeeRepository.findById(id);
    if(employee.isPresent()){
      return employee.get();
    }else{
      throw new UnsupportedOperationException();
    }

  }

  public List<Employee> findEmployeesForService(Set<EmployeeSkill> skills, LocalDate date){
    List<Employee> employees = this.employeeRepository.findEmployeesByDaysAvailableAndSkillsIn(date.getDayOfWeek(),skills);
    List<Employee> availableEmployees = new ArrayList<>();
    for(Employee employee:employees){
      if(employee.getSkills().containsAll(skills)){
        availableEmployees.add(employee);
      }
    }
    return availableEmployees;

  }

  public List<Employee> getAllEmployees(){
    return this.employeeRepository.findAll();
  }



}
