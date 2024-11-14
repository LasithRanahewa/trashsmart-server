package com.g41.trashsmart_server.Repositories;

import com.g41.trashsmart_server.Enums.AuctionStatus;
import com.g41.trashsmart_server.Models.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

    @Query("SELECT a FROM Auction a WHERE a.isDeleted = false AND a.isClosed = false AND a.startDate <= CURRENT_TIMESTAMP AND a.endDate >= CURRENT_TIMESTAMP ")
    List<Auction> findLiveAuctions();

    @Query("SELECT a FROM Auction a WHERE a.isDeleted = false AND a.isClosed = false AND a.endDate < CURRENT_TIMESTAMP")
    List<Auction> findPastAuctions();

    @Query("SELECT a FROM Auction a WHERE a.isDeleted = false AND a.isClosed = true")
    List<Auction> findCanceledAuctions();

    @Query("SELECT a FROM Auction a WHERE a.isDeleted = false AND a.isClosed = false AND  a.startDate > CURRENT_TIMESTAMP")
    List<Auction> findUpcomingAuctions();

    @Query("SELECT a FROM Auction a WHERE a.isDeleted = true AND a.endDate < CURRENT_TIMESTAMP")
    List<Auction> findDeletedAuctions();

    List<Auction> findByStatus(AuctionStatus auctionStatus);

    @Query("SELECT a FROM Auction a WHERE a.isDeleted = false AND a.isClosed = false ")
    List<Auction> findAllAuctions();
}
