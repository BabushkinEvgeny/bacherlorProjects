package com.example.springapp.controller;

import com.example.springapp.entity.AutoPersonnelEntity;
import com.example.springapp.service.AutoPersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personnels")
public class AutoPersonnelController {
  @Autowired
  private AutoPersonnelService autoPersonnelService;


  @PostMapping("/addNew")
  public ResponseEntity addNewAutoPersonnel(@RequestBody AutoPersonnelEntity auto) {
    try {
      return ResponseEntity.ok(autoPersonnelService.addNewAutoPersonnel(auto));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/getAll")
  public ResponseEntity getAllPersonnels() {
    return ResponseEntity.ok(autoPersonnelService.getAllPersonnels());
  }

  @PutMapping("/setNewName")
  public ResponseEntity updateName(Integer id, String newName) {
    try {
      return ResponseEntity.ok(autoPersonnelService.newName(id, newName));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/deleteById")
  public ResponseEntity deleteById(@RequestParam Integer id) {
    return ResponseEntity.ok(autoPersonnelService.deleteById(id));
  }

}
