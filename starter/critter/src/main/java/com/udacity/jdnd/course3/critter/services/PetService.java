package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.exceptions.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.exceptions.PetNotFoundException;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {
  @Autowired
  private PetRepository petRepository;

  @Autowired
  private CustomerService customerService;

  public Pet findPetById(Long id){
    Optional<Pet> petOptional = this.petRepository.findById(id);
    if(petOptional.isPresent()){
      return petOptional.get();
    }else{
      throw new PetNotFoundException("Pet not exists");
    }
  }

  public Long savePet(Pet pet){
    Pet savedPet = this.petRepository.save(pet);
    if(savedPet.getOwner() != null) {
      customerService.addPetToCustomer(savedPet, savedPet.getOwner().getId());
    }
    return savedPet.getId();
  }

  public Pet getOne(Long id){
    return this.petRepository.getOne(id);
  }

  public List<Pet> findAllPets(){
    return this.petRepository.findAll();
  }

  public List<Pet> findPetsByOwner(Long ownerId){
    List<Pet> pets = this.petRepository.findByOwnerId(ownerId);
    return pets;
  }

}
