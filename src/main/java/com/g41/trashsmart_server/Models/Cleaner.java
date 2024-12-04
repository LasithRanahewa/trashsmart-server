package com.g41.trashsmart_server.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.g41.trashsmart_server.Enums.Role;
import com.g41.trashsmart_server.Enums.Status;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
public class Cleaner extends SystemUser {
    private Status status = Status.ACTIVE;
    private Integer totalCollections = 0;
    private Integer currentStreak = 0;
    private Integer longestStreak = 0;
    private LocalDate lastCollectionDate;
    private Integer totalActiveDays = 0;
    private Integer numberOfHolidays = 0;
    @ManyToMany
    @JoinTable(
            name = "cleaner_assigned_to_bin",
            joinColumns = @JoinColumn(name = "cleaner_id"),
            inverseJoinColumns = @JoinColumn(name = "bin_id")
    )
//    @JsonManagedReference
    private List<CommunalBin> communalBins;

    public Cleaner() {
        this.setRole(Role.CLEANER);
    }

    // Cleaner ContactNo Registration no Email
    public Cleaner(String firstName, String lastName, String password, String contactNo, String address,
                   String profileURL, LocalDate dob, String nic) {
        super(firstName, lastName, password, contactNo, address, profileURL, LocalDateTime.now(), dob, nic);
        this.setRole(Role.CLEANER);
    }

    // Cleaner Email Registration
    public Cleaner(String firstName, String lastName, String password, String contactNo, String address,
                   String email, String profileURL, LocalDate dob, String nic) {
        super(firstName, lastName, password, contactNo, address, email, profileURL, LocalDateTime.now(), dob, nic);
        this.setRole(Role.CLEANER);
    }

    // Getters and Setters
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getTotalCollections() {
        return totalCollections;
    }

    public void setTotalCollections(Integer totalCollections) {
        this.totalCollections = totalCollections;
    }

    public Integer getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(Integer currentStreak) {
        this.currentStreak = currentStreak;
    }

    public Integer getLongestStreak() {
        return longestStreak;
    }

    public void setLongestStreak(Integer longestStreak) {
        this.longestStreak = longestStreak;
    }

    public LocalDate getLastCollectionDate() {
        return lastCollectionDate;
    }

    public void setLastCollectionDate(LocalDate lastCollectionDate) {
        this.lastCollectionDate = lastCollectionDate;
    }

    public Integer getTotalActiveDays() {
        return totalActiveDays;
    }

    public void setTotalActiveDays(Integer totalActiveDays) {
        this.totalActiveDays = totalActiveDays;
    }

    public Integer getNumberOfHolidays() {
        return numberOfHolidays;
    }

    public void setNumberOfHolidays(Integer numberOfHolidays) {
        this.numberOfHolidays = numberOfHolidays;
    }

    public List<CommunalBin> getCommunalBins() {
        return communalBins;
    }

    public void setCommunalBins(List<CommunalBin> communalBins) {
        this.communalBins = communalBins;
    }
}
