package com.g41.trashsmart_server.Models;

import com.g41.trashsmart_server.Enums.Role;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BusinessUser extends User {
    public BusinessUser() {
    }

    // For HouseholdUser/OrganizationUser/RecyclingPlant Email/ContactNo Registration
    public BusinessUser(String firstName, String lastName, String email, String password, String contactNo,
                        String address, String profileURL, LocalDateTime createdTimeStamp) {
        super(firstName, lastName, email, password, contactNo, address, profileURL, createdTimeStamp);
    }
}
