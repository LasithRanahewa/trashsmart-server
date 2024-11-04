package com.g41.trashsmart_server.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

// Cluster class to represent a cluster of waste collection requests
public class Cluster {
    private int id;
    private double latitude;
    private double longitude;
    @OneToMany(mappedBy = "cluster")
    @JsonManagedReference
    private final List<WasteCollectionRequest> wasteCollectionRequests;

    public Cluster(int id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.wasteCollectionRequests = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<WasteCollectionRequest> getWasteCollectionRequests() {
        return wasteCollectionRequests;
    }

    public void addWasteCollectionRequests(WasteCollectionRequest wasteCollectionRequest) {
        wasteCollectionRequests.add(wasteCollectionRequest);
    }

    public void clearWasteCollectionRequests() {
        wasteCollectionRequests.clear();
    }
}
