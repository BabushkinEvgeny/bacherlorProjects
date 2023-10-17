package com.example.springapp.controller;

import com.example.springapp.entity.RouteEntity;
import com.example.springapp.exception.RouteAlreadyExist;
import com.example.springapp.exception.RouteNotFound;
import com.example.springapp.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/routes")
public class RouteController {

  @Autowired
  private RouteService routeService;

  @PostMapping("/addNew")
  public ResponseEntity addNewRoute(@RequestBody RouteEntity route) {
    try {
      routeService.addNewRoute(route);
      return ResponseEntity.ok("Маршрут сохранён.");
    } catch (RouteAlreadyExist alreadyExist) {
      return ResponseEntity.badRequest().body(alreadyExist.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Error route");
    }
  }

  @GetMapping("/getRouteById")
  public ResponseEntity getOneRoute(@RequestParam Integer id) {
    try {
      return ResponseEntity.ok(routeService.getOne(id));
    } catch (RouteNotFound e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Error");
    }
  }

  @GetMapping("/getAll")
  public ResponseEntity getAllRoutes() {
    return ResponseEntity.ok(routeService.getAllRoutes());
  }

  @DeleteMapping("/deleteByName")
  public ResponseEntity deleteRouteByName(@RequestParam String name) {
    return ResponseEntity.ok(routeService.deleteByName(name));
  }

  @DeleteMapping("/deleteAll")
  public ResponseEntity deleteRoute() {
    try {
      return ResponseEntity.ok(routeService.deleteAllRoutes());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Error");
    }
  }
}
