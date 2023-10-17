package com.example.springapp.repository;

import com.example.springapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, BigInteger> {
  Optional<User> findUserByUsername(String username);
}
