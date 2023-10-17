package com.example.springapp.repository;

import com.example.springapp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface RoleRepository extends JpaRepository<Role, BigInteger> {
  Role findByName(String name);
}
