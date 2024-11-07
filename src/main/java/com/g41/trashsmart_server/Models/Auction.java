package com.g41.trashsmart_server.Models;

import jakarta.persistence.*;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String auctionWasteType;
    private Double weight;
    private Double basePrice;
    private Double currentBidPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    private Contractor contractor;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL)
    private List<Bid> bids;

    private boolean isClosed;

    public Auction() {}

    public Auction(String auctionWasteType, Double weight, Double basePrice, Double currentBidPrice, LocalDateTime startDate, LocalDateTime endDate, Contractor contractor, List<Bid> bids, Boolean isClosed) {
        this.auctionWasteType = auctionWasteType;
        this.weight = weight;
        this.basePrice = basePrice;
        this.currentBidPrice = basePrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contractor = contractor;
        this.bids = bids;
        this.isClosed = false;
    }

    // Automatically set the auction as open if current time is within start and end date
    @PrePersist
    public void onPrePersist() {
        LocalDateTime now = LocalDateTime.now();
        if(now.isAfter(startDate) && now.isBefore(endDate)) {
            this.isClosed = false;
        }
    }

    // Updates the current bid if a higher bid is received
    public void updateCurrentBid(double newBid) {
        if (newBid > currentBidPrice) {
            this.currentBidPrice = newBid;
        }
    }

    // Automatically closes the auction if the current time exceeds the end date
    @PostLoad
    public void checkAuctionStatus() {
        if (LocalDateTime.now().isAfter(endDate)) {
            this.isClosed = true;
        }
    }

    // getters and setters

    public Long getId() {
        return id;
    }

    public String getAuctionWasteType() {
        return auctionWasteType;
    }

    public void setAuctionWasteType(String auctionWasteType) {
        this.auctionWasteType = auctionWasteType;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Double getCurrentBidPrice() {
        return currentBidPrice;
    }

    public void setCurrentBidPrice(Double currentBidPrice) {
        this.currentBidPrice = currentBidPrice;
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

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
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
}
