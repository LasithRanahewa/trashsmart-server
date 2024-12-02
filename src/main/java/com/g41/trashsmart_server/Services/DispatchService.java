package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.Enums.DispatchStatus;
import com.g41.trashsmart_server.Models.Dispatch;
import com.g41.trashsmart_server.Models.HouseholdDispatch;
import com.g41.trashsmart_server.Models.OrganizationDispatch;
import com.g41.trashsmart_server.Repositories.HouseholdDispatchRepository;
import com.g41.trashsmart_server.Repositories.OrganizationDispatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DispatchService {
    private final HouseholdDispatchRepository householdDispatchRepository;
    private final OrganizationDispatchRepository organizationDispatchRepository;

    @Autowired
    public DispatchService(HouseholdDispatchRepository householdDispatchRepository,
                           OrganizationDispatchRepository organizationDispatchRepository) {
        this.householdDispatchRepository = householdDispatchRepository;
        this.organizationDispatchRepository = organizationDispatchRepository;
    }

    // Get all the dispatches by driver and status
    public List<Dispatch> getDispatchByDriverStatus(Long driver_id, DispatchStatus dispatchStatus) {
        List<HouseholdDispatch> householdDispatches = householdDispatchRepository.getHouseholdDispatchByDriverStatus(driver_id, dispatchStatus);
        List<OrganizationDispatch> organizationDispatches = organizationDispatchRepository.getOrgDispatchByDriverStatus(driver_id, dispatchStatus);

        List<Dispatch> dispatches = new ArrayList<>();
        dispatches.addAll(householdDispatches);
        dispatches.addAll(organizationDispatches);

        return dispatches;
    }
}
