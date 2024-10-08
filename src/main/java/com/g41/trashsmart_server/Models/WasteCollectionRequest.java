package com.g41.trashsmart_server.Models;

import com.g41.trashsmart_server.Enums.WasteCollectionRequestStatus;
import com.g41.trashsmart_server.Enums.WasteType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class WasteCollectionRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double accumulatedVolume;
    private WasteType wasteType;
    private WasteCollectionRequestStatus wasteCollectionRequestStatus = WasteCollectionRequestStatus.NEW;
    private LocalDateTime createdTimeStamp = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    public WasteCollectionRequest() {
    }

    public WasteCollectionRequest(Double accumulatedVolume, WasteType wasteType) {
        this.accumulatedVolume = accumulatedVolume;
        this.wasteType = wasteType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAccumulatedVolume() {
        return accumulatedVolume;
    }

    public void setAccumulatedVolume(Double accumulatedVolume) {
        this.accumulatedVolume = accumulatedVolume;
    }

    public WasteType getWasteType() {
        return wasteType;
    }

    public void setWasteType(WasteType wasteType) {
        this.wasteType = wasteType;
    }

    public WasteCollectionRequestStatus getWasteCollectionRequestStatus() {
        return wasteCollectionRequestStatus;
    }

    public void setWasteCollectionRequestStatus(WasteCollectionRequestStatus wasteCollectionRequestStatus) {
        this.wasteCollectionRequestStatus = wasteCollectionRequestStatus;
    }

    public LocalDateTime getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(LocalDateTime createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
