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
        this.setRole(Role.RECYCLING_PLANT);
    }

    public RecyclingPlant(String firstName, String lastName, String email, String password, String contactNo,
                          String address, String profileURL, String BRN) {
        super(firstName, lastName, email, password, contactNo, address, profileURL, LocalDateTime.now());
        this.BRN = BRN;
        this.setRole(Role.RECYCLING_PLANT);
    }

    // Getters and Setters
    public String getBRN() {
        return BRN;
    }

    public void setBRN(String BRN) {
        this.BRN = BRN;
    }
}
