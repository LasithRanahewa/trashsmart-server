package com.g41.trashsmart_server.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Double latitude;
    private Double longitude;
    private WasteCollectionRequestStatus wasteCollectionRequestStatus = WasteCollectionRequestStatus.NEW;
    private LocalDateTime createdTimeStamp = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "organization_id")
    @JsonBackReference(value = "organization-back-ref")
    private Organization organization;
    @ManyToOne
    @JoinColumn(name = "dispatch_id")
    @JsonBackReference(value = "organizationDispatch-back-ref")
    private OrganizationDispatch organizationDispatch;
    @ManyToOne
    @JoinColumn(name = "cluster_id")
    @JsonBackReference(value = "cluster-back-ref")
    private Cluster cluster;

    public WasteCollectionRequest() {
    }

    public WasteCollectionRequest(Double accumulatedVolume, WasteType wasteType, Double latitude, Double longitude) {
        this.accumulatedVolume = accumulatedVolume;
        this.wasteType = wasteType;
        this.longitude = longitude;
        this.latitude = latitude;
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

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }
}
