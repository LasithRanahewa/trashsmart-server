package com.g41.trashsmart_server.Models;

import com.g41.trashsmart_server.Enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table
public class HouseholdUser extends BusinessUser implements UserDetails {
    @ManyToOne
    @JoinColumn(name = "suburb_id")
    private Suburb suburb;

    public HouseholdUser() {
    }

    public HouseholdUser(String firstName, String lastName, String email, String password, String contactNo,
                         String address, String profileURL) {
        super(firstName, lastName, email, password, contactNo, address, Role.HOUSEHOLD_USER, profileURL, LocalDateTime.now());
    }

    public Suburb getSuburb() {
        return suburb;
    }

    public void setSuburb(Suburb suburb) {
        this.suburb = suburb;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return the authorities granted to the user
        return null; // Replace with actual authorities
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