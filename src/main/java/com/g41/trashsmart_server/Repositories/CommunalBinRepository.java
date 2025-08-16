package com.g41.trashsmart_server.Repositories;

import com.g41.trashsmart_server.Enums.BinStatus;
import com.g41.trashsmart_server.Models.CommunalBin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CommunalBinRepository extends JpaRepository<CommunalBin, Long>{
    // Select a specific communal bin using ID
    @Query("SELECT communalbin FROM CommunalBin communalbin WHERE communalbin.id = :id")
    Optional<CommunalBin> findCommunalBinById(Long id);

    // Select a specific active communal bin using ID
    @Query("SELECT communalbin FROM CommunalBin communalbin WHERE communalbin.id = :id AND communalbin.deleted = :deleted")
    Optional<CommunalBin> findCommunalBinById(Long id, Boolean deleted);

    // Select a specific communal bin using bin status
    @Query("SELECT communalbin FROM CommunalBin communalbin WHERE communalbin.binStatus = :binStatus AND communalbin.deleted = :deleted")
    Optional<CommunalBin> findCommunalBinByBinStatus(BinStatus binStatus, Boolean deleted);

    // Select all the communal bins in the system active/logically deleted
    @Query("SELECT communalbin FROM CommunalBin communalbin WHERE communalbin.deleted = :deleted")
    List<CommunalBin> findAllCommunalBins(Boolean deleted);

    // Select all the communal bins registered with the system
    @Query("SELECT communalbin FROM CommunalBin communalbin")
    List<CommunalBin> findAllCommunalBinsUnFiltered();

    // Select new communal bin establishments over last week
    @Query("SELECT COUNT(communalbin) FROM CommunalBin communalbin WHERE communalbin.installationDate >= :startDate AND communalbin.installationDate <= :endDate")
    long findNewEstablishments(LocalDate startDate, LocalDate endDate);
}
