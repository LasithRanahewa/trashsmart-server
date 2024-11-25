package com.g41.trashsmart_server.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.g41.trashsmart_server.Enums.BinSize;
import com.g41.trashsmart_server.Enums.BinStatus;
import com.g41.trashsmart_server.Enums.WasteType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "binType")
public class SmartBin {
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
    private Boolean deleted = false;
    @OneToMany(mappedBy = "smartBin")
    @JsonManagedReference
    private List<MaintenanceRequest> maintenanceRequests;
    @ManyToOne
    @JoinColumn(name = "suburb_id")
    private Suburb suburb;
    @Column(name = "APIKEY", unique = true, nullable = false)
    private String APIKEY;

    public SmartBin() {
    }

    public SmartBin(Double longitude, Double latitude, WasteType wasteType, BinSize binSize, String APIKEY) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.wasteType = wasteType;
        this.binSize = binSize;
        this.APIKEY = APIKEY;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public List<MaintenanceRequest> getMaintenanceRequests() {
        return maintenanceRequests;
    }

    public void setMaintenanceRequests(List<MaintenanceRequest> maintenanceRequests) {
        this.maintenanceRequests = maintenanceRequests;
    }

    public Suburb getSuburb() {
        return suburb;
    }

    public void setSuburb(Suburb suburb) {
        this.suburb = suburb;
    }

    public String getAPIKEY() {
        return APIKEY;
    }

    public void setAPIKEY(String APIKEY) {
        this.APIKEY = APIKEY;
    }
}
