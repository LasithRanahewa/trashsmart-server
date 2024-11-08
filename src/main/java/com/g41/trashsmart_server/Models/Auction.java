package com.g41.trashsmart_server.Models;

import com.g41.trashsmart_server.Enums.AuctionWasteType;
import jakarta.persistence.*;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;

@Table
@Entity
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private AuctionWasteType auctionWasteType;
    private Double weight;
    private Double minimumBidAmount;
    private Double currentBid;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL)
    private List<Bid> bids;

    private boolean isClosed;
    private boolean isDeleted;
    private String status;

    public Auction() {}

    public Auction(AuctionWasteType auctionWasteType, Double weight, Double minimumBidAmount, LocalDateTime startDate, LocalDateTime endDate) {
        this.auctionWasteType = auctionWasteType;
        this.weight = weight;
        this.minimumBidAmount = minimumBidAmount;
        this.currentBid = minimumBidAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isClosed = true;
        this.isDeleted = false;
    }

    // Automatically set the auction as open if current time is within start and end date
    @PrePersist
    public void onPrePersist() {
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(startDate) && now.isBefore(endDate)) {
            this.isClosed = false;
        } else {
            this.isClosed = true;
        }
        this.currentBid = this.minimumBidAmount;
    }

    // Updates the current bid if a higher bid is received
    public void updateCurrentBid(double newBid) {
        if (newBid > currentBid) {
            this.currentBid = newBid;
        }
    }

    // Automatically closes the auction if the current time exceeds the end date


    // getters and setters

    public Long getId() {
        return id;
    }

    public AuctionWasteType getAuctionWasteType() {
        return auctionWasteType;
    }

    public void setAuctionWasteType(AuctionWasteType auctionWasteType) {
        this.auctionWasteType = auctionWasteType;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getMinimumBidAmount() {
        return minimumBidAmount;
    }

    public void setMinimumBidAmount(Double minimumBidAmount) {
        this.minimumBidAmount = minimumBidAmount;
    }

    public Double getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(Double currentBid) {
        this.currentBid = currentBid;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    public boolean isDeleted() { return isDeleted; }

    public void setDeleted(Boolean deleted) { isDeleted = deleted; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
