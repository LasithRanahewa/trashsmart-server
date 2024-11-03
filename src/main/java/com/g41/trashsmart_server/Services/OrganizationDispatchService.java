package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.Enums.Status;
import com.g41.trashsmart_server.Enums.TruckStatus;
import com.g41.trashsmart_server.Enums.WasteCollectionRequestStatus;
import com.g41.trashsmart_server.Models.Cluster;
import com.g41.trashsmart_server.Models.Driver;
import com.g41.trashsmart_server.Models.GarbageTruck;
import com.g41.trashsmart_server.Models.WasteCollectionRequest;
import com.g41.trashsmart_server.Repositories.DriverRepository;
import com.g41.trashsmart_server.Repositories.GarbageTruckRepository;
import com.g41.trashsmart_server.Repositories.WasteCollectionRequestRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrganizationDispatchService {
    private final WasteCollectionRequestRepository wasteCollectionRequestRepository;
    private final GarbageTruckRepository garbageTruckRepository;
    private final DriverRepository driverRepository;

    @Value("${municipal.council.latitude}")
    private double municipalLatitude;

    @Value("${municipal.council.longitude}")
    private double municipalLongitude;

    public OrganizationDispatchService(WasteCollectionRequestRepository wasteCollectionRequestRepository,
                                       GarbageTruckRepository garbageTruckRepository,
                                       DriverRepository driverRepository
    ) {
        this.wasteCollectionRequestRepository = wasteCollectionRequestRepository;
        this.garbageTruckRepository = garbageTruckRepository;
        this.driverRepository = driverRepository;
    }

    public Map<Integer, List<WasteCollectionRequest>> clusterWasteCollectionRequests() {
        final int MAX_CLUSTERS = 10;
        final int MIN_REQUESTS_PER_CLUSTER = 5;

        List<WasteCollectionRequest> wasteCollectionRequests =
                wasteCollectionRequestRepository.findByWasteCollectionRequestStatus(WasteCollectionRequestStatus.NEW);
        List<GarbageTruck> garbageTrucks = garbageTruckRepository.findByTruckStatus(TruckStatus.IDLE);
        List<Driver> drivers = driverRepository.findByStatus(Status.ACTIVE);

        // number of clusters, set to at most MAX_CLUSTERS
        int k = Math.min(garbageTrucks.size(), drivers.size());
        if(k > MAX_CLUSTERS) {
            k = MAX_CLUSTERS;
        } else if(k == 0) {
            throw new IllegalStateException("Not enough drivers or trucks");
        } else if(wasteCollectionRequests.size() < k * MIN_REQUESTS_PER_CLUSTER) {
            throw new IllegalStateException("Not enough waste collection requests");
        }

        List<Cluster> clusters = initializeClusters(k, wasteCollectionRequests);
        boolean clusterUpdated;

        do {
            assignRequestsToClusters(wasteCollectionRequests, clusters);
            clusterUpdated = updateClusterCentroids(clusters);
        } while(clusterUpdated);

        return convertClustersToResult(clusters);
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

    // Calculate lateral distance between the request and the cluster
    private double calculateDistance(double requestLatitude, double requestLongitude,
                                     double clusterLatitude, double clusterLongitude) {
        return Math.sqrt(Math.pow(requestLatitude - clusterLatitude, 2) + Math.pow(requestLongitude - clusterLongitude, 2));
    }
}
