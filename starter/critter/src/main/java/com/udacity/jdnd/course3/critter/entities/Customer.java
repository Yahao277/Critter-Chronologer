package com.udacity.jdnd.course3.critter.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String phoneNumber;

  private String notes;

  @OneToMany
  private List<Pet> pets;

  public Customer(){
    this.pets = new ArrayList<>();
  }

  /* getters and setters*/
  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPhoneNumber(String phoneNumber){ this.phoneNumber = phoneNumber;}

  public String getPhoneNumber(){ return this.phoneNumber;}

  public void setNotes(String notes){ this.notes = notes;}

  public String getNotes(){return this.notes;}

  public void setPets(List<Pet> pets){this.pets = pets;}

  public List<Pet> getPets(){return this.pets;}

}
