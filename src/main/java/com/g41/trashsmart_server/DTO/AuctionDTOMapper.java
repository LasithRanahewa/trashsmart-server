package com.g41.trashsmart_server.DTO;

import com.g41.trashsmart_server.Models.Auction;
import org.springframework.stereotype.Service;

import java.util.function.Function;

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
                auction.getStartDate(),
                auction.getEndDate()
        );
    }
}
