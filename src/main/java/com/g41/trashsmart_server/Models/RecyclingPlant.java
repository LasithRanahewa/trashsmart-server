package com.g41.trashsmart_server.Models;

import com.g41.trashsmart_server.Enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "recycling-plant")
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

    @ManyToMany(mappedBy = "registeredPlants")
    private Set<Auction> auctions;

    // Getters and Setters
    public String getBRN() {
        return BRN;
    }

    public void setBRN(String BRN) {
        this.BRN = BRN;
    }

    public boolean isEnabled() {
        return !super.isDeleted();
    }

    public Set<Auction> getAuctions() { return auctions; }

    public void setAuctions(Set<Auction> auctions) { this.auctions = auctions; }
}
