package com.g41.trashsmart_server.Models;

import com.g41.trashsmart_server.Enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table
public class HouseholdUser extends BusinessUser {
    public HouseholdUser() {
    }

    public HouseholdUser(String firstName, String lastName, String email, String password, String contactNo,
                         String address, Role role, String profileURL, LocalDateTime createdTimeStamp) {
        super(firstName, lastName, email, password, contactNo, address, role, profileURL, createdTimeStamp);
    }
}
