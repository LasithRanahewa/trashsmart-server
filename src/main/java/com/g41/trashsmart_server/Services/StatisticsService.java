package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.Enums.BinStatus;
import com.g41.trashsmart_server.Enums.WasteType;
import com.g41.trashsmart_server.Models.BusinessUser;
import com.g41.trashsmart_server.Models.Organization;
import com.g41.trashsmart_server.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final CommunalBinRepository communalBinRepository;
    private final CommercialBinRepository commercialBinRepository;
    private final SmartBinRepository smartBinRepository;

    @Autowired
    public StatisticsService(CleanerRepository cleanerRepository, ContractorRepository contractorRepository,
                             DriverRepository driverRepository, HouseholdUserRepository householdUserRepository,
                             OrganizationRepository organizationRepository, RecyclingPlantRepository recyclingPlantRepository,
                             HouseholdDispatchRepository householdDispatchRepository, OrganizationDispatchRepository organizationDispatchRepository,
                             WasteCollectionRequestRepository wasteCollectionRequestRepository,
                             CommunalBinRepository communalBinRepository,
                             CommercialBinRepository commercialBinRepository,
                             SmartBinRepository smartBinRepository) {
        this.cleanerRepository = cleanerRepository;
        this.contractorRepository = contractorRepository;
        this.driverRepository = driverRepository;
        this.householdUserRepository = householdUserRepository;
        this.organizationRepository = organizationRepository;
        this.recyclingPlantRepository = recyclingPlantRepository;
        this.householdDispatchRepository = householdDispatchRepository;
        this.organizationDispatchRepository = organizationDispatchRepository;
        this.wasteCollectionRequestRepository = wasteCollectionRequestRepository;
        this.communalBinRepository = communalBinRepository;
        this.commercialBinRepository = commercialBinRepository;
        this.smartBinRepository = smartBinRepository;
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

    // Organization count
    public long getTotalOrganizations(){
        return organizationRepository.count();
    }

    // New organization count (past week)
    public long getTotalNewOrganizations() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusWeeks(1);

        return organizationRepository.getNewRegistrations(startDate, endDate);
    }

    // Active organization count
    public long getActiveOrganizations() {
        LocalDate today = LocalDate.now();

        return organizationRepository.getActiveCount(today);
    }

    // Number of orgs' WCRs last week
    public Long getLastWeekOrgWasteRequestCount() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusWeeks(1);

        return wasteCollectionRequestRepository.getCountOfWasteRequestsForLastWeek(startDate, endDate);
    }

    // Get the total number of bins in the system
    public long getTotalBins() {
        return (smartBinRepository.count());
    }

    // Get the total number of bins in the system
    public long getFullBins() {
        BinStatus binStatus = BinStatus.FULL;
        return smartBinRepository.findFullLevelCount(binStatus);
    }

    // Get top 10 organizations
    public List<Organization> getTopTenOrganizations() {
        Pageable pageable = PageRequest.of(0, 10);
        return organizationRepository.findTop10OrganizationsByBinCount(pageable);
    }

    // Get commercial bin purchase count over last week
    public long getCommercialBinPurchaseCount() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusWeeks(1);

        return commercialBinRepository.findNewPurchases(startDate, endDate);
    }

    // Get communal bin establishment count over last week
    public long getCommunalBinEstCount() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusWeeks(1);

        return communalBinRepository.findNewEstablishments(startDate, endDate);
    }

    // New recycling plant count (past week)
    public long getTotalNewRecyclingPlants() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusWeeks(1);

        return recyclingPlantRepository.getNewRegistrations(startDate, endDate);
    }

    // Active organization count
//    public long getActiveRecyclingPlants() {
//        return recyclingPlantRepository.getActiveCount();
//    }

    // Monthly accumulated recyclable waste
    public List<Map<String, Object>> getMonthlyAccumulatedRecyclableWaste() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusMonths(12);
        List<Object[]> rawData = wasteCollectionRequestRepository.getMonthlyAccumulatedRecyclableWaste(WasteType.RECYCLABLE, startDate, endDate);
        return rawData.stream()
                .map(data -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("month", ((String) data[0]).trim());
                    map.put("year", data[1]);
                    map.put("volume", data[2] != null ? ((Number) data[2]).doubleValue() : 0.0);
                    return map;
                })
                .collect(Collectors.toList());
    }

    // Monthly accumulated  waste
    public List<Map<String, Object>> getMonthlyAccumulatedWaste() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusMonths(12);
        List<Object[]> rawData = wasteCollectionRequestRepository.getMonthlyAccumulatedWaste(startDate, endDate);

        return rawData.stream()
                .map(data -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("month", ((String) data[0]).trim());
                    map.put("year", data[1]);
                    map.put("volume", data[2] != null ? ((Number) data[2]).doubleValue() : 0.0);
                    return map;
                })
                .collect(Collectors.toList());
    }

    // Monthly commercial bin purchase count
    public List<Map<String, Object>> getMonthlyCommercialBinPurchases() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(12);
        List<Object[]> rawData = commercialBinRepository.getMonthlyNewBinPurchases(startDate, endDate);

        return rawData.stream()
                .map(data -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("month", ((String) data[0]).trim());
                    map.put("year", data[1]);
                    map.put("count", data[2] != null ? ((Number) data[2]).doubleValue() : 0.0);
                    return map;
                })
                .collect(Collectors.toList());
    }

    // Monthly communal bin purchase count
    public List<Map<String, Object>> getMonthlyCommunalBinPurchases() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(12);
        List<Object[]> rawData = communalBinRepository.getMonthlyNewBinPurchases(startDate, endDate);

        return rawData.stream()
                .map(data -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("month", ((String) data[0]).trim());
                    map.put("year", data[1]);
                    map.put("count", data[2] != null ? ((Number) data[2]).doubleValue() : 0.0);
                    return map;
                })
                .collect(Collectors.toList());
    }
}
