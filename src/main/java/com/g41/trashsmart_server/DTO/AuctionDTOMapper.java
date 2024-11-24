package com.g41.trashsmart_server.DTO;

import com.g41.trashsmart_server.Models.Auction;
import com.g41.trashsmart_server.Models.RecyclingPlant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AuctionDTOMapper implements Function<Auction, AuctionDTO> {

    private final BidDTOMapper bidDTOMapper;
    private final RecyclingPlantDTOMapper recyclingPlantDTOMapper;
    @Autowired
    public AuctionDTOMapper(BidDTOMapper bidDTOMapper, RecyclingPlantDTOMapper recyclingPlantDTOMapper) {
        this.bidDTOMapper = bidDTOMapper;
        this.recyclingPlantDTOMapper = recyclingPlantDTOMapper;
    }

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
                auction.getWinningPlantId(),
                auction.getRegisteredPlants().stream()
                        .map(recyclingPlantDTOMapper::apply)
                        .collect(Collectors.toSet()),
                auction.getBids().stream()
                        .map(bidDTOMapper::apply)
                        .collect(Collectors.toList())

        );
    }
}
