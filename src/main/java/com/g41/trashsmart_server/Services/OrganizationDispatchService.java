package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.Enums.*;
import com.g41.trashsmart_server.Models.*;
import com.g41.trashsmart_server.Repositories.DriverRepository;
import com.g41.trashsmart_server.Repositories.GarbageTruckRepository;
import com.g41.trashsmart_server.Repositories.OrganizationDispatchRepository;
import com.g41.trashsmart_server.Repositories.WasteCollectionRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrganizationDispatchService {
    private final WasteCollectionRequestRepository wasteCollectionRequestRepository;
    private final GarbageTruckRepository garbageTruckRepository;
    private final DriverRepository driverRepository;
    private final OrganizationDispatchRepository organizationDispatchRepository;
    private final FastApiClient fastApiClient;

    // constructor autowired
    public OrganizationDispatchService(WasteCollectionRequestRepository wasteCollectionRequestRepository,
                                       GarbageTruckRepository garbageTruckRepository,
                                       DriverRepository driverRepository,
                                       OrganizationDispatchRepository organizationDispatchRepository,
                                       FastApiClient fastApiClient) {
        this.wasteCollectionRequestRepository = wasteCollectionRequestRepository;
        this.garbageTruckRepository = garbageTruckRepository;
        this.driverRepository = driverRepository;
        this.organizationDispatchRepository = organizationDispatchRepository;
        this.fastApiClient = fastApiClient;
    }

    @Transactional
    public Map<Integer, OrganizationDispatch> clusterWasteCollectionRequests(WasteType wasteType) {
        // load new requests of this waste type
        List<WasteCollectionRequest> requests = wasteCollectionRequestRepository.findByWCRStatusAndWasteType(WasteCollectionRequestStatus.NEW, wasteType);
        List<GarbageTruck> availableTrucks = garbageTruckRepository.findByTruckStatus(TruckStatus.IDLE);
        List<Driver> availableDrivers = driverRepository.findByStatus(Status.ACTIVE);

        int availableResources = Math.min(availableTrucks.size(), availableDrivers.size());
        if (availableResources == 0) {
            throw new IllegalStateException("Not enough drivers or trucks");
        }
        if (requests.isEmpty()) {
            throw new IllegalStateException("No waste collection requests found");
        }

        // Build FastApiClient payload
        List<FastApiClient.RequestPayload> reqPayloads = requests.stream()
                .map(r -> new FastApiClient.RequestPayload(r.getId(), r.getLatitude(), r.getLongitude(), r.getAccumulatedVolume()))
                .collect(Collectors.toList());

        FastApiClient.ClusterOptionsPayload opts = new FastApiClient.ClusterOptionsPayload();
        opts.setAvailableTrucks(availableTrucks.size());
        opts.setAvailableDrivers(availableDrivers.size());
        // optional: tune these values or read from config
        opts.setMaxClusters(10);
        opts.setAvgTruckCapacityLiters(garbageTruckRepository.findAverageTruckCapacity());
        opts.setDbscanEpsKm(0.2);
        opts.setDbscanMinSamples(2);
        // BIAS centroid to municipal council (optional)
        opts.setMunicipalLatitude(6.915788733342365);
        opts.setMunicipalLongitude(79.86372182720865);
        opts.setIncludeMunicipalInCentroid(true);

        // Call FastAPI
        FastApiClient.ClusterResponseDTO response = fastApiClient.clusterRequests(reqPayloads, opts);
        if (response == null || response.getClusters() == null) {
            throw new IllegalStateException("Invalid response from clustering service");
        }

        // Map request id -> WasteCollectionRequest
        Map<Long, WasteCollectionRequest> reqById = requests.stream()
                .collect(Collectors.toMap(WasteCollectionRequest::getId, r -> r));

        Map<Integer, OrganizationDispatch> dispatchMap = new HashMap<>();

        // iterate over clusters; for each cluster, assign the i-th truck and driver
        List<FastApiClient.ClusterDTO> clusterDtos = response.getClusters();
        for (int i = 0; i < clusterDtos.size(); i++) {
            FastApiClient.ClusterDTO clusterDTO = clusterDtos.get(i);
            List<Long> clusterRequestIds = clusterDTO.getRequestIds();
            if (clusterRequestIds == null || clusterRequestIds.isEmpty()) {
                continue;
            }

            if (i >= availableTrucks.size() || i >= availableDrivers.size()) {
                // Not enough resources for this cluster; you can choose priority logic here
                break;
            }

            GarbageTruck truck = availableTrucks.get(i);
            Driver driver = availableDrivers.get(i);

            // set statuses
            truck.setTruckStatus(TruckStatus.EN_ROUTE);
            driver.setStatus(Status.UNAVAILABLE);

            // collect requests in this cluster
            List<WasteCollectionRequest> clusterRequests = new ArrayList<>();
            for (Long reqId : clusterRequestIds) {
                WasteCollectionRequest req = reqById.get(reqId);
                if (req != null) {
                    req.setWasteCollectionRequestStatus(WasteCollectionRequestStatus.COLLECTING);
                    clusterRequests.add(req);
                }
            }

            OrganizationDispatch dispatch = new OrganizationDispatch(
                    LocalDateTime.now(),
                    truck,
                    driver,
                    new ArrayList<>(clusterRequests),
                    wasteType
            );

            // set dispatch reference on each request
            for (WasteCollectionRequest r : clusterRequests) {
                r.setOrganizationDispatch(dispatch);
            }

            // persist dispatch and add to result
            organizationDispatchRepository.save(dispatch);
            dispatchMap.put(clusterDTO.getClusterId(), dispatch);
        }

        return dispatchMap;
    }

    // Get all the organization dispatches
    public List<OrganizationDispatch> getAllOrganizationDispatches() {
        return organizationDispatchRepository.findAll();
    }

    // Get all the dispatches based on the DispatchStatus
    public List<OrganizationDispatch> getOrganizationDispatchesByStatus(DispatchStatus dispatchStatus) {
        return organizationDispatchRepository.findByDispatchStatus(dispatchStatus);
    }

    // Get all the dispatches based on the DispatchStatus
    public List<OrganizationDispatch> getOrganizationDispatchesByStatusAndWasteType(DispatchStatus dispatchStatus,
                                                                                    WasteType wasteType) {
        return organizationDispatchRepository.findByDispatchStatusAndWasteType(dispatchStatus, wasteType);
    }

    // Get all the dispatches based on the Waste Type
    public List<OrganizationDispatch> getOrganizationDispatchesWasteType(WasteType wasteType) {
        return organizationDispatchRepository.findByWasteType(wasteType);
    }

    // Get all the dispatches based on ID
    public OrganizationDispatch getOrganizationDispatchById(Long id) {
        Optional<OrganizationDispatch> organizationDispatchOptional = organizationDispatchRepository.findById(id);
        if (organizationDispatchOptional.isEmpty()) {
            throw new IllegalStateException("Dispatch with id " + id + " does not exist");
        }
        return organizationDispatchOptional.get();
    }

    // Update the status of a dispatch given its ID
    public void updateOrganizationDispatchStatus(Long id, DispatchStatus dispatchStatus) {
        Optional<OrganizationDispatch> optionalOrganizationDispatch = organizationDispatchRepository.findById(id);
        if (optionalOrganizationDispatch.isEmpty()) {
            throw new IllegalStateException("Dispatch with id " + id + " does not exist");
        }
        OrganizationDispatch organizationDispatch = optionalOrganizationDispatch.get();
        if (organizationDispatch.getDispatchStatus() != dispatchStatus) {
            organizationDispatch.setDispatchStatus(dispatchStatus);
        }
    }

    // Complete an Organization Dispatch
    public void completeDispatch(Long dispatchId) {
        OrganizationDispatch organizationDispatchToUpdate = organizationDispatchRepository.findById(
                dispatchId).orElseThrow( () -> new IllegalStateException("Household User with id " + dispatchId + " does not exists")
        );
        organizationDispatchToUpdate.getDriver().setStatus(Status.ACTIVE);
        organizationDispatchToUpdate.getGarbageTruck().setTruckStatus(TruckStatus.IDLE);
        if (!organizationDispatchToUpdate.getWasteCollectionRequestList().isEmpty()) {
            for (WasteCollectionRequest wasteCollectionRequest: organizationDispatchToUpdate.getWasteCollectionRequestList()) {
                wasteCollectionRequest.setWasteCollectionRequestStatus(WasteCollectionRequestStatus.COLLECTED);
            }
        }
        organizationDispatchToUpdate.setDispatchStatus(DispatchStatus.COMPLETED);
        organizationDispatchRepository.save(organizationDispatchToUpdate);
    }

    // get waste collection requests of a dispatch
    public List<WasteCollectionRequest> getWasteCollectionRequests(Long dispatchId) {
        Optional<OrganizationDispatch> organizationDispatchOptional = organizationDispatchRepository.findById(dispatchId);
        if (organizationDispatchOptional.isEmpty()) {
            throw new IllegalStateException("Organization Dispatch with id " + dispatchId + " does not exist");
        }
        OrganizationDispatch organizationDispatch = organizationDispatchOptional.get();
        return organizationDispatch.getWasteCollectionRequestList();
    }

    // get dispatches based on driver id and status
    public List<OrganizationDispatch> getOrgDispatchByDriverStatus(Long driver_id, DispatchStatus dispatchStatus) {
        return organizationDispatchRepository.getOrgDispatchByDriverStatus(driver_id, dispatchStatus);
    }
}