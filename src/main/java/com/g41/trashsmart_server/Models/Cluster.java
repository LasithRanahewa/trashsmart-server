package com.g41.trashsmart_server.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

// Cluster class to represent a cluster of waste collection requests
@Entity
@Table
public class Cluster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double latitude;
    private double longitude;
    private double volume;
    @OneToMany(mappedBy = "cluster")
    @JsonManagedReference
    private final List<WasteCollectionRequest> wasteCollectionRequests;

    public Cluster(int id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.volume = 0.0;
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

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
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
