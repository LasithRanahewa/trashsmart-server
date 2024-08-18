package com.g41.trashsmart_server.Models;

import com.g41.trashsmart_server.Enums.BinSize;
import com.g41.trashsmart_server.Enums.BinStatus;
import com.g41.trashsmart_server.Enums.WasteType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDate;

@MappedSuperclass
public abstract class SmartBin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double longitude;
    private Double latitude;
    private LocalDate lastMaintenanceDate = LocalDate.now();
    private Double fillLevel = 0.0;
    private BinStatus binStatus = BinStatus.EMPTY;
    private WasteType wasteType;
    private LocalDate lastCollectionDate = LocalDate.now();
    private BinSize binSize;

    public SmartBin() {
    }

    public SmartBin(Double longitude, Double latitude, WasteType wasteType, BinSize binSize) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.wasteType = wasteType;
        this.binSize = binSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public LocalDate getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }

    public void setLastMaintenanceDate(LocalDate lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    public Double getFillLevel() {
        return fillLevel;
    }

    public void setFillLevel(Double fillLevel) {
        this.fillLevel = fillLevel;
    }

    public BinStatus getBinStatus() {
        return binStatus;
    }

    public void setBinStatus(BinStatus binStatus) {
        this.binStatus = binStatus;
    }

    public WasteType getWasteType() {
        return wasteType;
    }

    public void setWasteType(WasteType wasteType) {
        this.wasteType = wasteType;
    }

    public LocalDate getLastCollectionDate() {
        return lastCollectionDate;
    }

    public void setLastCollectionDate(LocalDate lastCollectionDate) {
        this.lastCollectionDate = lastCollectionDate;
    }

    public BinSize getBinSize() {
        return binSize;
    }

    public void setBinSize(BinSize binSize) {
        this.binSize = binSize;
    }
}
