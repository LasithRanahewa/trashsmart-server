package com.g41.trashsmart_server.Repositories;

import com.g41.trashsmart_server.Enums.BinStatus;
import com.g41.trashsmart_server.Models.CommercialBin;
import com.g41.trashsmart_server.Models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    // Select commercial bin by api key
    @Query("SELECT com_bin FROM CommercialBin com_bin WHERE com_bin.apiKey = :apiKey")
    Optional<CommercialBin> findCommercialBinByAPIKey(String apiKey);

    // Select commercial bins given organization id
    @Query("SELECT com_bin FROM CommercialBin com_bin WHERE com_bin.organization.id = :org_id")
    List<CommercialBin> findCommercialBinByOrganizationID(Long org_id);

    // Select new commercial bin purchases over last week
    @Query("SELECT COUNT(com_bin) FROM CommercialBin com_bin WHERE com_bin.purchaseDate >= :startDate AND com_bin.purchaseDate <= :endDate")
    long findNewPurchases(LocalDate startDate, LocalDate endDate);

    // Select new commercial bin purchases over the past year
    @Query("SELECT TO_CHAR(com_bin.purchaseDate, 'FMMonth'), " +
            "       EXTRACT(YEAR FROM com_bin.purchaseDate), " +
            "       COUNT(com_bin) " +
            "FROM CommercialBin com_bin " +
            "WHERE com_bin.purchaseDate >= :startDate " +
            "AND com_bin.purchaseDate < :endDate " +
            "GROUP BY EXTRACT(YEAR FROM com_bin.purchaseDate), " +
            "         EXTRACT(MONTH FROM com_bin.purchaseDate), " +
            "         TO_CHAR(com_bin.purchaseDate, 'FMMonth') " +
            "ORDER BY EXTRACT(YEAR FROM com_bin.purchaseDate), EXTRACT(MONTH FROM com_bin.purchaseDate)")
    List<Object[]> getMonthlyNewBinPurchases(@Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);

}
