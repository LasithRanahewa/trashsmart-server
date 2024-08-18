package com.g41.trashsmart_server.Models;

import com.g41.trashsmart_server.Enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table
public class RecyclingPlant extends BusinessUser {
    private String BRN;

    public RecyclingPlant() {
    }

    public RecyclingPlant(String firstName, String lastName, String email, String password, String contactNo,
                          String address, Role role, String profileURL, LocalDateTime createdTimeStamp, String BRN) {
        super(firstName, lastName, email, password, contactNo, address, role, profileURL, createdTimeStamp);
        this.BRN = BRN;
    }

    // Getters and Setters
    public String getBRN() {
        return BRN;
    }

    public void setBRN(String BRN) {
        this.BRN = BRN;
    }
}
