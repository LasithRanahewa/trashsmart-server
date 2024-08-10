package com.example.trashsmart.Models;

import com.example.trashsmart.Enums.Role;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class SystemUser extends User {
    private LocalDate dob;
    private String nic;

    public SystemUser() {
    }

    // Contactor registration
    public SystemUser(String firstName, String lastName, String email, String password, Role role,
                      LocalDateTime createdTimeStamp, LocalDate dob, String nic) {
        super(firstName, lastName, email, password, role, createdTimeStamp);
        this.dob = dob;
        this.nic = nic;
    }

    // Driver/Cleaner ContactNo Registration no Email
    public SystemUser(String firstName, String lastName, String password, String contactNo, String address, Role role,
                      String profileURL, LocalDateTime createdTimeStamp, LocalDate dob, String nic) {
        super(firstName, lastName, password, contactNo, address, role, profileURL, createdTimeStamp);
        this.dob = dob;
        this.nic = nic;
    }

    // Driver/Cleaner Email Registration
    public SystemUser(String firstName, String lastName, String password, String contactNo, String address, Role role,
                      String email, String profileURL, LocalDateTime createdTimeStamp, LocalDate dob, String nic) {
        super(firstName, lastName, password, contactNo, address, role, email, profileURL, createdTimeStamp);
        this.dob = dob;
        this.nic = nic;
    }

    // Getters and Setters
    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }
}
