package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {
    private final CleanerRepository cleanerRepository;
    private final ContractorRepository contractorRepository;
    private final DriverRepository driverRepository;
    private final HouseholdUserRepository householdUserRepository;
    private final OrganizationRepository organizationRepository;
    private final RecyclingPlantRepository recyclingPlantRepository;

    @Autowired
    public StatisticsService(CleanerRepository cleanerRepository, ContractorRepository contractorRepository,
                             DriverRepository driverRepository, HouseholdUserRepository householdUserRepository,
                             OrganizationRepository organizationRepository, RecyclingPlantRepository recyclingPlantRepository) {
        this.cleanerRepository = cleanerRepository;
        this.contractorRepository = contractorRepository;
        this.driverRepository = driverRepository;
        this.householdUserRepository = householdUserRepository;
        this.organizationRepository = organizationRepository;
        this.recyclingPlantRepository = recyclingPlantRepository;
    }
}
