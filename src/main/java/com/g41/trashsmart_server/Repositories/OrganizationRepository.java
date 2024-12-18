package com.g41.trashsmart_server.Repositories;

import com.g41.trashsmart_server.Models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    // Select a specific organization user using email
    @Query("SELECT org FROM Organization org WHERE org.email = :email AND org.deleted = :deleted")
    Optional<Organization> findOrganizationByEmail(String email, Boolean deleted);

    // Select a specific organization using email without deleted
    @Query("SELECT o FROM Organization o WHERE o.email = :email")
    Optional<Organization> findOrganizationByEmail(String email);

    // Select a specific organization using id
    @Query("SELECT org FROM Organization org WHERE org.id = :id AND org.deleted = :deleted")
    Optional<Organization> findOrganizationById(Long id, Boolean deleted);

    // Select a specific organization using name
    @Query("SELECT org FROM Organization org WHERE org.firstName = :firstName AND org.deleted = :deleted")
    Optional<Organization> findOrganizationByFirstName(String firstName, Boolean deleted);

    // Select all the organizations in the system active/logically deleted
    @Query("SELECT org FROM Organization org WHERE org.deleted = :deleted")
    List<Organization> findAllOrganizations(Boolean deleted);

    // Select all the organizations registered with the system
    @Query("SELECT org FROM Organization org")
    List<Organization> findAllOrganizationsUnFiltered();

    // count the number of organizations
    long count();
}
