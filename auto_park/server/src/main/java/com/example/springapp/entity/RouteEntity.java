package com.example.springapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "routes")
public class RouteEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Integer id;

  @Column(name = "name", nullable = false, length = 20)
  private String name;

  public RouteEntity() {
  }

  public RouteEntity(String name) {
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
