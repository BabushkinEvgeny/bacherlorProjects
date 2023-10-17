package com.example.springapp.service;

import com.example.springapp.entity.AutoEntity;
import com.example.springapp.entity.JournalEntity;
import com.example.springapp.entity.RouteEntity;
import com.example.springapp.exception.CarIsBusy;
import com.example.springapp.exception.CarNotFound;
import com.example.springapp.exception.RouteNotFound;
import com.example.springapp.exception.TimeLapseError;
import com.example.springapp.repository.AutoRepo;
import com.example.springapp.repository.JournalRepo;
import com.example.springapp.repository.RouteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class JournalService {

  @Autowired
  private JournalRepo journalRepo;

  @Autowired
  private AutoRepo autoRepo;

  @Autowired
  private RouteRepo routeRepo;

  public JournalEntity addNewJournal(Timestamp time_in, Timestamp time_out, Integer route_id, Integer auto_id) throws TimeLapseError, CarNotFound, CarIsBusy {
    Optional<AutoEntity> auto = autoRepo.findById(auto_id);
    Optional<RouteEntity> route = routeRepo.findById(route_id);

    if (time_out.before(time_in)) {
      throw new TimeLapseError("Машина не могла приехать раньше чем уехала");
    }

    Optional<AutoEntity> currentAuto = autoRepo.findById(auto_id);

    if (currentAuto.isEmpty()) {
      throw new CarNotFound("Машины с таким id не существует");
    }

    List<JournalEntity> journalList = journalRepo.findJournalEntityByAutoId(currentAuto.get());

    for (JournalEntity record : journalList) {
      if (((record.getTimeIn().before(time_in) || (record.getTimeIn().equals(time_in)))
        && (record.getTimeOut().after(time_in) || record.getTimeOut().equals(time_in)))
        || ((record.getTimeIn().before(time_out) || record.getTimeIn().equals(time_out))
        && (record.getTimeIn().after(time_in) || record.getTimeIn().equals(time_in)))
        || ((record.getTimeIn().before(time_in) || record.getTimeIn().equals(time_in))
        && (record.getTimeOut().after(time_out) || record.getTimeOut().equals(time_out)))) {
        throw new CarIsBusy("Машина занята");
      }
    }

    if (auto.isPresent() && route.isPresent()) {
      JournalEntity newJournal = new JournalEntity(time_in, time_out, auto.get(), route.get());
      return journalRepo.save(newJournal);
    }
    throw new RuntimeException();
  }

  public List<JournalEntity> getAllJournals() {
    return (List<JournalEntity>) journalRepo.findAll();
  }

  public Boolean deleteById(Integer id) {
    Optional<JournalEntity> journal = journalRepo.findById(id);
    if (journal.isPresent()) {
      journalRepo.deleteById(id);
      return true;
    }
    return false;
  }

  public List<JournalEntity> getByRoute(Integer routeId) throws RouteNotFound {
    Optional<RouteEntity> route = routeRepo.findById(routeId);
    if (!route.isPresent()) {
      throw new RouteNotFound("Маршрута с таким id не существует");
    }
    List<JournalEntity> list = journalRepo.findAllByRouteId(route.get());
    return list;
  }
}
