package com.g41.trashsmart_server.Models;

import com.g41.trashsmart_server.Enums.BinSize;
import com.g41.trashsmart_server.Enums.WasteType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table
public class CommunalBin extends SmartBin {
    private LocalDate installationDate;

    public CommunalBin() {
    }

    public CommunalBin(Double longitude, Double latitude, WasteType wasteType, BinSize binSize,
                       LocalDate installationDate) {
        super(longitude, latitude, wasteType, binSize);
        this.installationDate = installationDate;
    }

    public LocalDate getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(LocalDate installationDate) {
        this.installationDate = installationDate;
    }
}
