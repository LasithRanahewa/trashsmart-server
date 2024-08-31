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
    private Integer longitude;
    private Integer latitude;
    private WasteCollectionRequestStatus wasteCollectionRequestStatus = WasteCollectionRequestStatus.NEW;
    private LocalDateTime createdTimeStamp = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
    @ManyToOne
    @JoinColumn(name = "dispatch_id")
    private OrganizationDispatch organizationDispatch;

    public WasteCollectionRequest() {
    }

    public WasteCollectionRequest(Double accumulatedVolume, WasteType wasteType, Integer longitude, Integer latitude) {
        this.accumulatedVolume = accumulatedVolume;
        this.wasteType = wasteType;
        this.longitude = longitude;
        this.latitude = latitude;
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

    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
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

    public OrganizationDispatch getOrganizationDispatch() {
        return organizationDispatch;
    }

    public void setOrganizationDispatch(OrganizationDispatch organizationDispatch) {
        this.organizationDispatch = organizationDispatch;
    }
}
