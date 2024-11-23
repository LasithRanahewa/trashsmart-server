package com.g41.trashsmart_server.Repositories;

import com.g41.trashsmart_server.Enums.WasteCollectionRequestStatus;
import com.g41.trashsmart_server.Enums.WasteType;
import com.g41.trashsmart_server.Models.WasteCollectionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WasteCollectionRequestRepository extends JpaRepository<WasteCollectionRequest, Long> {
    // Retrieve all the waste collection requests opened by a given organization
    @Query("SELECT wcr FROM WasteCollectionRequest wcr WHERE wcr.organization.id = :organizationId AND wcr.wasteCollectionRequestStatus = :wasteCollectionRequestStatus")
    List<WasteCollectionRequest> findByOrganizationId(Long organizationId,
                                                      WasteCollectionRequestStatus wasteCollectionRequestStatus);

    // Retrieve all the waste collection requests by the given Request status and waste type
    @Query( "SELECT wcr FROM WasteCollectionRequest wcr WHERE wcr.wasteCollectionRequestStatus = :wasteCollectionRequestStatus AND wcr.wasteType = :wasteType")
    List<WasteCollectionRequest> findByWCRStatusAndWasteType(WasteCollectionRequestStatus wasteCollectionRequestStatus,
                                                             WasteType wasteType);
}
