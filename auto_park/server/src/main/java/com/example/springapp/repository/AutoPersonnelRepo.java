package com.example.springapp.repository;

import com.example.springapp.entity.AutoPersonnelEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AutoPersonnelRepo extends CrudRepository<AutoPersonnelEntity, Integer> {
  Optional<AutoPersonnelEntity> findByFirstNameAndLastNameAndPatherName(String firstName, String lastName, String patherName);
}
