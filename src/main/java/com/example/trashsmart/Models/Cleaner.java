package com.example.trashsmart.Models;

import com.example.trashsmart.Enums.Role;
import com.example.trashsmart.Enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public Cleaner() {
    }

    // Cleaner ContactNo Registration no Email
    public Cleaner(String firstName, String lastName, String password, String contactNo, String address, Role role,
                   String profileURL, LocalDateTime createdTimeStamp, LocalDate dob, String nic) {
        super(firstName, lastName, password, contactNo, address, role, profileURL, createdTimeStamp, dob, nic);
    }

    // Cleaner Email Registration

    public Cleaner(String firstName, String lastName, String password, String contactNo, String address, Role role,
                   String email, String profileURL, LocalDateTime createdTimeStamp, LocalDate dob, String nic) {
        super(firstName, lastName, password, contactNo, address, role, email, profileURL, createdTimeStamp, dob, nic);
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
}
