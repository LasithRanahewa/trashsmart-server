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
    }

    public Contractor(String firstName, String lastName, String email, String password, Role role,
                      LocalDateTime createdTimeStamp, LocalDate dob, String nic) {
        super(firstName, lastName, email, password, role, createdTimeStamp, dob, nic);
    }
}
