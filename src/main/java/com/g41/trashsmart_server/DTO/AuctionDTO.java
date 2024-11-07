package com.g41.trashsmart_server.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuctionDTO {
    private Long id;
    private String auctionWasteType;
    private Double weight;
    private Double basePrice;
    private Double currentBidPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isClosed;
    private Long contractorId;
}
