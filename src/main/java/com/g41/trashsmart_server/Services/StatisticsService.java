package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.Enums.WasteType;
import com.g41.trashsmart_server.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StatisticsService {
    private final CleanerRepository cleanerRepository;
    private final ContractorRepository contractorRepository;
    private final DriverRepository driverRepository;
    private final HouseholdUserRepository householdUserRepository;
    private final OrganizationRepository organizationRepository;
    private final RecyclingPlantRepository recyclingPlantRepository;
    private final HouseholdDispatchRepository householdDispatchRepository;
    private final OrganizationDispatchRepository organizationDispatchRepository;
    private final WasteCollectionRequestRepository wasteCollectionRequestRepository;

    @Autowired
    public StatisticsService(CleanerRepository cleanerRepository, ContractorRepository contractorRepository,
                             DriverRepository driverRepository, HouseholdUserRepository householdUserRepository,
                             OrganizationRepository organizationRepository, RecyclingPlantRepository recyclingPlantRepository,
                             HouseholdDispatchRepository householdDispatchRepository, OrganizationDispatchRepository organizationDispatchRepository,
                             WasteCollectionRequestRepository wasteCollectionRequestRepository) {
        this.cleanerRepository = cleanerRepository;
        this.contractorRepository = contractorRepository;
        this.driverRepository = driverRepository;
        this.householdUserRepository = householdUserRepository;
        this.organizationRepository = organizationRepository;
        this.recyclingPlantRepository = recyclingPlantRepository;
        this.householdDispatchRepository = householdDispatchRepository;
        this.organizationDispatchRepository = organizationDispatchRepository;
        this.wasteCollectionRequestRepository = wasteCollectionRequestRepository;
    }

    // Get the total number of users in the system
    public long getTotalUsers() {
        return (
                cleanerRepository.count() + contractorRepository.count() + driverRepository.count() +
                householdUserRepository.count() + organizationRepository.count() + recyclingPlantRepository.count()
        );
    }

    // Get the total number of dispatches in the system
    public long getTotalDispatches() {
        return (householdDispatchRepository.count() + organizationDispatchRepository.count());
    }

    // Get the total waste last week
    public Double getLastWeekTotalWasteVolume() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusWeeks(1);

        return wasteCollectionRequestRepository.getTotalWasteVolumeForLastWeek(startDate, endDate);
    }

    // Number of WCRs last week
    public Long getLastWeekWasteRequestCount() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusWeeks(1);

        return wasteCollectionRequestRepository.getCountOfWasteRequestsForLastWeek(startDate, endDate);
    }

    // Total accumulated waste (all types)
    public Double getTotalAccumulatedWaste() {
        return wasteCollectionRequestRepository.getTotalAccumulatedWaste();
    }

    // Total accumulated recyclable waste
    public Double getTotalAccumulatedRecyclableWaste() {
        return wasteCollectionRequestRepository.getTotalAccumulatedRecyclableWaste(WasteType.RECYCLABLE);
    }
}
