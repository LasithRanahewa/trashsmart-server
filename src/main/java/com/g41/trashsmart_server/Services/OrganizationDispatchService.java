package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.Enums.*;
import com.g41.trashsmart_server.Models.*;
import com.g41.trashsmart_server.Repositories.DriverRepository;
import com.g41.trashsmart_server.Repositories.GarbageTruckRepository;
import com.g41.trashsmart_server.Repositories.OrganizationDispatchRepository;
import com.g41.trashsmart_server.Repositories.WasteCollectionRequestRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrganizationDispatchService {
    private final OrganizationDispatchRepository organizationDispatchRepository;
    private final WasteCollectionRequestRepository wasteCollectionRequestRepository;
    private final GarbageTruckRepository garbageTruckRepository;
    private final DriverRepository driverRepository;

    @Value("${municipal.council.latitude}")
    private double municipalLatitude;

    @Value("${municipal.council.longitude}")
    private double municipalLongitude;

    public OrganizationDispatchService(OrganizationDispatchRepository organizationDispatchRepository,
                                       WasteCollectionRequestRepository wasteCollectionRequestRepository,
                                       GarbageTruckRepository garbageTruckRepository,
                                       DriverRepository driverRepository
    ) {
        this.organizationDispatchRepository = organizationDispatchRepository;
        this.wasteCollectionRequestRepository = wasteCollectionRequestRepository;
        this.garbageTruckRepository = garbageTruckRepository;
        this.driverRepository = driverRepository;
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

    // Create dispatches based  on the waste type
    public Map<Integer, OrganizationDispatch> clusterWasteCollectionRequests(WasteType wasteType) {
        final int MAX_CLUSTERS = 10;
        final int MIN_REQUESTS_PER_CLUSTER = 5;

        List<WasteCollectionRequest> wasteCollectionRequests =
                wasteCollectionRequestRepository.findByWCRStatusAndWasteType(WasteCollectionRequestStatus.NEW, wasteType);
        List<GarbageTruck> garbageTrucks = garbageTruckRepository.findByTruckStatus(TruckStatus.IDLE);
        List<Driver> drivers = driverRepository.findByStatus(Status.ACTIVE);

        // number of clusters, set to at most MAX_CLUSTERS
        int k = Math.min(garbageTrucks.size(), drivers.size());
        if(k > MAX_CLUSTERS) {
            k = MAX_CLUSTERS;
        } else if(k == 0) {
            throw new IllegalStateException("Not enough drivers or trucks");
        } else if(wasteCollectionRequests.size() < k * MIN_REQUESTS_PER_CLUSTER) {
            throw new IllegalStateException("Not enough waste collection requests, wait until there are more requests to dispatch");
        }

        List<Cluster> clusters = initializeClusters(k, wasteCollectionRequests);
        boolean clusterUpdated;

        do {
            assignRequestsToClusters(wasteCollectionRequests, clusters);
            clusterUpdated = updateClusterCentroids(clusters);
        } while(clusterUpdated);

        return assignOrganizationDispatches(clusters, garbageTrucks, drivers, wasteType);
    }

    // Initialize clusters with centroids taken from the input request set
    private List<Cluster> initializeClusters(int k, List<WasteCollectionRequest> wasteCollectionRequests) {
        List<Cluster> clusters = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            WasteCollectionRequest initialRequest = wasteCollectionRequests.get(i % wasteCollectionRequests.size());
            Cluster cluster = new Cluster(i, initialRequest.getLatitude(), initialRequest.getLongitude());
            clusters.add(cluster);
        }
        return clusters;
    }

    // Assign each request to the nearest centroid
    private void assignRequestsToClusters(List<WasteCollectionRequest> wasteCollectionRequests, List<Cluster> clusters) {
        for (WasteCollectionRequest wasteCollectionRequest : wasteCollectionRequests) {
            Cluster closetCluster = null;
            double minDistance = Double.MAX_VALUE;

            for (Cluster cluster : clusters) {
                double distance = calculateDistance(wasteCollectionRequest.getLatitude(), wasteCollectionRequest.getLongitude(),
                                                    cluster.getLatitude(), cluster.getLongitude());
                if(distance < minDistance) {
                    minDistance = distance;
                    closetCluster = cluster;
                }
            }
            if(closetCluster != null) {
                closetCluster.addWasteCollectionRequests(wasteCollectionRequest);
            }
        }
    }

    // Update cluster centroids
    private boolean updateClusterCentroids(List<Cluster> clusters) {
        boolean updated = false;

        for (Cluster cluster : clusters) {
            if (cluster.getWasteCollectionRequests().isEmpty()) {
                continue;
            }

            double sumLatitude = municipalLatitude;
            double sumLongitude = municipalLongitude;

            for (WasteCollectionRequest wasteCollectionRequest : cluster.getWasteCollectionRequests()) {
                sumLatitude += wasteCollectionRequest.getLatitude();
                sumLongitude += wasteCollectionRequest.getLongitude();
            }

            double newLatitude = sumLatitude / (cluster.getWasteCollectionRequests().size() + 1);
            double newLongitude = sumLongitude / (cluster.getWasteCollectionRequests().size() + 1);

            if (newLatitude != cluster.getLatitude() || newLongitude != cluster.getLongitude()) {
                cluster.setLatitude(newLatitude);
                cluster.setLongitude(newLongitude);
                updated = true;
            }

            cluster.clearWasteCollectionRequests();
        }

        return updated;
    }

    // Convert the clusters into results
    private Map<Integer, List<WasteCollectionRequest>> convertClustersToResult(List<Cluster> clusters) {
        Map<Integer, List<WasteCollectionRequest>> result = new HashMap<>();
        for (Cluster cluster : clusters) {
            result.put(cluster.getId(), new ArrayList<>(cluster.getWasteCollectionRequests()));
        }
        return result;
    }

    // Assign a driver and a truck to each cluster
    private Map<Integer, OrganizationDispatch> assignOrganizationDispatches(List<Cluster> clusterList,
                                                                            List<GarbageTruck> garbageTruckList,
                                                                            List<Driver> driverList,
                                                                            WasteType wasteType) {
        Map<Integer, OrganizationDispatch> organizationDispatches = new HashMap<>();
        for (int i = 0; i < clusterList.size(); i++) {
            Cluster cluster = clusterList.get(i);
            GarbageTruck garbageTruck = garbageTruckList.get(i);
            Driver driver = driverList.get(i);
            garbageTruck.setTruckStatus(TruckStatus.EN_ROUTE);
            driver.setStatus(Status.UNAVAILABLE);

            OrganizationDispatch dispatch = new OrganizationDispatch(
                    LocalDateTime.now(),
                    garbageTruck,
                    driver,
                    new ArrayList<>(cluster.getWasteCollectionRequests()),
                    wasteType
            );

            organizationDispatches.put(cluster.getId(), dispatch);
        }

        return organizationDispatches;
    }

    // Calculate lateral distance between the request and the cluster
    private double calculateDistance(double requestLatitude, double requestLongitude,
                                     double clusterLatitude, double clusterLongitude) {
        return Math.sqrt(Math.pow(requestLatitude - clusterLatitude, 2) + Math.pow(requestLongitude - clusterLongitude, 2));
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
}
