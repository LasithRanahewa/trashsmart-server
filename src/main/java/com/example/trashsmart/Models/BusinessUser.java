package com.example.trashsmart.Models;

import com.example.trashsmart.Enums.Role;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BusinessUser extends User {
    public BusinessUser() {
    }

    // For HouseholdUser/OrganizationUser/RecyclingPlant Email/ContactNo Registration
    public BusinessUser(String firstName, String lastName, String email, String password, String contactNo,
                        String address, Role role, String profileURL, LocalDateTime createdTimeStamp) {
        super(firstName, lastName, email, password, contactNo, address, role, profileURL, createdTimeStamp);
    }
}
