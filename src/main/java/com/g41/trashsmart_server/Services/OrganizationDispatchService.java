package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.Repositories.OrganizationDispatchRepository;
import org.springframework.stereotype.Service;

@Service
public class OrganizationDispatchService {
    private final OrganizationDispatchRepository organizationDispatchRepository;

    public OrganizationDispatchService(OrganizationDispatchRepository organizationDispatchRepository) {
        this.organizationDispatchRepository = organizationDispatchRepository;
    }
}
