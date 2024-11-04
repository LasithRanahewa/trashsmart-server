package com.g41.trashsmart_server.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    private List<WasteCollectionRequest> wasteCollectionRequestList;

    public OrganizationDispatch() {
        this.setDispatchType(DispatchType.ORGANIZATION);
    }

    public OrganizationDispatch(LocalDateTime dateTime, GarbageTruck garbageTruck, Driver driver,
                                List<WasteCollectionRequest> wasteCollectionRequestList) {
        super(dateTime, garbageTruck, driver, DispatchType.ORGANIZATION);
        this.wasteCollectionRequestList = wasteCollectionRequestList;
    }

    public List<WasteCollectionRequest> getWasteCollectionRequestList() {
        return wasteCollectionRequestList;
    }

    public void setWasteCollectionRequestList(List<WasteCollectionRequest> wasteCollectionRequestList) {
        this.wasteCollectionRequestList = wasteCollectionRequestList;
    }
}
