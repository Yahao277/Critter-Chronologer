package com.udacity.jdnd.course3.critter.controllers;

import com.google.j2objc.annotations.AutoreleasePool;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertToPet(petDTO);
        Long savedId = petService.savePet(pet);
        return convertToPetDTO(petService.getOne(savedId));
    }

    @PostMapping("/{ownerId}")
    public PetDTO savePetWithId(@RequestBody PetDTO petDTO,@PathVariable long ownerId){
        petDTO.setOwnerId(ownerId);
        Pet pet = convertToPet(petDTO);
        Long savedId = petService.savePet(pet);
        return convertToPetDTO(petService.getOne(savedId));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        PetDTO petDTO = convertToPetDTO(petService.findPetById(petId));
        return petDTO;
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.findAllPets();
        return pets.stream().map(p -> convertToPetDTO(p)).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable Long ownerId) {
        List<Pet> pets = petService.findPetsByOwner(ownerId);
        return pets.stream().map(p -> convertToPetDTO(p)).collect(Collectors.toList());
    }

    private Pet convertToPet(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO,pet);
        pet.setOwner(customerService.getOne(petDTO.getOwnerId()));
        return pet;
    }

    private PetDTO convertToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet,petDTO);
        petDTO.setOwnerId(pet.getOwner().getId());
        return petDTO;
    }
}
