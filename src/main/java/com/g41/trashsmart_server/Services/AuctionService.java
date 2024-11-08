package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.DTO.AuctionDTO;
import com.g41.trashsmart_server.DTO.AuctionDTOMapper;
import com.g41.trashsmart_server.Models.Auction;
import com.g41.trashsmart_server.Repositories.AuctionRepository;
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

    @Autowired
    public AuctionService(AuctionRepository auctionRepository, AuctionDTOMapper auctionDTOMapper) {
        this.auctionRepository = auctionRepository;
        this.auctionDTOMapper = auctionDTOMapper;
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
}
