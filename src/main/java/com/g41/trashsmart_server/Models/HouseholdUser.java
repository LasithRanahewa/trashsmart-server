package com.g41.trashsmart_server.Models;

import com.g41.trashsmart_server.Enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table
public class HouseholdUser extends BusinessUser {
    @ManyToOne
    @JoinColumn(name = "suburb_id")
    private Suburb suburb;

    public HouseholdUser() {
    }

    public HouseholdUser(String firstName, String lastName, String email, String password, String contactNo,
                         String address, Role role, String profileURL, LocalDateTime createdTimeStamp) {
        super(firstName, lastName, email, password, contactNo, address, role, profileURL, createdTimeStamp);
    }

    public Suburb getSuburb() {
        return suburb;
    }

    public void setSuburb(Suburb suburb) {
        this.suburb = suburb;
    }
}
