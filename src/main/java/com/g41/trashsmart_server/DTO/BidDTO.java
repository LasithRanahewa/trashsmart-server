package com.g41.trashsmart_server.DTO;

import com.g41.trashsmart_server.Models.Bid;
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
    private Long recyclingPlantId;

    public BidDTO(Bid bid) {
        this.id = bid.getId();
        this.bidAmount = bid.getBidAmount();
        this.bidTime = bid.getBidTime();
        this.auctionId = bid.getAuction() != null ? bid.getAuction().getId() : null;
        this.recyclingPlantId = bid.getRecyclingPlant() != null ? bid.getRecyclingPlant().getId() : null;
    }

}
