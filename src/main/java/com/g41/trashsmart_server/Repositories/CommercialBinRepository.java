package com.g41.trashsmart_server.Repositories;

import com.g41.trashsmart_server.Enums.BinStatus;
import com.g41.trashsmart_server.Models.CommercialBin;
import com.g41.trashsmart_server.Models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CommercialBinRepository extends JpaRepository<CommercialBin, Long> {
    // Select a specific commercial bin using ID
    @Query("SELECT commercialbin FROM CommercialBin commercialbin WHERE commercialbin.id = :id")
    Optional<CommercialBin> findCommercialBinById(Long id);

    // Select a specific commercial bin using organization
    @Query("SELECT commercialbin FROM CommercialBin commercialbin WHERE commercialbin.organization = :organization")
    Optional<CommercialBin> findCommercialBinByOrganization(Organization organization);

    // Select a specific active commercial bin using ID
    @Query("SELECT commercialbin FROM CommercialBin commercialbin WHERE commercialbin.id = :id AND commercialbin.deleted = :deleted")
    Optional<CommercialBin> findCommercialBinById(Long id, Boolean deleted);

    // Select a specific commercial bin using bin status
    @Query("SELECT commercialbin FROM CommercialBin commercialbin WHERE commercialbin.binStatus = :binStatus AND commercialbin.deleted = :deleted")
    Optional<CommercialBin> findCommercialBinByBinStatus(BinStatus binStatus, Boolean deleted);

    // Select all the commercial bins in the system active/logically deleted
    @Query("SELECT commercialbin FROM CommercialBin commercialbin WHERE commercialbin.deleted = :deleted")
    List<CommercialBin> findAllCommercialBins(Boolean deleted);

    // Select all the commercial bins registered with the system
    @Query("SELECT commercialbin FROM CommercialBin commercialbin")
    List<CommercialBin> findAllCommercialBinsUnFiltered();
}
