package com.g41.trashsmart_server.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.g41.trashsmart_server.Enums.TruckStatus;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class GarbageTruck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String licencePlateNo;
    private Integer mileage;
    private Integer maxVolume;
    private Boolean deleted = false;
    private TruckStatus truckStatus = TruckStatus.IDLE;
    @OneToMany(mappedBy = "garbageTruck")
    @JsonManagedReference(value = "garbageTruck-back-ref")
    private List<Dispatch> dispatches;

    public GarbageTruck() {
    }

    public GarbageTruck(String licencePlateNo, Integer mileage, Integer maxVolume) {
        this.licencePlateNo = licencePlateNo;
        this.mileage = mileage;
        this.maxVolume = maxVolume;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicencePlateNo() {
        return licencePlateNo;
    }

    public void setLicencePlateNo(String licencePlateNo) {
        this.licencePlateNo = licencePlateNo;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public Integer getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(Integer maxVolume) {
        this.maxVolume = maxVolume;
    }

    public TruckStatus getTruckStatus() {
        return truckStatus;
    }

    public void setTruckStatus(TruckStatus truckStatus) {
        this.truckStatus = truckStatus;
    }

    public List<Dispatch> getDispatches() {
        return dispatches;
    }

    public void setDispatches(List<Dispatch> dispatches) {
        this.dispatches = dispatches;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
