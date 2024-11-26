package com.g41.trashsmart_server.Models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double bidAmount;
    private LocalDateTime bidTime;

    @ManyToOne
    @JoinColumn(name = "recycle_plant_id")
    private RecyclingPlant recyclingPlant;

    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;


    public Bid() {}

    public Bid(Double bidAmount, LocalDateTime bidTime, RecyclingPlant recyclingPlant, Auction auction) {
        this.bidAmount = bidAmount;
        this.bidTime = bidTime;
        this.recyclingPlant = recyclingPlant;
        this.auction = auction;
    }

    public Long getId() {
        return id;
    }

    public Double getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(Double bidAmount) {
        this.bidAmount = bidAmount;
    }

    public LocalDateTime getBidTime() {
        return bidTime;
    }

    public void setBidTime(LocalDateTime bidTime) {
        this.bidTime = bidTime;
    }

    public RecyclingPlant getRecyclingPlant() {
        return recyclingPlant;
    }

    public void setRecyclingPlant(RecyclingPlant recyclingPlant) {
        this.recyclingPlant = recyclingPlant;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) { this.auction = auction; }

}
