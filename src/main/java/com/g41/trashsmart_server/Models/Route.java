package com.g41.trashsmart_server.Models;

import jakarta.persistence.*;

@Entity
@Table
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Suburb suburb;
    private Driver driver;
    private GarbageTruck garbageTruck;

    public Route() {
    }

    public Route(Suburb suburb, Driver driver, GarbageTruck garbageTruck) {
        this.suburb = suburb;
        this.driver = driver;
        this.garbageTruck = garbageTruck;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Suburb getSuburb() {
        return suburb;
    }

    public void setSuburb(Suburb suburb) {
        this.suburb = suburb;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public GarbageTruck getGarbageTruck() {
        return garbageTruck;
    }

    public void setGarbageTruck(GarbageTruck garbageTruck) {
        this.garbageTruck = garbageTruck;
    }
}
