package com.g41.trashsmart_server.Repositories;

import com.g41.trashsmart_server.Models.WasteCollectionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WasteCollectionRequestRepository extends JpaRepository<WasteCollectionRequest, Long> {
    // Retrieve all the waste collection requests opened by a given organization
    List<WasteCollectionRequest> findByOrganizationId(Long organizationId);
}
