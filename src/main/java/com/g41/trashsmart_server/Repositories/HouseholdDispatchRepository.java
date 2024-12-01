package com.g41.trashsmart_server.Repositories;

import com.g41.trashsmart_server.Enums.DispatchStatus;
import com.g41.trashsmart_server.Enums.WasteType;
import com.g41.trashsmart_server.Models.HouseholdDispatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HouseholdDispatchRepository extends JpaRepository<HouseholdDispatch, Long> {

    @Query("SELECT hd FROM HouseholdDispatch hd WHERE hd.dispatchStatus = :dispatchStatus")
    List<HouseholdDispatch> findByDispatchStatus(DispatchStatus dispatchStatus);

    @Query("SELECT hd FROM HouseholdDispatch hd WHERE hd.dispatchStatus = :dispatchStatus AND hd.wasteType = :wasteType")
    List<HouseholdDispatch> findByDispatchStatusAndWasteType(DispatchStatus dispatchStatus, WasteType wasteType);

    @Query("SELECT hd FROM HouseholdDispatch hd WHERE hd.wasteType = :wasteType")
    List<HouseholdDispatch> findByWasteType(WasteType wasteType);
}