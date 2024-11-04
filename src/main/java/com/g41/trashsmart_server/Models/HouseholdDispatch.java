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

    public HouseholdDispatch(LocalDateTime dateTime, GarbageTruck garbageTruck, Driver driver, WasteType wasteType) {
        super(dateTime, garbageTruck, driver, DispatchType.HOUSEHOLD, wasteType);
    }
}
