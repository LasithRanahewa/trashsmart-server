package com.g41.trashsmart_server.Repositories;

import com.g41.trashsmart_server.Enums.TruckStatus;
import com.g41.trashsmart_server.Models.GarbageTruck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface GarbageTruckRepository extends JpaRepository<GarbageTruck, Long> {
    // Select a specific garbage truck using ID
    @Query("SELECT truck FROM GarbageTruck truck WHERE truck.id = :id")
    Optional<GarbageTruck> findGarbageTruckById(Long id);

    // Select a specific garbage truck using license plate
    @Query("SELECT truck FROM GarbageTruck truck WHERE truck.licencePlateNo = :license_plate")
    Optional<GarbageTruck> findGarbageTruckByLicensePlate(String license_plate);

    // Select all the garbage trucks registered with the system
    @Query("SELECT truck FROM GarbageTruck truck")
    List<GarbageTruck> findAllGarbageTrucksGUnFiltered();

    // Retrieve all the garbage trucks by status
    List<GarbageTruck> findByTruckStatus(TruckStatus truckStatus);
}
