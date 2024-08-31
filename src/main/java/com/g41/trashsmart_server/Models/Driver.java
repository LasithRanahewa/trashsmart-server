package com.g41.trashsmart_server.Models;

import com.g41.trashsmart_server.Enums.Role;
import com.g41.trashsmart_server.Enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table
public class Driver extends SystemUser implements  UserDetails{
    private Status status = Status.ACTIVE;
    private Integer totalCollections = 0;
    private Integer currentStreak = 0;
    private Integer longestStreak = 0;
    private Integer totalActiveDays = 0;
    private Integer numberOfHolidays = 0;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return the authorities granted to the user
        return null; // Replace with actual authorities
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !super.isDeleted();
    }
}
