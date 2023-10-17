package com.example.springapp.repository;

import com.example.springapp.entity.RouteEntity;
import org.springframework.data.repository.CrudRepository;

public interface RouteRepo extends CrudRepository<RouteEntity, Integer> {
  RouteEntity findByName(String name);
}
