package com.example.springapp.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "journal")
public class JournalEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Integer id;

  @Column(name = "time_out")
  private Timestamp timeOut;

  @Column(name = "time_in")
  private Timestamp timeIn;

  @ManyToOne(optional = false, cascade = CascadeType.MERGE)
  @JoinColumn(name = "route_id")
  private RouteEntity routeId;

  @ManyToOne(optional = false, cascade = CascadeType.MERGE)
  @JoinColumn(name = "auto_id")
  private AutoEntity autoId;

  public JournalEntity() {
  }

  public JournalEntity(Timestamp timeIn, Timestamp timeOut, AutoEntity autoId, RouteEntity routeId) {
    this.autoId = autoId;
    this.routeId = routeId;
    this.timeOut = timeOut;
    this.timeIn = timeIn;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Timestamp getTimeOut() {
    return timeOut;
  }

  public void setTimeOut(Timestamp time_out) {
    this.timeOut = time_out;
  }

  public Timestamp getTimeIn() {
    return timeIn;
  }

  public void setTimeIn(Timestamp time_in) {
    this.timeIn = time_in;
  }

  public RouteEntity getRouteId() {
    return routeId;
  }

  public void setRouteId(RouteEntity route_id) {
    this.routeId = route_id;
  }

  public AutoEntity getAutoId() {
    return autoId;
  }

  public void setAutoId(AutoEntity auto_id) {
    this.autoId = auto_id;
  }
}
