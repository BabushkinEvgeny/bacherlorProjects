package com.example.springapp.repository;

import com.example.springapp.entity.AutoEntity;
import com.example.springapp.entity.AutoPersonnelEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AutoRepo extends CrudRepository<AutoEntity, Integer> {
  Optional<AutoEntity> findByNum(String num);

  Optional<AutoEntity> findByPersonnelId(Optional<AutoPersonnelEntity> personnelId);
}
