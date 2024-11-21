package com.g41.trashsmart_server.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.g41.trashsmart_server.Enums.BinSize;
import com.g41.trashsmart_server.Enums.WasteType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@DiscriminatorValue("COMMUNAL")
public class CommunalBin extends SmartBin {
    private LocalDate installationDate;
    @ManyToMany(mappedBy = "communalBins")
    private List<Cleaner> cleaners;

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

    public List<Cleaner> getCleaners() {
        return cleaners;
    }

    public void setCleaners(List<Cleaner> cleaners) {
        this.cleaners = cleaners;
    }
}
