package com.g41.trashsmart_server.Repositories;

import com.g41.trashsmart_server.Enums.WasteCollectionRequestStatus;
import com.g41.trashsmart_server.Enums.WasteType;
import com.g41.trashsmart_server.Models.WasteCollectionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WasteCollectionRequestRepository extends JpaRepository<WasteCollectionRequest, Long> {
    // Retrieve all the waste collection requests opened by a given organization with status
    @Query("SELECT wcr FROM WasteCollectionRequest wcr WHERE wcr.organization.id = :organizationId AND wcr.wasteCollectionRequestStatus = :wasteCollectionRequestStatus")
    List<WasteCollectionRequest> findByOrganizationId(Long organizationId,
                                                      WasteCollectionRequestStatus wasteCollectionRequestStatus);

    // Retrieve all the waste collection requests opened by a given organization
    @Query("SELECT wcr FROM WasteCollectionRequest wcr WHERE wcr.organization.id = :organizationId")
    List<WasteCollectionRequest> findByOrganizationIdWOStatus(Long organizationId);

    // Retrieve all the waste collection requests by the given Request status and waste type
    @Query( "SELECT wcr FROM WasteCollectionRequest wcr WHERE wcr.wasteCollectionRequestStatus = :wasteCollectionRequestStatus AND wcr.wasteType = :wasteType")
    List<WasteCollectionRequest> findByWCRStatusAndWasteType(WasteCollectionRequestStatus wasteCollectionRequestStatus,
                                                             WasteType wasteType);

    @Query("SELECT COALESCE(SUM(w.accumulatedVolume), 0) FROM WasteCollectionRequest w WHERE w.createdTimeStamp >= :startDate AND w.createdTimeStamp <= :endDate")
    Double getTotalWasteVolumeForLastWeek(@Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(w) FROM WasteCollectionRequest w WHERE w.createdTimeStamp >= :startDate AND w.createdTimeStamp <= :endDate")
    Long getCountOfWasteRequestsForLastWeek(@Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate);

    // Total accumulated waste (all types)
    @Query("SELECT COALESCE(SUM(w.accumulatedVolume), 0) FROM WasteCollectionRequest w")
    Double getTotalAccumulatedWaste();

    // Total accumulated recyclable waste
    @Query("SELECT COALESCE(SUM(w.accumulatedVolume), 0) FROM WasteCollectionRequest w WHERE w.wasteType = :wasteType")
    Double getTotalAccumulatedRecyclableWaste(WasteType wasteType);

    // Get monthly recyclable waste
    @Query("SELECT TO_CHAR(w.createdTimeStamp, 'FMMonth'), " +
            "       EXTRACT(YEAR FROM w.createdTimeStamp), " +
            "       COALESCE(SUM(w.accumulatedVolume), 0) " +
            "FROM WasteCollectionRequest w " +
            "WHERE w.wasteType = :wasteType " +
            "AND w.createdTimeStamp >= :startDate " +
            "AND w.createdTimeStamp < :endDate " +
            "GROUP BY EXTRACT(YEAR FROM w.createdTimeStamp), " +
            "         EXTRACT(MONTH FROM w.createdTimeStamp), " +
            "         TO_CHAR(w.createdTimeStamp, 'FMMonth') " +
            "ORDER BY EXTRACT(YEAR FROM w.createdTimeStamp), EXTRACT(MONTH FROM w.createdTimeStamp)")
    List<Object[]> getMonthlyAccumulatedRecyclableWaste(@Param("wasteType") WasteType wasteType, @Param("startDate") LocalDateTime startDate,
                                                        @Param("endDate") LocalDateTime endDate);

    // Get monthly waste between startDate and endDate
    @Query("SELECT TO_CHAR(w.createdTimeStamp, 'FMMonth'), " +
            "       EXTRACT(YEAR FROM w.createdTimeStamp), " +
            "       COALESCE(SUM(w.accumulatedVolume), 0) " +
            "FROM WasteCollectionRequest w " +
            "WHERE w.createdTimeStamp >= :startDate " +
            "AND w.createdTimeStamp < :endDate " +
            "GROUP BY EXTRACT(YEAR FROM w.createdTimeStamp), " +
            "         EXTRACT(MONTH FROM w.createdTimeStamp), " +
            "         TO_CHAR(w.createdTimeStamp, 'FMMonth') " +
            "ORDER BY EXTRACT(YEAR FROM w.createdTimeStamp), EXTRACT(MONTH FROM w.createdTimeStamp)")
    List<Object[]> getMonthlyAccumulatedWaste(@Param("startDate") LocalDateTime startDate,
                                              @Param("endDate") LocalDateTime endDate);

}
