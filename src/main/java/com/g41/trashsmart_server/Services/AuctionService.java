package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.DTO.AuctionDTO;
import com.g41.trashsmart_server.DTO.AuctionDTOMapper;
import com.g41.trashsmart_server.Enums.AuctionStatus;
import com.g41.trashsmart_server.Models.Auction;
import com.g41.trashsmart_server.Models.Bid;
import com.g41.trashsmart_server.Models.RecyclingPlant;
import com.g41.trashsmart_server.Repositories.AuctionRepository;
import com.g41.trashsmart_server.Repositories.BidRepository;
import com.g41.trashsmart_server.Repositories.RecyclingPlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void createAuction(Auction auction) {
        auctionRepository.save(auction);
    }


    public List<AuctionDTO> getAllAuctions() {
        List<Auction> auctions = auctionRepository.findAllAuctions();
        return auctions.stream()
                .map(auctionDTOMapper)
                .collect(Collectors.toList());
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

    public List<AuctionDTO> getDeletedAuctions() {
        List<Auction> deletedAuctions = auctionRepository.findDeletedAuctions();
        return deletedAuctions.stream()
                .map(auctionDTOMapper)
                .collect(Collectors.toList());
    }

    public AuctionDTO getSpecificAuction(Long auctionId) {
        Optional<Auction> auctionOptional = auctionRepository.findById(auctionId);
        if (auctionOptional.isEmpty()) {
            throw new IllegalStateException("Auction with id " + auctionId + "does not exist.");
        }

        return auctionDTOMapper.apply(auctionOptional.get());
    }

    public AuctionDTO getAuctionById(Long auctionId, String status) {
        Optional<Auction> auctionOptional = auctionRepository.findById(auctionId);
        if (auctionOptional.isEmpty()) {
            throw new IllegalStateException("Auction with id " + auctionId + "does not exist.");
        }

        Auction auction = auctionOptional.get();

        switch (status) {
            case "Live":
                if (auction.getStatus() != AuctionStatus.LIVE) {
                    throw new RuntimeException("Auction with ID " + auctionId + " is not a live auction.");
                }
                break;
            case "Past":
                if (auction.getStatus() != AuctionStatus.PAST) {
                    throw new RuntimeException("Auction with ID " + auctionId + " is not a past auction.");
                }
                break;
            case "Canceled":
                if (auction.getStatus() != AuctionStatus.CANCELLED) {
                    throw new RuntimeException("Auction with ID " + auctionId + " is not a canceled auction.");
                }
                break;
            case "Upcoming":
                if (auction.getStatus() != AuctionStatus.UPCOMING) {
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

        if (auctionToDelete.getStatus() != AuctionStatus.PAST) {
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

        if (auctionToDelete.getStatus() != AuctionStatus.UPCOMING) {
            throw new IllegalStateException("Auction with ID " + auctionId + " has already started and cannot be deleted.");
        }

        auctionRepository.delete(auctionToDelete);
    }


    public void cancelLiveAuction(Long auctionId) {
        Optional<Auction> auctionOptional = auctionRepository.findById(auctionId);
        if (auctionOptional.isEmpty()) {
            throw new IllegalStateException("Auction with ID " + auctionId + " does not exist.");
        }

        Auction auction = auctionOptional.get();

        if (auction.getStatus() != AuctionStatus.LIVE) {
            throw new IllegalStateException("Auction with ID " + auctionId + " is not live and cannot be canceled.");
        }

        auction.setStatus(AuctionStatus.CANCELLED);
        auction.setClosed(true);
        auctionRepository.save(auction);
        System.out.println("Auction ID " + auction.getId() + " has been canceled.");
    }


    @Transactional
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
        if (auction.getStatus() != AuctionStatus.UPCOMING && auction.getStatus() != AuctionStatus.LIVE) {
            throw new IllegalStateException("Auction with ID " + auctionId + " is neither upcoming nor live.");
        }

        // Check if the recycling plant is already registered
        if (bidRepository.existsByAuctionAndRecyclingPlant(auction, recyclingPlant)) {
            throw new IllegalStateException("Recycling Plant is already registered for this auction.");
        }

        auction.getRegisteredPlants().add(recyclingPlant);
        auctionRepository.save(auction);

        Bid registrationBid = new Bid();
        registrationBid.setAuction(auction);
        registrationBid.setRecyclingPlant(recyclingPlant);
        registrationBid.setBidAmount(null);
        bidRepository.save(registrationBid);

        return "Recycling Plant successfully registered to the upcoming auction.";
    }


    //Scheduled task to automatically transition auctions from UPCOMING to LIVE and LIVE to PAST
    @Scheduled(fixedRate = 60000)
    public void updateAuctionStatus() {
        LocalDateTime now = LocalDateTime.now();

        try {
            //UPCOMING to LIVE
            List<Auction> upcomingAuctions = auctionRepository.findByStatus(AuctionStatus.UPCOMING);
            for (Auction auction : upcomingAuctions) {
                if (now.isAfter(auction.getStartDate()) || now.isEqual(auction.getStartDate())) {
                    auction.setStatus(AuctionStatus.LIVE);
                    auctionRepository.save(auction);
                    System.out.println("Auction ID " + auction.getId() + " is now live.");
                }
            }

            //LIVE to PAST
            List<Auction> liveAuctions = auctionRepository.findByStatus(AuctionStatus.LIVE);
            for (Auction auction : liveAuctions) {
                if (now.isAfter(auction.getEndDate())) {
                    auction.setStatus(AuctionStatus.PAST);
                    auctionRepository.save(auction);
                    System.out.println("Auction ID " + auction.getId() + " has ended and is now past.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error updating auction statuses: " + e.getMessage());
        }

    }
}
