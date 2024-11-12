package com.g41.trashsmart_server.DTO;

import com.g41.trashsmart_server.Models.Auction;
import com.g41.trashsmart_server.Models.RecyclingPlant;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AuctionDTOMapper implements Function<Auction, AuctionDTO> {
    @Override
    public AuctionDTO apply(Auction auction) {
        return new AuctionDTO(
                auction.getId(),
                auction.getAuctionWasteType(),
                auction.getWeight(),
                auction.getMinimumBidAmount(),
                auction.getCurrentBid(),
                auction.getStatus(),
                auction.getStartDate(),
                auction.getEndDate(),
                auction.getRegisteredPlants().stream()
                        .map(RecyclingPlant::getId)
                        .collect(Collectors.toSet())

        );
    }
}
