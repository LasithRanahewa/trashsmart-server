package com.g41.trashsmart_server.Models;

import com.g41.trashsmart_server.Enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table
public class Contractor extends SystemUser {
    public Contractor() {
        this.setRole(Role.CONTRACTOR);
    }

    public Contractor(String firstName, String lastName, String email, String password, LocalDate dob, String nic) {
        super(firstName, lastName, email, password, LocalDateTime.now(), dob, nic);
        this.setRole(Role.CONTRACTOR);
    }
}
