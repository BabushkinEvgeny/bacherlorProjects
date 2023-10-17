package com.example.springapp;

import com.example.springapp.entity.*;

import com.example.springapp.repository.*;
import com.example.springapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

@Component
public class Fill implements CommandLineRunner {
    @Autowired
    AutoPersonnelRepo autoPersonel;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AutoRepo autoRepo;

    @Autowired
    JournalRepo journalRepo;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RouteRepo routeRepo;

    @Autowired
    UserService userService;

    @Override
    public void run(String... args) throws Exception {
        userService.saveRole(new Role(null, "ROLE_USER"));
        userService.saveRole(new Role(null, "ROLE_ADMIN"));
        userService.saveUser(new User(null, "Evgeny Babushkin", "admin", "admin", new ArrayList<>()));
        userService.saveUser(new User(null, "Sergei Lappo", "user", "user", new ArrayList<>()));
        userService.addRoleToUser("user", "ROLE_USER");
        userService.addRoleToUser("admin", "ROLE_ADMIN");

        AutoPersonnelEntity vova = new AutoPersonnelEntity("vova", "coca", "boba");
        autoPersonel.save(vova);
        AutoEntity car1 = new AutoEntity("a111aa","black","volvo", vova);
        autoRepo.save(car1);
        RouteEntity route1 = new RouteEntity("Москва - Питер");
        routeRepo.save(route1);
    }
}
