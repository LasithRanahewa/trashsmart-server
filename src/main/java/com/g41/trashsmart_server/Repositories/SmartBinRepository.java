package com.g41.trashsmart_server.Repositories;

import com.g41.trashsmart_server.Enums.BinStatus;
import com.g41.trashsmart_server.Models.SmartBin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface SmartBinRepository extends JpaRepository<SmartBin, Long> {
    // Select a specific smart bin using ID
    @Query("SELECT smartbin FROM SmartBin smartbin WHERE smartbin.id = :id")
    Optional<SmartBin> findSmartBinById(Long id);

    // Select a specific active smart bin using ID
    @Query("SELECT smartbin FROM SmartBin smartbin WHERE smartbin.id = :id AND smartbin.deleted = :deleted")
    Optional<SmartBin> findSmartBinById(Long id, Boolean deleted);

    // Select a specific smart bin using bin status
    @Query("SELECT smartbin FROM SmartBin smartbin WHERE smartbin.binStatus = :binStatus AND smartbin.deleted = :deleted")
    Optional<SmartBin> findSmartBinByBinStatus(BinStatus binStatus, Boolean deleted);

    // Select all the smart bins in the system active/logically deleted
    @Query("SELECT smartbin FROM SmartBin smartbin WHERE smartbin.deleted = :deleted")
    List<SmartBin> findAllSmartBins(Boolean deleted);

    // Select all the smart bins registered with the system
    @Query("SELECT smartbin FROM SmartBin smartbin")
    List<SmartBin> findAllSmartBinsUnFiltered();

    // Find the total number of active registered bins in the system
    @Query("SELECT COUNT(smartbin) FROM SmartBin smartbin WHERE smartbin.deleted = false")
    Long findTotalCount();

    // Find the total number of FULL bins
    @Query("SELECT COALESCE(COUNT(smartbin), 0) FROM SmartBin smartbin WHERE smartbin.deleted = false AND smartbin.binStatus = :binStatus")
    Long findFullLevelCount(BinStatus binStatus);

    // Find Smart bin using APIKEY
    @Query("SELECT smartbin FROM SmartBin smartbin WHERE smartbin.APIKEY = :APIKEY")
    Optional<SmartBin> findSmartBinByAPIKey(@Param("APIKEY") String APIKEY);
}
