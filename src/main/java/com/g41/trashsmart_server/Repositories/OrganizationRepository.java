package com.g41.trashsmart_server.Repositories;

import com.g41.trashsmart_server.Enums.BinStatus;
import com.g41.trashsmart_server.Enums.WasteCollectionRequestStatus;
import com.g41.trashsmart_server.Enums.WasteType;
import com.g41.trashsmart_server.Models.CommercialBin;
import com.g41.trashsmart_server.Models.Organization;
import com.g41.trashsmart_server.Models.OrganizationDispatch;
import com.g41.trashsmart_server.Enums.DispatchStatus;
import com.g41.trashsmart_server.Models.WasteCollectionRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    // Select a specific organization user using email
    @Query("SELECT org FROM Organization org WHERE org.email = :email AND org.deleted = :deleted")
    Optional<Organization> findOrganizationByEmail(String email, Boolean deleted);

    // Select a specific organization using email without deleted
    @Query("SELECT o FROM Organization o WHERE o.email = :email")
    Optional<Organization> findOrganizationByEmail(String email);

    // Select a specific organization using id
    @Query("SELECT org FROM Organization org WHERE org.id = :id AND org.deleted = :deleted")
    Optional<Organization> findOrganizationById(Long id, Boolean deleted);

    // Select a specific organization using name
    @Query("SELECT org FROM Organization org WHERE org.firstName = :firstName AND org.deleted = :deleted")
    Optional<Organization> findOrganizationByFirstName(String firstName, Boolean deleted);

    // Select all the organizations in the system active/logically deleted
    @Query("SELECT org FROM Organization org WHERE org.deleted = :deleted")
    List<Organization> findAllOrganizations(Boolean deleted);

    // Select all the organizations registered with the system
    @Query("SELECT org FROM Organization org")
    List<Organization> findAllOrganizationsUnFiltered();

    // count the number of organizations
    long count();

    // Get top 10 organizations
    @Query("SELECT org FROM Organization org LEFT JOIN org.commercialBins bins GROUP BY org ORDER BY COUNT(bins) DESC")
    List<Organization> findTop10OrganizationsByBinCount(Pageable pageable);

    // Select new organization registrations over last week
    @Query("SELECT COUNT(org) FROM Organization org WHERE org.contractStartDate >= :startDate AND org.contractStartDate <= :endDate")
    long getNewRegistrations(LocalDate startDate, LocalDate endDate);

    // Select active organizations count
    @Query("SELECT COUNT(org) FROM Organization org WHERE org.contractEndDate >= :today")
    long getActiveCount(LocalDate today);

    // Get the total number of bins
    @Query("SELECT COUNT(bins) FROM Organization org LEFT JOIN org.commercialBins bins WHERE org.id = :id")
    long findTotalBinCount(Long id);

    // Get the total number of full bins
    @Query("SELECT COUNT(bins) FROM Organization org LEFT JOIN org.commercialBins bins WHERE org.id = :id AND bins.binStatus = :binStatus")
    long findTotalFullBinCount(Long id, BinStatus binStatus);

    // Number of total organization dispatches
    @Query("SELECT COUNT(DISTINCT d) FROM OrganizationDispatch d JOIN d.wasteCollectionRequestList w WHERE w.organization.id = :orgId AND d.dispatchStatus = :dispatchStatus")
    long findCompletedDispatchesByOrgId(Long orgId, DispatchStatus dispatchStatus);

    // Get the total waste last week
    @Query("SELECT COALESCE(SUM(w.accumulatedVolume), 0) FROM WasteCollectionRequest w WHERE w.createdTimeStamp >= :startDate AND w.createdTimeStamp <= :endDate AND w.organization.id = :id")
    Double getTotalWasteVolumeForLastWeek(@Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate,
                                          @Param("id") Long id);

    // Last week wcr count
    @Query("SELECT COUNT(w) FROM WasteCollectionRequest w WHERE w.createdTimeStamp >= :startDate AND w.createdTimeStamp <= :endDate AND w.organization.id = :id")
    Long getCountOfWasteRequestsForLastWeek(@Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate,
                                            @Param("id") Long id);

    // Total accumulated waste (all types)
    @Query("SELECT COALESCE(SUM(w.accumulatedVolume), 0) FROM WasteCollectionRequest w WHERE w.organization.id = :id")
    Double getTotalAccumulatedWaste(@Param("id") Long id);

    // Total accumulated recyclable waste
    @Query("SELECT COALESCE(SUM(w.accumulatedVolume), 0) FROM WasteCollectionRequest w WHERE w.wasteType = :wasteType AND w.organization.id = :id")
    Double getTotalAccumulatedRecyclableWaste(WasteType wasteType, @Param("id") Long id);

    // Get monthly recyclable waste
    @Query("SELECT TO_CHAR(w.createdTimeStamp, 'FMMonth'), " +
            "       EXTRACT(YEAR FROM w.createdTimeStamp), " +
            "       COALESCE(SUM(w.accumulatedVolume), 0) " +
            "FROM WasteCollectionRequest w " +
            "WHERE w.wasteType = :wasteType " +
            "AND w.createdTimeStamp >= :startDate " +
            "AND w.createdTimeStamp <= :endDate " +
            "AND w.organization.id = :id " +
            "GROUP BY EXTRACT(YEAR FROM w.createdTimeStamp), " +
            "         EXTRACT(MONTH FROM w.createdTimeStamp), " +
            "         TO_CHAR(w.createdTimeStamp, 'FMMonth') " +
            "ORDER BY EXTRACT(YEAR FROM w.createdTimeStamp), EXTRACT(MONTH FROM w.createdTimeStamp)")
    List<Object[]> getMonthlyAccumulatedRecyclableWaste(@Param("wasteType") WasteType wasteType, @Param("startDate") LocalDateTime startDate,
                                                        @Param("endDate") LocalDateTime endDate, @Param("id") Long id);

    // Get monthly waste between startDate and endDate
    @Query("SELECT TO_CHAR(w.createdTimeStamp, 'FMMonth'), " +
            "       EXTRACT(YEAR FROM w.createdTimeStamp), " +
            "       COALESCE(SUM(w.accumulatedVolume), 0) " +
            "FROM WasteCollectionRequest w " +
            "WHERE w.createdTimeStamp >= :startDate " +
            "AND w.createdTimeStamp <= :endDate " +
            "AND w.organization.id = :id " +
            "GROUP BY EXTRACT(YEAR FROM w.createdTimeStamp), " +
            "         EXTRACT(MONTH FROM w.createdTimeStamp), " +
            "         TO_CHAR(w.createdTimeStamp, 'FMMonth') " +
            "ORDER BY EXTRACT(YEAR FROM w.createdTimeStamp), EXTRACT(MONTH FROM w.createdTimeStamp)")
    List<Object[]> getMonthlyAccumulatedWaste(@Param("startDate") LocalDateTime startDate,
                                              @Param("endDate") LocalDateTime endDate, @Param("id") Long id);

    // Get the total number of bin maintenance count
    @Query("SELECT COUNT(m) FROM MaintenanceRequest m JOIN m.smartBin b JOIN CommercialBin cb ON cb.id = b.id WHERE cb.organization.id = :orgId AND m.deleted = false")
    long countMaintenanceRequestsByOrgId(@Param("orgId") Long orgId);

    // Select new commercial bin purchases over last month
    @Query("SELECT COUNT(com_bin) FROM CommercialBin com_bin WHERE com_bin.purchaseDate >= :startDate AND com_bin.purchaseDate <= :endDate AND com_bin.organization.id = :org_id")
    long findNewPurchases(LocalDate startDate, LocalDate endDate, Long org_id);

    // Select new commercial bin purchases over the past year
    @Query("SELECT TO_CHAR(com_bin.purchaseDate, 'FMMonth'), " +
            "       EXTRACT(YEAR FROM com_bin.purchaseDate), " +
            "       COUNT(com_bin) " +
            "FROM CommercialBin com_bin " +
            "WHERE com_bin.purchaseDate >= :startDate " +
            "AND com_bin.purchaseDate <= :endDate " +
            "AND com_bin.organization.id = :org_id " +
            "GROUP BY EXTRACT(YEAR FROM com_bin.purchaseDate), " +
            "         EXTRACT(MONTH FROM com_bin.purchaseDate), " +
            "         TO_CHAR(com_bin.purchaseDate, 'FMMonth') " +
            "ORDER BY EXTRACT(YEAR FROM com_bin.purchaseDate), EXTRACT(MONTH FROM com_bin.purchaseDate)")
    List<Object[]> getMonthlyNewBinPurchases(@Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate,
                                             @Param("org_id") Long org_id);

    // List of collections
    @Query("SELECT w FROM WasteCollectionRequest w WHERE w.organization.id = :orgId")
    List<WasteCollectionRequest> findByOrganizationAndStatuses(@Param("orgId") Long orgId);

    // List of bins
    @Query("SELECT b FROM CommercialBin b WHERE b.organization.id = :orgId")
    List<CommercialBin> findOrganizationBins(@Param("orgId") Long orgId);
}
