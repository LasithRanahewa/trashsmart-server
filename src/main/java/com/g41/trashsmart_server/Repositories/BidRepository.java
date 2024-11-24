package com.g41.trashsmart_server.Repositories;

import com.g41.trashsmart_server.Models.Auction;
import com.g41.trashsmart_server.Models.Bid;
import com.g41.trashsmart_server.Models.RecyclingPlant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {

    boolean existsByAuctionAndRecyclingPlant(Auction auction, RecyclingPlant recyclingPlant);

    List<Bid> findAllByAuctionOrderByBidAmountDesc(Auction auction);

    Optional<Bid> findTopByAuctionOrderByBidAmountDesc(Auction auction);
}
