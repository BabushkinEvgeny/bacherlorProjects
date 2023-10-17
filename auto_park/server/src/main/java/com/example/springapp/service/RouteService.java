package com.example.springapp.service;

import com.example.springapp.entity.RouteEntity;
import com.example.springapp.exception.RouteAlreadyExist;
import com.example.springapp.exception.RouteNotFound;
import com.example.springapp.exception.TooManyCharacters;
import com.example.springapp.model.Route;
import com.example.springapp.repository.RouteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService {
  @Autowired
  private RouteRepo routeRepo;

  public RouteEntity addNewRoute(RouteEntity route) throws RouteAlreadyExist, TooManyCharacters {
    if (route.getName().length() > 20) {
      throw new TooManyCharacters("Длина строки не должна превышать 20 символов");
    }

    if (routeRepo.findByName(route.getName()) != null) {
      throw new RouteAlreadyExist("Маршрут с таким именем уже существует");
    }
    return routeRepo.save(route);
  }

  public Route getOne(Integer id) throws RouteNotFound {
    Optional<RouteEntity> route = routeRepo.findById(id);
    if (route.isEmpty()) {
      throw new RouteNotFound("Route wasn't found.");
    }
    return Route.toModel(route.get());
  }

  public Integer deleteRoute(Integer id) throws RouteNotFound {
    Optional<RouteEntity> route = routeRepo.findById(id);

    if (route.isPresent()) {
      routeRepo.deleteById(id);
      return id;
    }

    throw new RouteNotFound("Маршрута с таким id не существует");
  }

  public Boolean deleteAllRoutes() {
    routeRepo.deleteAll();
    return true;
  }

  public List<RouteEntity> getAllRoutes() {
    return (List<RouteEntity>) routeRepo.findAll();
  }

  public Boolean deleteByName(String name) {
    RouteEntity route = routeRepo.findByName(name);
    routeRepo.deleteById(route.getId());
    return true;
  }
}
