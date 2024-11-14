package com.g41.trashsmart_server.Repositories;

import com.g41.trashsmart_server.Models.Cleaner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CleanerRepository extends JpaRepository<Cleaner, Long> {

    @Query("SELECT c FROM Cleaner c WHERE c.deleted = :deleted")
    List<Cleaner> findCleaners(boolean deleted);

    @Query("SELECT c FROM  Cleaner c")
    List<Cleaner> findAllCleanersUnfiltered();

    @Query("SELECT c FROM Cleaner c WHERE c.email = :email")
    Optional<Cleaner> findByEmail(String email);

    @Query("SELECT c FROM Cleaner c WHERE c.contactNo = :contactNo")
    Optional<Cleaner> findByContactNo(String contactNo);
}
