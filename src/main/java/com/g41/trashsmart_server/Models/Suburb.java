package com.g41.trashsmart_server.Models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Suburb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    @OneToMany(mappedBy = "suburb")
    private List<HouseholdUser> householdUsers;
    @OneToMany(mappedBy = "suburb")
    private List<SmartBin> smartBins;
    @OneToMany(mappedBy = "suburb")
    private List<Dispatch> dispatches;

    public Suburb() {
    }

    public Suburb(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<HouseholdUser> getHouseholdUsers() {
        return householdUsers;
    }

    public void setHouseholdUsers(List<HouseholdUser> householdUsers) {
        this.householdUsers = householdUsers;
    }

    public List<SmartBin> getSmartBins() {
        return smartBins;
    }

    public void setSmartBins(List<SmartBin> smartBins) {
        this.smartBins = smartBins;
    }

    public List<Dispatch> getDispatches() {
        return dispatches;
    }

    public void setDispatches(List<Dispatch> dispatches) {
        this.dispatches = dispatches;
    }
}
