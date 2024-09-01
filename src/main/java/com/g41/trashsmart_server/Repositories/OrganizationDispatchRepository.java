package com.g41.trashsmart_server.Repositories;

import com.g41.trashsmart_server.Models.OrganizationDispatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationDispatchRepository extends JpaRepository<OrganizationDispatch, Long> {
}
