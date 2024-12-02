package com.g41.trashsmart_server.Repositories;

import com.g41.trashsmart_server.Models.HouseholdUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HouseholdUserRepository extends JpaRepository<HouseholdUser, Long> {
    // Select a specific household user using email
    @Query("SELECT hu FROM HouseholdUser hu WHERE hu.email = :email AND hu.deleted = :deleted")
    Optional<HouseholdUser> findHouseholdUserByEmail(String email, Boolean deleted);

    // Select a specific household user using email without deleted
    @Query("SELECT hu FROM HouseholdUser hu WHERE hu.email = :email")
    Optional<HouseholdUser> findHouseholdUserByEmail(String email);

    // Select a specific household user using id
    @Query("SELECT hu FROM HouseholdUser hu WHERE hu.id = :id AND hu.deleted = :deleted")
    Optional<HouseholdUser> findHouseholdUserById(Long id, Boolean deleted);

    // Select all the household users in the system active/logically deleted
    @Query("SELECT hu FROM HouseholdUser hu WHERE hu.deleted = :deleted")
    List<HouseholdUser> findAllHouseholdUsers(Boolean deleted);

    // Select all the household users registered with the system
    @Query("SELECT hu FROM HouseholdUser hu")
    List<HouseholdUser> findAllHouseholdUsersUnFiltered();

    // count the number of household users
    long count();
}
