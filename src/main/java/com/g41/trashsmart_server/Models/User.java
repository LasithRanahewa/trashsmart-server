package com.g41.trashsmart_server.Models;

import com.g41.trashsmart_server.Enums.Role;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String contactNo;
    private String address;
    private Role role;
    private Boolean deleted = false;
    private String profileURL;
    private LocalDateTime createdTimeStamp = LocalDateTime.now();

    // No Args Constructor
    public User() {
    }

    // For Contractor Registration
    public User(String firstName, String lastName, String email, String password, Role role,
                LocalDateTime createdTimeStamp) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdTimeStamp = createdTimeStamp;
    }

    // For HouseholdUser/OrganizationUser/RecyclingPlant Email/ContactNo Registration
    public User(String firstName, String lastName, String email, String password, String contactNo, String address,
                Role role, String profileURL, LocalDateTime createdTimeStamp) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.contactNo = contactNo;
        this.address = address;
        this.role = role;
        this.profileURL = profileURL;
        this.createdTimeStamp = createdTimeStamp;
    }

    // For Driver/Cleaner ContactNo Registration (no email)
    public User(String firstName, String lastName, String password, String contactNo, String address, Role role,
                String profileURL, LocalDateTime createdTimeStamp) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.contactNo = contactNo;
        this.address = address;
        this.role = role;
        this.profileURL = profileURL;
        this.createdTimeStamp = createdTimeStamp;
    }

    // For Driver/Cleaner Email Registration with ContactNo
    public User(String firstName, String lastName, String password, String contactNo, String address, Role role,
                String email, String profileURL, LocalDateTime createdTimeStamp) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.contactNo = contactNo;
        this.address = address;
        this.role = role;
        this.email = email;
        this.profileURL = profileURL;
        this.createdTimeStamp = createdTimeStamp;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    public LocalDateTime getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(LocalDateTime createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public boolean isDeleted() {
        return deleted;
    }
}