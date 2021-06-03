package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {
  @Autowired
  private CustomerRepository customerRepository;

  public Long saveCustomer(Customer customer){
    Customer saved = this.customerRepository.save(customer);
    return saved.getId();
  }

  public Optional<Customer> findById(Long id){
    return this.customerRepository.findById(id);
  }

  public Customer getOne(Long id){ return this.customerRepository.getOne(id);}

  public List<Customer> getAllCustomers(){
    return this.customerRepository.findAll();
  }

  public void addPetToCustomer(Pet pet,Long id){
    Customer customer = this.customerRepository.getOne(id);
    List<Pet> pets = customer.getPets();
    pets.add(pet);
    customer.setPets(pets);
    this.customerRepository.save(customer);
  }


}
