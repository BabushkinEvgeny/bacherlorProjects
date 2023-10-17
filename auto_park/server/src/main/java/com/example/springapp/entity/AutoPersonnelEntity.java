package com.example.springapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "auto_personnel")
public class AutoPersonnelEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Integer id;

  @Column(name = "first_name", nullable = false, length = 20)
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 20)
  private String lastName;

  @Column(name = "pather_name", nullable = false, length = 20)
  private String patherName;

  public AutoPersonnelEntity() {
  }

  public AutoPersonnelEntity(String firstName, String lastName, String patherName) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.patherName = patherName;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String first_name) {
    this.firstName = first_name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String last_name) {
    this.lastName = last_name;
  }

  public String getPatherName() {
    return patherName;
  }

  public void setPatherName(String pather_name) {
    this.patherName = pather_name;
  }
}
