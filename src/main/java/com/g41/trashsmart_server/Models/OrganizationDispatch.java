package com.g41.trashsmart_server.Models;

import com.g41.trashsmart_server.Enums.DispatchType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@DiscriminatorValue("ORGANIZATION")
public class OrganizationDispatch extends Dispatch {
    @OneToMany(mappedBy = "organizationDispatch")
    private List<WasteCollectionRequest> wasteCollectionRequestList;

    public OrganizationDispatch() {
        this.setDispatchType(DispatchType.ORGANIZATION);
    }

    public OrganizationDispatch(LocalDateTime dateTime, Suburb suburb, GarbageTruck garbageTruck, Driver driver) {
        super(dateTime, suburb, garbageTruck, driver, DispatchType.ORGANIZATION);
    }

    public List<WasteCollectionRequest> getWasteCollectionRequestList() {
        return wasteCollectionRequestList;
    }

    public void setWasteCollectionRequestList(List<WasteCollectionRequest> wasteCollectionRequestList) {
        this.wasteCollectionRequestList = wasteCollectionRequestList;
    }
}
