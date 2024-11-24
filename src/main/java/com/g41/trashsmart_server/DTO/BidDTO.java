package com.g41.trashsmart_server.DTO;

import com.g41.trashsmart_server.Models.Bid;
import com.g41.trashsmart_server.Models.RecyclingPlant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidDTO {
    private Long id;
    private Double bidAmount;
    private LocalDateTime bidTime;
    private Long auctionId;
    private RecyclingPlantDTO recyclingPlant;

//    public BidDTO(Bid bid) {
//        this.id = bid.getId();
//        this.bidAmount = bid.getBidAmount();
//        this.bidTime = bid.getBidTime();
//        this.auctionId = bid.getAuction() != null ? bid.getAuction().getId() : null;
//        this.recyclingPlant = bid.getRecyclingPlant();
//    }

}
