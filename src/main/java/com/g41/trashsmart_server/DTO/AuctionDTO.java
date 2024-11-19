package com.g41.trashsmart_server.DTO;

import com.g41.trashsmart_server.Enums.AuctionStatus;
import com.g41.trashsmart_server.Enums.AuctionWasteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuctionDTO {
    private Long id;
    private AuctionWasteType auctionWasteType;
    private Double weight;
    private Double minimumBidAmount;
    private Double currentBid;
    private AuctionStatus status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long winningPlantId;
    private Set<Long> registeredPlantIds;
}
