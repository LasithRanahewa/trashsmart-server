package com.g41.trashsmart_server.Models;


import com.g41.trashsmart_server.Enums.DispatchType;
import com.g41.trashsmart_server.Enums.WasteType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("HOUSEHOLD")
public class HouseholdDispatch extends Dispatch {
    public HouseholdDispatch() {
        this.setDispatchType(DispatchType.HOUSEHOLD);
    }

    private String route;

    public HouseholdDispatch(LocalDateTime dateTime, GarbageTruck garbageTruck, Driver driver, WasteType wasteType, String route) {
        super(dateTime, garbageTruck, driver, DispatchType.HOUSEHOLD, wasteType);
        this.route = route;
    }

    public HouseholdDispatch(LocalDateTime dateTime, GarbageTruck garbageTruck, WasteType wasteType, String route) {
        super(dateTime, garbageTruck, null, DispatchType.HOUSEHOLD, wasteType);
        this.route = route;
    }

    public String getRoute() {
        return route;
    }
}
