package com.g41.trashsmart_server.Repositories;
import com.g41.trashsmart_server.Models.SmartBin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
public interface SmartBinRepository extends JpaRepository<SmartBin, Long> {
    // Select a specific smart bin using id
    @Query("SELECT bin FROM SmartBin bin WHERE bin.id = :id AND bin.deleted = :deleted")
    Optional<SmartBin> findSmartBinById(Long id, Boolean deleted);

    // Select all the smart bins in the system active/logically deleted
    @Query("SELECT bin FROM SmartBin bin WHERE bin.deleted = :deleted")
    List<SmartBin> findAllSmartBins(Boolean deleted);

    // Select all the smart bins registered with the system
    @Query("SELECT bin FROM SmartBin bin")
    List<SmartBin> findAllSmartBinsUnFiltered();

    // Return true or false if the non deleted smart bin api key exists in the system
    @Query("SELECT CASE WHEN COUNT(bin) > 0 THEN TRUE ELSE FALSE END FROM SmartBin bin WHERE bin.binAPI = :apiKey AND bin.deleted = :deleted")
    Boolean existsByApiKey(String apiKey, Boolean deleted);

} 