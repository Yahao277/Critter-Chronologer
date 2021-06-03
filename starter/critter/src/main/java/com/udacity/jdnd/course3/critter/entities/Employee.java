package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Table
public class Employee{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ElementCollection
  private Set<EmployeeSkill> skills;

  @ElementCollection
  private Set<DayOfWeek> daysAvailable;


  /*getters and setters*/
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

  public void setSkills(Set<EmployeeSkill> skills){ this.skills = skills;}

  public Set<EmployeeSkill> getSkills(){ return this.skills;}

  public void setDaysAvailable(Set<DayOfWeek> daysAvailable) { this.daysAvailable = daysAvailable;}

  public Set<DayOfWeek> getDaysAvailable(){ return this.daysAvailable;}
}
