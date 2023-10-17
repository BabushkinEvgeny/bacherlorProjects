package com.example.springapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "auto")
public class AutoEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Integer id;

  @Column(name = "num", nullable = false, length = 20)
  private String num;

  @Column(name = "color", nullable = false, length = 20)
  private String color;

  @Column(name = "mark", nullable = false, length = 20)
  private String mark;

  @ManyToOne(optional = false, cascade = CascadeType.MERGE)
  @JoinColumn(name = "personnel_id")
  private AutoPersonnelEntity personnelId;

  public AutoEntity() {
  }

  public AutoEntity(String num, String color, String mark, AutoPersonnelEntity personnelId) {
    this.num = num;
    this.color = color;
    this.mark = mark;
    this.personnelId = personnelId;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNum() {
    return num;
  }

  public void setNum(String num) {
    this.num = num;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getMark() {
    return mark;
  }

  public void setMark(String mark) {
    this.mark = mark;
  }

  public AutoPersonnelEntity getPersonnelId() {
    return personnelId;
  }

  public void setPersonnelId(AutoPersonnelEntity personnel_id) {
    this.personnelId = personnel_id;
  }
}
