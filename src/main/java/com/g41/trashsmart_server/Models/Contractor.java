package com.g41.trashsmart_server.Models;

import com.g41.trashsmart_server.Enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table
public class Contractor extends SystemUser implements UserDetails {
    public Contractor() {
    }

    public Contractor(String firstName, String lastName, String email, String password, Role role,
                      LocalDateTime createdTimeStamp, LocalDate dob, String nic) {
        super(firstName, lastName, email, password, role, createdTimeStamp, dob, nic);
    }

    public Contractor(String firstName, String lastName, String email, String password, String nic, LocalDate dob) {
        super(firstName, lastName, email, password, Role.CONTRACTOR, LocalDateTime.now(), dob, nic);
    }


    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(super.getRole().name()));
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !super.isDeleted();
    }
}