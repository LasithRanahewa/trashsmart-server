package com.g41.trashsmart_server.Services;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fasterxml.jackson.annotation.JsonProperty;
import reactor.core.publisher.Mono;

@Service
public class FastApiClient {

    private final WebClient webClient;
    private final Duration REQUEST_TIMEOUT = Duration.ofSeconds(15);

    public FastApiClient(WebClient fastApiWebClient) {
        this.webClient = fastApiWebClient;
    }

    /**
     * Send waste collection requests and options to FastAPI /cluster and return the parsed response.
     * We construct a JSON: { "requests": [...], "opts": { ... } } payload.
     */
    public ClusterResponseDTO clusterRequests(List<RequestPayload> requests, ClusterOptionsPayload opts) {
        Map<String, Object> payload = new HashMap<>();
        // requests -> array of maps
        List<Map<String, Object>> reqs = requests.stream().map(r -> {
            Map<String, Object> m = new HashMap<>();
            m.put("id", r.getId());
            m.put("latitude", r.getLatitude());
            m.put("longitude", r.getLongitude());
            m.put("volume_liters", r.getVolumeLiters());
            return m;
        }).collect(Collectors.toList());

        payload.put("requests", reqs);

        // opts -> map with snake_case keys expected by FastAPI
        Map<String, Object> optsMap = new HashMap<>();
        optsMap.put("available_trucks", opts.getAvailableTrucks());
        optsMap.put("available_drivers", opts.getAvailableDrivers());
        if (opts.getMaxClusters() != null) optsMap.put("max_clusters", opts.getMaxClusters());
        if (opts.getAvgTruckCapacityLiters() != null) optsMap.put("avg_truck_capacity_liters", opts.getAvgTruckCapacityLiters());
        if (opts.getDbscanEpsKm() != null) optsMap.put("dbscan_eps_km", opts.getDbscanEpsKm());
        if (opts.getDbscanMinSamples() != null) optsMap.put("dbscan_min_samples", opts.getDbscanMinSamples());
        if (opts.getMunicipalLatitude() != null) optsMap.put("municipal_latitude", opts.getMunicipalLatitude());
        if (opts.getMunicipalLongitude() != null) optsMap.put("municipal_longitude", opts.getMunicipalLongitude());
        if (opts.getIncludeMunicipalInCentroid() != null) optsMap.put("include_municipal_in_centroid", opts.getIncludeMunicipalInCentroid());

        payload.put("opts", optsMap);

        try {
            Mono<ClusterResponseDTO> mono = webClient.post()
                    .uri("/cluster") // Ensure your FastAPI cluster route is /cluster or adapt
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToMono(ClusterResponseDTO.class)
                    .timeout(REQUEST_TIMEOUT);

            return mono.block(REQUEST_TIMEOUT);
        } catch (WebClientResponseException ex) {
            throw new RuntimeException("FastAPI responded with error: " + ex.getStatusCode() + " - " + ex.getResponseBodyAsString(), ex);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to call FastAPI clustering endpoint: " + ex.getMessage(), ex);
        }
    }

    // --------------------------
    // DTO helpers (inner static classes)
    // --------------------------
    public static class RequestPayload {
        private Long id;
        private Double latitude;
        private Double longitude;
        @JsonProperty("volume_liters")
        private Double volumeLiters = 0.0;

        public RequestPayload() {}

        public RequestPayload(Long id, Double latitude, Double longitude, Double volumeLiters) {
            this.id = id;
            this.latitude = latitude;
            this.longitude = longitude;
            this.volumeLiters = volumeLiters == null ? 0.0 : volumeLiters;
        }
        // getters & setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Double getLatitude() { return latitude; }
        public void setLatitude(Double latitude) { this.latitude = latitude; }
        public Double getLongitude() { return longitude; }
        public void setLongitude(Double longitude) { this.longitude = longitude; }
        public Double getVolumeLiters() { return volumeLiters; }
        public void setVolumeLiters(Double volumeLiters) { this.volumeLiters = volumeLiters; }
    }

    public static class ClusterOptionsPayload {
        private Integer availableTrucks;
        private Integer availableDrivers;
        private Integer maxClusters;
        private Double avgTruckCapacityLiters;
        private Double dbscanEpsKm;
        private Integer dbscanMinSamples;
        private Double municipalLatitude;
        private Double municipalLongitude;
        private Boolean includeMunicipalInCentroid;

        public ClusterOptionsPayload() {}

        // getters & setters
        public Integer getAvailableTrucks() { return availableTrucks; }
        public void setAvailableTrucks(Integer availableTrucks) { this.availableTrucks = availableTrucks; }
        public Integer getAvailableDrivers() { return availableDrivers; }
        public void setAvailableDrivers(Integer availableDrivers) { this.availableDrivers = availableDrivers; }
        public Integer getMaxClusters() { return maxClusters; }
        public void setMaxClusters(Integer maxClusters) { this.maxClusters = maxClusters; }
        public Double getAvgTruckCapacityLiters() { return avgTruckCapacityLiters; }
        public void setAvgTruckCapacityLiters(Double avgTruckCapacityLiters) { this.avgTruckCapacityLiters = avgTruckCapacityLiters; }
        public Double getDbscanEpsKm() { return dbscanEpsKm; }
        public void setDbscanEpsKm(Double dbscanEpsKm) { this.dbscanEpsKm = dbscanEpsKm; }
        public Integer getDbscanMinSamples() { return dbscanMinSamples; }
        public void setDbscanMinSamples(Integer dbscanMinSamples) { this.dbscanMinSamples = dbscanMinSamples; }
        public Double getMunicipalLatitude() { return municipalLatitude; }
        public void setMunicipalLatitude(Double municipalLatitude) { this.municipalLatitude = municipalLatitude; }
        public Double getMunicipalLongitude() { return municipalLongitude; }
        public void setMunicipalLongitude(Double municipalLongitude) { this.municipalLongitude = municipalLongitude; }
        public Boolean getIncludeMunicipalInCentroid() { return includeMunicipalInCentroid; }
        public void setIncludeMunicipalInCentroid(Boolean includeMunicipalInCentroid) { this.includeMunicipalInCentroid = includeMunicipalInCentroid; }
    }

    // Response mapping classes (expect FastAPI shape like { "clusters": [ { cluster_id, centroid_latitude, centroid_longitude, request_ids, total_volume_liters } ], "meta": {...} })
    public static class ClusterResponseDTO {
        private List<ClusterDTO> clusters;
        private Map<String, Object> meta;

        public List<ClusterDTO> getClusters() { return clusters; }
        public void setClusters(List<ClusterDTO> clusters) { this.clusters = clusters; }
        public Map<String, Object> getMeta() { return meta; }
        public void setMeta(Map<String, Object> meta) { this.meta = meta; }
    }

    public static class ClusterDTO {
        @JsonProperty("cluster_id")
        private Integer clusterId;
        @JsonProperty("centroid_latitude")
        private Double centroidLatitude;
        @JsonProperty("centroid_longitude")
        private Double centroidLongitude;
        @JsonProperty("request_ids")
        private List<Long> requestIds;
        @JsonProperty("total_volume_liters")
        private Double totalVolumeLiters;

        public Integer getClusterId() { return clusterId; }
        public void setClusterId(Integer clusterId) { this.clusterId = clusterId; }
        public Double getCentroidLatitude() { return centroidLatitude; }
        public void setCentroidLatitude(Double centroidLatitude) { this.centroidLatitude = centroidLatitude; }
        public Double getCentroidLongitude() { return centroidLongitude; }
        public void setCentroidLongitude(Double centroidLongitude) { this.centroidLongitude = centroidLongitude; }
        public List<Long> getRequestIds() { return requestIds; }
        public void setRequestIds(List<Long> requestIds) { this.requestIds = requestIds; }
        public Double getTotalVolumeLiters() { return totalVolumeLiters; }
        public void setTotalVolumeLiters(Double totalVolumeLiters) { this.totalVolumeLiters = totalVolumeLiters; }
    }
}

