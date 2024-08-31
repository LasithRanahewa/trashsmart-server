package com.g41.trashsmart_server.Models;


import com.g41.trashsmart_server.Enums.DispatchType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table
public class HouseholdDispatch extends Dispatch {
    public HouseholdDispatch() {
        this.setDispatchType(DispatchType.HOUSEHOLD);
    }

    public HouseholdDispatch(LocalDateTime dateTime, Suburb suburb, GarbageTruck garbageTruck, Driver driver) {
        super(dateTime, suburb, garbageTruck, driver, DispatchType.HOUSEHOLD);
    }
}
