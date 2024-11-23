package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.DTO.AuctionDTO;
import com.g41.trashsmart_server.DTO.AuctionDTOMapper;
import com.g41.trashsmart_server.DTO.BidDTO;
import com.g41.trashsmart_server.Models.Auction;
import com.g41.trashsmart_server.Models.Bid;
import com.g41.trashsmart_server.Models.RecyclingPlant;
import com.g41.trashsmart_server.Repositories.AuctionRepository;
import com.g41.trashsmart_server.Repositories.BidRepository;
import com.g41.trashsmart_server.Repositories.RecyclingPlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuctionService {
    private final AuctionRepository auctionRepository;
    private final AuctionDTOMapper auctionDTOMapper;
    private final RecyclingPlantRepository recyclingPlantRepository;
    private final BidRepository bidRepository;

    @Autowired
    public AuctionService(AuctionRepository auctionRepository, AuctionDTOMapper auctionDTOMapper, RecyclingPlantRepository recyclingPlantRepository, BidRepository bidRepository) {
        this.auctionRepository = auctionRepository;
        this.auctionDTOMapper = auctionDTOMapper;
        this.recyclingPlantRepository = recyclingPlantRepository;
        this.bidRepository = bidRepository;
    }

    public Auction createAuction(Auction auction) {
        return auctionRepository.save(auction);
    }

    public List<AuctionDTO> getLiveAuctions() {
        List<Auction> liveAuctions = auctionRepository.findLiveAuctions();
        return liveAuctions.stream()
                .map(auctionDTOMapper)
                .collect(Collectors.toList());
    }

    public List<AuctionDTO> getPastAuctions() {
        List<Auction> pastAuctions = auctionRepository.findPastAuctions();
        return pastAuctions.stream()
                .map(auctionDTOMapper)
                .collect(Collectors.toList());
    }

    public List<AuctionDTO> getCanceledAuctions() {
        List<Auction> canceledAuctions = auctionRepository.findCanceledAuctions();
        return canceledAuctions.stream()
                .map(auctionDTOMapper)
                .collect(Collectors.toList());
    }

    public List<AuctionDTO> getUpcomingAuctions() {
        List<Auction> upcomingAuctions = auctionRepository.findUpcomingAuctions();
        return upcomingAuctions.stream()
                .map(auctionDTOMapper)
                .collect(Collectors.toList());
    }

    public AuctionDTO getAuctionById(Long auctionId, String status) {
        Optional<Auction> auctionOptional = auctionRepository.findById(auctionId);
        if (auctionOptional.isEmpty()) {
            throw new IllegalStateException("Auction with id " + auctionId + "does not exist.");
        }

        Auction auction = auctionOptional.get();
        LocalDateTime now = LocalDateTime.now();

        switch (status) {
            case "Live":
                if (auction.getClosed() || now.isBefore(auction.getStartDate()) || now.isAfter(auction.getEndDate())) {
                    throw new RuntimeException("Auction with ID " + auctionId + " is not a live auction.");
                }
                break;
            case "Past":
                if (!auction.getClosed() || now.isBefore(auction.getEndDate())) {
                    throw new RuntimeException("Auction with ID " + auctionId + " is not a past auction.");
                }
                break;
            case "Canceled":
                if (!auction.getClosed() || now.isAfter(auction.getEndDate())) {
                    throw new RuntimeException("Auction with ID " + auctionId + " is not a canceled auction.");
                }
                break;
            case "Upcoming":
                if (!auction.getClosed() || now.isBefore(auction.getStartDate())) {
                    throw new RuntimeException("Auction with ID " + auctionId + " is not an upcoming auction.");
                }
                break;
        }

        return auctionDTOMapper.apply(auction);
    }

    public void deletePastAuction(Long auctionId) {
        Optional<Auction> auctionOptional = auctionRepository.findById(auctionId);
        if (auctionOptional.isEmpty()) {
            throw new IllegalStateException("Auction with ID" + auctionId + "does not exist.");
        }
        Auction auctionToDelete = auctionOptional.get();

        LocalDateTime now = LocalDateTime.now();
        if (!auctionToDelete.getClosed() || now.isBefore(auctionToDelete.getEndDate())) {
            throw new IllegalStateException("Auction with ID " + auctionId + " is not a past auction.");
        }

        auctionToDelete.setDeleted(true);
        auctionRepository.save(auctionToDelete);
    }

    public void deleteUpcomingAuction(Long auctionId) {
        Optional<Auction> auctionOptional = auctionRepository.findById(auctionId);
        if (auctionOptional.isEmpty()) {
            throw new IllegalStateException("Auction with ID" + auctionId + "does not exist.");
        }
        Auction auctionToDelete = auctionOptional.get();

        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(auctionToDelete.getStartDate())) {
            throw new IllegalStateException("Auction with ID " + auctionId + " has already started and cannot be deleted.");
        }

        auctionRepository.delete(auctionToDelete);
    }


    public String registerForAuction(Long auctionId, Long recyclingPlantId) {
        Optional<Auction> auctionOptional = auctionRepository.findById(auctionId);
        Optional<RecyclingPlant> recyclingPlantOptional = recyclingPlantRepository.findById(recyclingPlantId);

        if (auctionOptional.isEmpty()) {
            throw new IllegalStateException("Auction with ID " + auctionId + " does not exist.");
        }
        if (recyclingPlantOptional.isEmpty()) {
            throw new IllegalStateException("Recycling Plant with ID " + recyclingPlantId + " does not exist.");
        }

        Auction auction = auctionOptional.get();
        RecyclingPlant recyclingPlant = recyclingPlantOptional.get();

        // Validate if the auction is upcoming
        if (auction.getStartDate().isBefore(LocalDateTime.now()) || auction.getClosed()) {
            throw new IllegalStateException("Auction with ID " + auctionId + " is not upcoming.");
        }

        // Check if the recycling plant is already registered
        if (bidRepository.existsByAuctionAndRecyclingPlant(auction, recyclingPlant)) {
            throw new IllegalStateException("Recycling Plant is already registered for this auction.");
        }

        Bid registrationBid = new Bid();
        registrationBid.setAuction(auction);
        registrationBid.setRecyclingPlant(recyclingPlant);
        registrationBid.setBidAmount(auction.getMinimumBidAmount());
        bidRepository.save(registrationBid);

        return "Recycling Plant successfully registered to the upcoming auction.";
    }
}
