package com.g41.trashsmart_server.DTO;

public class CommercialBinRequestDTO {

    private Double longitude;
    private Double latitude;
    private String wasteType;
    private String binSize;
    private Long organizationId; // organization_id

    // Getters and Setters

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getWasteType() {
        return wasteType;
    }

    public void setWasteType(String wasteType) {
        this.wasteType = wasteType;
    }

    public String getBinSize() {
        return binSize;
    }

    public void setBinSize(String binSize) {
        this.binSize = binSize;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }
}

