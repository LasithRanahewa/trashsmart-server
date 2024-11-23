package com.g41.trashsmart_server.Models;

import com.g41.trashsmart_server.Enums.Role;
import com.g41.trashsmart_server.Enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.*;

import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table
public class Driver extends SystemUser implements UserDetails{
    private Status status = Status.ACTIVE;
    private Integer totalCollections = 0;
    private Integer currentStreak = 0;
    private Integer longestStreak = 0;
    private Integer totalActiveDays = 0;
    private Integer numberOfHolidays = 0;
    @OneToMany(mappedBy = "driver")
    private List<Dispatch> dispatches;

    public Driver() {
        this.setRole(Role.DRIVER);
    }

    // Driver ContactNo Registration no Email
    public Driver(String firstName, String lastName, String password, String contactNo, String address, String profileURL,
                  LocalDate dob, String nic) {
        super(firstName, lastName, password, contactNo, address, profileURL, LocalDateTime.now(), dob, nic);
        this.setRole(Role.DRIVER);
    }

    // Driver Email Registration
    public Driver(String firstName, String lastName, String password, String contactNo, String address, String email,
                  String profileURL, LocalDate dob, String nic) {
        super(firstName, lastName, password, contactNo, address, email, profileURL, LocalDateTime.now(), dob, nic);
        this.setRole(Role.DRIVER);
    }

    // Getters and Setters
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(super.getRole().name()));
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    public String getUsername() {
        return super.getEmail();
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

    public List<Dispatch> getDispatches() {
        return dispatches;
    }

    public void setDispatches(List<Dispatch> dispatches) {
        this.dispatches = dispatches;
    }
}
