package com.example.springapp.model;

import com.example.springapp.entity.AutoEntity;
import com.example.springapp.entity.RouteEntity;

public class Auto
{
  private String num;
  private String color;
  private String mark;
  private Integer personnelId;

  public static Auto toModel(AutoEntity entity)
  {
    Auto model = new Auto();
    model.setNum(entity.getNum());
    model.setColor(entity.getNum());
    model.setMark(entity.getNum());
    model.setPersonnelId(entity.getPersonnelId().getId());
    return model;
  }

  public Auto()
  {
  }

  public Auto(String num, String color, String mark, Integer personnelId)
  {
    this.num = num;
    this.color = color;
    this.mark = mark;
    this.personnelId = personnelId;
  }

  public String getNum()
  {
    return num;
  }

  public void setNum(String num)
  {
    this.num = num;
  }

  public String getColor()
  {
    return color;
  }

  public void setColor(String color)
  {
    this.color = color;
  }

  public String getMark()
  {
    return mark;
  }

  public void setMark(String mark)
  {
    this.mark = mark;
  }


  public Integer getPersonnelId()
  {
    return personnelId;
  }

  public void setPersonnelId(Integer personnelId)
  {
    this.personnelId = personnelId;
  }
}
