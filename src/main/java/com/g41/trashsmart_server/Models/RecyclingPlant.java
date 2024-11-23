package com.g41.trashsmart_server.Models;

import com.g41.trashsmart_server.Enums.Role;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
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

    @OneToMany(mappedBy = "recyclingPlant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bid> bids;


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

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }
}

