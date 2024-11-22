package com.g41.trashsmart_server.Repositories;

import com.g41.trashsmart_server.Enums.RequestStatus;
import com.g41.trashsmart_server.Models.MaintenanceRequest;
import com.g41.trashsmart_server.Models.SmartBin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, Long> {
    // Select a specific maintenance request using ID
    @Query("SELECT maintenancereq FROM MaintenanceRequest maintenancereq WHERE maintenancereq.id = :id")
    Optional<MaintenanceRequest> findMaintenanceRequestById(Long id);

    // Select a specific maintenance request using bin id
    @Query("SELECT maintenancereq FROM MaintenanceRequest maintenancereq WHERE maintenancereq.smartBin = :bin")
    Optional<MaintenanceRequest> findMaintenanceRequestByBin(SmartBin bin);

    // Select a specific maintenance request using status
    @Query("SELECT maintenancereq FROM MaintenanceRequest maintenancereq WHERE maintenancereq.requestStatus = :status")
    Optional<MaintenanceRequest> findMaintenanceRequestByStatus(RequestStatus status);

    // Select all the maintenance requests registered with the system
    @Query("SELECT maintenancereq FROM MaintenanceRequest maintenancereq")
    List<MaintenanceRequest> findAllMaintenanceRequestsUnFiltered();
}
