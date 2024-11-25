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

    public CommercialBin() {
    }

    public CommercialBin(Double longitude, Double latitude, WasteType wasteType, BinSize binSize,String APIKEY ) {
        super(longitude, latitude, wasteType, binSize, APIKEY);
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
}
