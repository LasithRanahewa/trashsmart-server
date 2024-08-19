package com.g41.trashsmart_server.Models;

import com.g41.trashsmart_server.Enums.BinSize;
import com.g41.trashsmart_server.Enums.WasteType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("COMMERCIAL")
public class CommercialBin extends SmartBin{
    private LocalDate purchaseDate = LocalDate.now();

    public CommercialBin() {
    }

    public CommercialBin(Double longitude, Double latitude, WasteType wasteType, BinSize binSize) {
        super(longitude, latitude, wasteType, binSize);
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
