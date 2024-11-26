package com.g41.trashsmart_server.Configuration;

import com.g41.trashsmart_server.Enums.AuctionStatus;
import com.g41.trashsmart_server.Models.Auction;
import com.g41.trashsmart_server.Models.Bid;
import com.g41.trashsmart_server.Models.RecyclingPlant;
import com.g41.trashsmart_server.Repositories.AuctionRepository;
import com.g41.trashsmart_server.Repositories.BidRepository;
import com.g41.trashsmart_server.Repositories.RecyclingPlantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class BidConfig {
    @Bean
    CommandLineRunner bidCommandLineRunner(
            BidRepository bidRepository,
            AuctionRepository auctionRepository,
            RecyclingPlantRepository recyclingPlantRepository) {
        return args -> {
            List<Auction> liveAuctions = auctionRepository.findLiveAuctions();
            List<RecyclingPlant> recyclingPlants = recyclingPlantRepository.findAll();

            if (liveAuctions.isEmpty() || recyclingPlants.isEmpty()) {
                System.out.println("No live auctions or recycling plants found. Skipping bid initialization.");
                return;
            }

            for (Auction auction : liveAuctions) {
                RecyclingPlant recyclingPlant = recyclingPlants.get(0);

                Bid bid1 = new Bid();
                bid1.setBidAmount(auction.getMinimumBidAmount() + 50.0);
                bid1.setBidTime(LocalDateTime.now());
                bid1.setAuction(auction);
                bid1.setRecyclingPlant(recyclingPlant);

                bidRepository.save(bid1);

                System.out.println("Initialized bid for auction ID " + auction.getId() + " by recycling plant ID " + recyclingPlant.getId());
            }
        };
    }
}
