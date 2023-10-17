package com.example.springapp.model;

import com.example.springapp.entity.RouteEntity;

public class Route
{

  private String name;

  public static Route toModel(RouteEntity entity)
  {
    Route model = new Route();
    model.setName(entity.getName());
    return model;
  }

  public Route()
  {
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }
}
