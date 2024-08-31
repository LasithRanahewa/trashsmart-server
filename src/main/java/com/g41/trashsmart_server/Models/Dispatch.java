package com.g41.trashsmart_server.Models;

import com.g41.trashsmart_server.Enums.DispatchStatus;
import com.g41.trashsmart_server.Enums.DispatchType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class Dispatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTime;
    private DispatchStatus dispatchStatus = DispatchStatus.NEW;
    @ManyToOne
    @JoinColumn(name = "suburb_id")
    private Suburb suburb;
    @ManyToOne
    @JoinColumn(name = "truck_id")
    private GarbageTruck garbageTruck;
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;
    private DispatchType dispatchType;
    private LocalDateTime createdDateTime;

    public Dispatch() {
    }

    public Dispatch(LocalDateTime dateTime, Suburb suburb, GarbageTruck garbageTruck, Driver driver,
                    DispatchType dispatchType) {
        this.dateTime = dateTime;
        this.suburb = suburb;
        this.garbageTruck = garbageTruck;
        this.driver = driver;
        this.dispatchType = dispatchType;
        this.createdDateTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public DispatchStatus getDispatchStatus() {
        return dispatchStatus;
    }

    public void setDispatchStatus(DispatchStatus dispatchStatus) {
        this.dispatchStatus = dispatchStatus;
    }

    public Suburb getSuburb() {
        return suburb;
    }

    public void setSuburb(Suburb suburb) {
        this.suburb = suburb;
    }

    public GarbageTruck getGarbageTruck() {
        return garbageTruck;
    }

    public void setGarbageTruck(GarbageTruck garbageTruck) {
        this.garbageTruck = garbageTruck;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public DispatchType getDispatchType() {
        return dispatchType;
    }

    public void setDispatchType(DispatchType dispatchType) {
        this.dispatchType = dispatchType;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
}
