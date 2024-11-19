package com.g41.trashsmart_server.Models;

import com.g41.trashsmart_server.Controllers.AuctionController;
import com.g41.trashsmart_server.Enums.AuctionStatus;
import com.g41.trashsmart_server.Enums.AuctionWasteType;
import jakarta.persistence.*;
import org.springframework.data.repository.query.Param;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Table(name = "auction")
@Entity
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AuctionWasteType auctionWasteType;
    private Double weight;
    private Double minimumBidAmount;
    private Double currentBid;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Enumerated(EnumType.STRING)
    private AuctionStatus status;

    private Long winningPlantId;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL)
    private List<Bid> bids;

    private boolean isDeleted;

    private boolean isClosed;

    @ManyToMany
    @JoinTable(
            name = "auction_recycling_plant",
            joinColumns = @JoinColumn(name = "auction_id"),
            inverseJoinColumns = @JoinColumn(name = "recycling_plant_id")
    )
    private Set<RecyclingPlant> registeredPlants;


    public Auction() {}

    public Auction(AuctionWasteType auctionWasteType, Double weight, Double minimumBidAmount, LocalDateTime startDate, LocalDateTime endDate, Set<RecyclingPlant> registeredPlants) {
        this.auctionWasteType = auctionWasteType;
        this.weight = weight;
        this.minimumBidAmount = minimumBidAmount;
        this.currentBid = minimumBidAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = null;
        this.isDeleted = false;
        this.isClosed = false;
        this.registeredPlants = registeredPlants;
    }

    public Auction(AuctionWasteType auctionWasteType, Double weight, Double minimumBidAmount, LocalDateTime startDate, LocalDateTime endDate) {
        this.auctionWasteType = auctionWasteType;
        this.weight = weight;
        this.minimumBidAmount = minimumBidAmount;
        this.currentBid = minimumBidAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = null;
        this.isDeleted = false;
        this.isClosed = false;
    }

    // Automatically update the status when the entity is loaded
    @PostLoad
    public void updateStatus() {
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(startDate) && now.isBefore(endDate)) {
            this.status = AuctionStatus.LIVE;
        } else if (now.isAfter(endDate)) {
            this.status = AuctionStatus.PAST;
        } else if (now.isBefore(startDate)) {
            this.status = AuctionStatus.UPCOMING;
        }
    }

    // Updates the current bid if a higher bid is received
    public void updateCurrentBid(double newBid, Long recyclingPlantId) {
        if (this.status == AuctionStatus.LIVE && newBid > currentBid) {
            this.currentBid = newBid;
            this.winningPlantId = recyclingPlantId;
        }
    }


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

    public boolean isDeleted() { return isDeleted; }

    public void setDeleted(Boolean deleted) { isDeleted = deleted; }

    public AuctionStatus getStatus() { return status ;}
    public void setStatus(@Param("status") AuctionStatus status) { this.status = status; }

    public Long getWinningPlantId() { return winningPlantId; }

    public void setWinningPlantId(Long winningPlantId) { this.winningPlantId = winningPlantId; }

    public Set<RecyclingPlant> getRegisteredPlants() { return registeredPlants; }

    public void setRegisteredPlants(Set<RecyclingPlant> registeredPlants) { this.registeredPlants = registeredPlants; }

    public void addRecyclingPlant(RecyclingPlant recyclingPlant) { this.registeredPlants.add(recyclingPlant); }

    public Boolean isClosed() { return isClosed; }
    public void setClosed(Boolean closed ) { this.isClosed = closed; }

}
