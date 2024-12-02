package com.g41.trashsmart_server.Repositories;

import com.g41.trashsmart_server.Models.RecyclingPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecyclingPlantRepository extends JpaRepository<RecyclingPlant, Long> {

    Optional<RecyclingPlant> findByBRN(String brn);

    Optional<RecyclingPlant> findByEmail(String email);

    @Query("SELECT r FROM RecyclingPlant r")
    List<RecyclingPlant> findAllRecyclingPlants();

    @Query("SELECT r FROM RecyclingPlant r WHERE r.deleted = :deleted")
    List<RecyclingPlant> findRecyclingPlantsFiltered(boolean deleted);

    // count the number of recycling plants
    long count();
}
