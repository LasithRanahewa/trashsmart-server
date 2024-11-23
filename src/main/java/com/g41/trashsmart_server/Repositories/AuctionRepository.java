package com.g41.trashsmart_server.Repositories;

import com.g41.trashsmart_server.Models.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

    @Query("SELECT a FROM Auction a WHERE a.isDeleted = false AND a.startDate <= CURRENT_TIMESTAMP AND a.endDate >= CURRENT_TIMESTAMP")
    List<Auction> findLiveAuctions();

    @Query("SELECT a FROM Auction a WHERE a.isDeleted = false AND a.endDate < CURRENT_TIMESTAMP AND a.isClosed = true")
    List<Auction> findPastAuctions();

    @Query("SELECT a FROM Auction a WHERE a.isDeleted = false AND a.endDate > CURRENT_TIMESTAMP AND a.isClosed = true")
    List<Auction> findCanceledAuctions();

    @Query("SELECT a FROM Auction a WHERE a.isDeleted = false AND a.startDate > CURRENT_TIMESTAMP AND a.isClosed = true")
    List<Auction> findUpcomingAuctions();
}
