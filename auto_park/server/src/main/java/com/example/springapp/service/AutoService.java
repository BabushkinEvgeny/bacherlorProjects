package com.example.springapp.service;

import com.example.springapp.entity.AutoEntity;
import com.example.springapp.entity.AutoPersonnelEntity;
import com.example.springapp.exception.*;
import com.example.springapp.repository.AutoPersonnelRepo;
import com.example.springapp.repository.AutoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutoService {

  @Autowired
  private AutoRepo autoRepo;

  @Autowired
  private AutoPersonnelRepo autoPersonnelRepo;

  public AutoEntity addNewAuto(String num, String color, String mark, Integer personnel_id) throws CarAlreadyExist, DriverIsBusy, PersonnelNotFound, TooManyCharacters {
    if (num.length() > 20 || color.length() > 20 || mark.length() > 20) {
      throw new TooManyCharacters("Длина строки не должна превышать 20 символов");
    }

    Optional<AutoPersonnelEntity> autoPersonnel = autoPersonnelRepo.findById(personnel_id);
    Optional<AutoEntity> auto = autoRepo.findByNum(num);

    if (auto.isPresent()) {
      throw new CarAlreadyExist("Машина с таким номером уже зарегестрирована");
    }

    auto = autoRepo.findByPersonnelId(autoPersonnelRepo.findById(personnel_id));

    if (auto.isPresent()) {
      throw new DriverIsBusy("Водитель закреплён за другой машиной");
    }

    if (autoPersonnel.isPresent()) {
      AutoEntity autoEntity = new AutoEntity(num, color, mark, autoPersonnel.get());
      return autoRepo.save(autoEntity);
    }
    throw new PersonnelNotFound("Пользователь не найден");
  }

  public Boolean deleteById(Integer id) {
    System.out.println("вошли в deletebyid");
    Optional<AutoEntity> auto = autoRepo.findById(id);
    if (auto.isPresent()) {
      autoRepo.deleteById(id);
      return true;
    }
    return false;
  }

  public List<AutoEntity> getAllAutos() {
    return (List<AutoEntity>) autoRepo.findAll();
  }

  public AutoEntity newColor(Integer id, String newColor) throws CarNotFound, TooManyCharacters {
    if (newColor.length() > 20) {
      throw new TooManyCharacters("Длина строки не должна превышать 20 символов");
    }

    Optional<AutoEntity> auto = autoRepo.findById(id);
    if (auto.isEmpty()) {
      throw new CarNotFound("Машина не найдена");
    }
    auto.get().setColor(newColor);
    return autoRepo.save(auto.get());
  }

  public AutoEntity newNum(Integer id, String newNum) throws CarNotFound, CarAlreadyExist, TooManyCharacters {
    if (newNum.length() > 20) {
      throw new TooManyCharacters("Длина строки не должна превышать 20 символов");
    }

    Optional<AutoEntity> auto = autoRepo.findById(id);
    if (auto.isEmpty()) {
      throw new CarNotFound("Машина с таким id не найдена");
    }

    Optional<AutoEntity> auto_1 = autoRepo.findByNum(newNum);
    if (auto_1.isPresent()) {
      throw new CarAlreadyExist("Такой номер уже есть");
    }
    auto.get().setNum(newNum);
    return autoRepo.save(auto.get());
  }
}
