package com.g41.trashsmart_server.Models;

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
    private List<Dispatch> dispatches;

    public GarbageTruck() {
    }

    public GarbageTruck(String licencePlateNo, Integer mileage, Integer maxVolume) {
        this.licencePlateNo = licencePlateNo;
        this.mileage = mileage;
        this.maxVolume = maxVolume;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
