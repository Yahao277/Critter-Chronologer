package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.enums.PetType;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class Pet {
  @Id
  @GeneratedValue
  private Long id;

  @Enumerated(EnumType.STRING)
  private PetType type;

  @Nationalized
  private String name;

  @ManyToOne(targetEntity = Customer.class)
  private Customer owner;

  private LocalDate birthDate;
  private String notes;

  /*getters and setters*/

  public void setId(Long id){this.id = id;}

  public Long getId(){return this.id;}

  public void setType(PetType type){this.type = type;}

  public PetType getType(){ return this.type;}

  public String getName(){return this.name;}

  public void setName(String name){ this.name = name;}

  public void setOwner(Customer owner){ this.owner = owner;}

  public Customer getOwner(){return this.owner;}

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getNotes() {
    return notes;
  }
}

