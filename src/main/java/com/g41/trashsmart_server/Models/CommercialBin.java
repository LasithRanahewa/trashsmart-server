package com.g41.trashsmart_server.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.g41.trashsmart_server.Enums.BinSize;
import com.g41.trashsmart_server.Enums.WasteType;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("COMMERCIAL")
public class CommercialBin extends SmartBin{
    private LocalDate purchaseDate = LocalDate.now();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id")
    @JsonBackReference
    private Organization organization;
    private String apiKey;

    public CommercialBin() {
    }

    public CommercialBin(Double latitude, Double longitude, WasteType wasteType, BinSize binSize, String apiKey) {
        super(latitude, longitude, wasteType, binSize);
        this.apiKey = apiKey;
    }

    // For contractor
    public CommercialBin(WasteType wasteType, BinSize binSize, String apiKey) {
        super(null, null, wasteType, binSize);
        this.apiKey = apiKey;
    }

//    public CommercialBin(Double longitude, Double latitude, WasteType wasteType, BinSize binSize, Organization organization) {
//        super(longitude, latitude, wasteType, binSize);
//        this.organization = organization;
//    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
