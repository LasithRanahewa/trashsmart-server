package com.g41.trashsmart_server.Models;

import com.g41.trashsmart_server.Enums.Role;
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
    public SystemUser(String firstName, String lastName, String email, String password, LocalDateTime createdTimeStamp,
                      LocalDate dob, String nic) {
        super(firstName, lastName, email, password, createdTimeStamp);
        this.dob = dob;
        this.nic = nic;
    }

    // Driver/Cleaner ContactNo Registration no Email
    public SystemUser(String firstName, String lastName, String password, String contactNo, String address,
                      String profileURL, LocalDateTime createdTimeStamp, LocalDate dob, String nic) {
        super(firstName, lastName, password, contactNo, address, profileURL, createdTimeStamp);
        this.dob = dob;
        this.nic = nic;
    }

    // Driver/Cleaner Email Registration
    public SystemUser(String firstName, String lastName, String password, String contactNo, String address,
                      String email, String profileURL, LocalDateTime createdTimeStamp, LocalDate dob, String nic) {
        super(firstName, lastName, password, contactNo, address, email, createdTimeStamp, profileURL);
        this.dob = dob;
        this.nic = nic;
    }

    public SystemUser(String firstName, String lastName, String email, String password, Role role, LocalDateTime now, LocalDate dob, String nic) {
        super(firstName, lastName, email, password, role, now);
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
