package com.g41.trashsmart_server.Repositories;

import com.g41.trashsmart_server.Enums.DispatchStatus;
import com.g41.trashsmart_server.Enums.WasteType;
import com.g41.trashsmart_server.Models.OrganizationDispatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationDispatchRepository extends JpaRepository<OrganizationDispatch, Long> {
    // Get organization dispatches by dispatch status
    @Query("SELECT org_dispatch FROM OrganizationDispatch org_dispatch WHERE dispatchStatus = :dispatchStatus")
    List<OrganizationDispatch> findByDispatchStatus(DispatchStatus dispatchStatus);

    // Get organization dispatches by dispatch status and waste type
    @Query("SELECT org_dispatch FROM OrganizationDispatch org_dispatch WHERE dispatchStatus = :dispatchStatus AND wasteType = :wasteType")
    List<OrganizationDispatch> findByDispatchStatusAndWasteType(DispatchStatus dispatchStatus, WasteType wasteType);

    // Get organization dispatches by waste type
    @Query("SELECT org_dispatch FROM OrganizationDispatch org_dispatch WHERE wasteType = :wasteType")
    List<OrganizationDispatch> findByWasteType(WasteType wasteType);

    // Get dispatches based on driver id and status
    @Query("SELECT org_dispatch FROM OrganizationDispatch org_dispatch WHERE driver.id = :driver_id AND dispatchStatus = :dispatchStatus")
    List<OrganizationDispatch> getOrgDispatchByDriverStatus(Long driver_id, DispatchStatus dispatchStatus);

    // Number of total organization dispatches
    long count();
}
