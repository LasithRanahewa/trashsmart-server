package com.g41.trashsmart_server.Models;

import com.g41.trashsmart_server.Enums.Role;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "recycling-plant")
public class RecyclingPlant extends BusinessUser implements UserDetails {

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

    @ManyToMany(mappedBy = "registeredPlants")
    private Set<Auction> auctions;

    @OneToMany(mappedBy = "recyclingPlant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bid> bids;


    // Getters and Setters
    public String getBRN() {
        return BRN;
    }

    public void setBRN(String BRN) {
        this.BRN = BRN;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
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
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    public boolean isEnabled() {
        return !super.isDeleted();
    }

    public Set<Auction> getAuctions() { return auctions; }

    public void setAuctions(Set<Auction> auctions) { this.auctions = auctions; }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }
}

