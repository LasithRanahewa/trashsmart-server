package com.g41.trashsmart_server.Repositories;

import com.g41.trashsmart_server.Models.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractorRepository extends JpaRepository<Contractor, Long> {
    // Select a specific contractor using email
    @Query("SELECT c FROM Contractor c WHERE c.email = :email AND c.deleted = :deleted")
    Optional<Contractor> findContractorByEmail(String email, Boolean deleted);

    // Select a specific contractor using email without deleted
    @Query("SELECT c FROM Contractor c WHERE c.email = :email")
    Optional<Contractor> findContractorByEmail(String email);

    // Select a specific contractor using id
    @Query("SELECT c FROM Contractor c WHERE c.id = :id AND c.deleted = :deleted")
    Optional<Contractor> findContractorById(Long id, Boolean deleted);

    // Select all the contractors in the system active/logically deleted
    @Query("SELECT c FROM Contractor c WHERE c.deleted = :deleted")
    List<Contractor> findAllContractors(Boolean deleted);

    // Select all the contractors registered with the system
    @Query("SELECT c FROM Contractor c")
    List<Contractor> findAllContractorsUnFiltered();
}