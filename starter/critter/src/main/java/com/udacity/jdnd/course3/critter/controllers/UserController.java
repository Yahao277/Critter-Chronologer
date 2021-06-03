package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.exceptions.PetNotFoundException;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import com.udacity.jdnd.course3.critter.services.PetService;
import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PetService petService;


    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = convertToCustomer(customerDTO);
        Long savedId = customerService.saveCustomer(customer);
        return convertToCustomerDTO(customerService.getOne(savedId));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = this.customerService.getAllCustomers();
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        for(Customer customer:customers){
            customerDTOList.add(convertToCustomerDTO(customer));
        }
        return customerDTOList;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Pet pet = this.petService.findPetById(petId);
        Customer owner = pet.getOwner();
        return this.convertToCustomerDTO(owner);

    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = convertToEmployee(employeeDTO);
        Long saved = employeeService.saveEmployee(employee);
        return convertToEmployeeDTO(this.employeeService.findById(saved));
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = this.employeeService.findById(employeeId);
        return this.convertToEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = this.employeeService.findById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        this.employeeService.saveEmployee(employee);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        Set<EmployeeSkill> skills = employeeDTO.getSkills();
        LocalDate availableDay = employeeDTO.getDate();
        List<Employee> employees = employeeService.findEmployeesForService(employeeDTO.getSkills()
            ,employeeDTO.getDate());

        return employees.stream().map(e -> convertToEmployeeDTO(e)).collect(Collectors.toList());
    }

    private Customer convertToCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO,customer);

        List<Long> petsIds = customerDTO.getPetIds();
        if(petsIds != null){
            customer.setPets(petsIds.stream().map(id -> petService.getOne(id)).collect(Collectors.toList()));
        }

        return customer;
    }

    private Employee convertToEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        return employee;
    }

    private CustomerDTO convertToCustomerDTO(Customer customer){
        CustomerDTO dto = new CustomerDTO();
        BeanUtils.copyProperties(customer,dto);
        List<Pet> pets = customer.getPets();
        if(pets != null){
            List<Long> ids = new ArrayList<>();
            for(Pet pet: pets){
                ids.add(pet.getId());
            }
            dto.setPetIds(ids);
        }

        return dto;
    }

    private EmployeeDTO convertToEmployeeDTO(Employee employee){
        EmployeeDTO dto = new EmployeeDTO();
        BeanUtils.copyProperties(employee,dto);
        return dto;
    }

}
