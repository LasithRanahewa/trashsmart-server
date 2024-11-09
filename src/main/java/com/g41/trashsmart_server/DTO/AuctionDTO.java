package com.g41.trashsmart_server.DTO;

import com.g41.trashsmart_server.Enums.AuctionWasteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuctionDTO {
    private Long id;
    private AuctionWasteType auctionWasteType;
    private Double weight;
    private Double minimumBidAmount;
    private Double currentBid;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
