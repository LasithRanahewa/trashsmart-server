package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.DTO.BidDTO;
import com.g41.trashsmart_server.DTO.BidDTOMapper;
import com.g41.trashsmart_server.Enums.AuctionStatus;
import com.g41.trashsmart_server.Models.Auction;
import com.g41.trashsmart_server.Models.Bid;
import com.g41.trashsmart_server.Models.RecyclingPlant;
import com.g41.trashsmart_server.Repositories.AuctionRepository;
import com.g41.trashsmart_server.Repositories.BidRepository;
import com.g41.trashsmart_server.Repositories.RecyclingPlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BidService {
    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private BidDTOMapper bidDTOMapper;

    @Autowired
    private RecyclingPlantRepository recyclingPlantRepository;


    public BidDTO placeBid(Long auctionId, Long recyclingPlantId, Double bidAmount) {
        Optional<Auction> auctionOptional = auctionRepository.findById(auctionId);
        Optional<RecyclingPlant> recyclingPlantOptional = recyclingPlantRepository.findById(recyclingPlantId);

        if (auctionOptional.isEmpty()) {
            throw new IllegalStateException("Auction with ID " + auctionId + " does not exists.");
        }
        if (recyclingPlantOptional.isEmpty()) {
            throw new IllegalStateException("Recycling Plant with ID " + recyclingPlantId + " does not exists.");
        }

        Auction auction = auctionOptional.get();
        RecyclingPlant recyclingPlant = recyclingPlantOptional.get();

        // Check if the auction is live
        if (auction.getStatus() != AuctionStatus.LIVE) {
            throw new RuntimeException("Auction with ID " + auctionId + " is not live.");
        }

        // Check if the recycling plant is registered for the auction
        if (!auction.getRegisteredPlants().stream().anyMatch(plant -> plant.getId().equals(recyclingPlantId))) {
            throw new IllegalStateException("Recycling Plant is not registered for this auction.");
        }

        // Validate the bid amount
        if (bidAmount <= auction.getCurrentBid() || bidAmount <= auction.getMinimumBidAmount()) {
            throw new IllegalArgumentException("Bid amount must be higher than the current bid of " + auction.getCurrentBid() + ".");
        }

        Bid bid = new Bid();
        bid.setAuction(auction);
        bid.setRecyclingPlant(recyclingPlant);
        bid.setBidAmount(bidAmount);
        bid.setBidTime(LocalDateTime.now());

        auction.setCurrentBid(bidAmount);
        auction.setWinningPlantId(recyclingPlantId);
        auctionRepository.save(auction);
        bidRepository.save(bid);

        return bidDTOMapper.apply(bid);
    }

    public List<BidDTO> getBidHistory(Long auctionId) {
        Optional<Auction> auctionOptional = auctionRepository.findById(auctionId);

        if (auctionOptional.isEmpty()) {
            throw new IllegalStateException("Auction with ID " + auctionId + " does not exists.");
        }

        Auction auction = auctionOptional.get();
        List<Bid> bids = bidRepository.findAllByAuctionOrderByBidAmountDesc(auction);
        return bids.stream()
                .map(bidDTOMapper)
                .collect(Collectors.toList());
    }

    public BidDTO getWinningBid(Long auctionId) {
        Optional<Auction> auctionOptional = auctionRepository.findById(auctionId);

        if (auctionOptional.isEmpty()) {
            throw new IllegalStateException("Auction with ID " + auctionId + " does not exist.");
        }

        Auction auction = auctionOptional.get();

        if (auction.getStatus() != AuctionStatus.PAST) {
            throw new IllegalStateException("Auction with ID " + auctionId + " has not ended yet.");
        }

        Bid winningBid = bidRepository.findTopByAuctionOrderByBidAmountDesc(auction).orElseThrow(
                () -> new IllegalStateException("No bids found for the auction with ID " + auctionId)
        );

        return bidDTOMapper.apply(winningBid);
    }
}
