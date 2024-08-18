package com.g41.trashsmart_server.Repositories;

import com.g41.trashsmart_server.Models.HouseholdUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HouseholdUserRepository extends JpaRepository<Long, HouseholdUser> {
    Optional<HouseholdUser> findHouseholdUserByEmail(String email);
}
