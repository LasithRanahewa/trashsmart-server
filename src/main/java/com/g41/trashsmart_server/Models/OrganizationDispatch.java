package com.g41.trashsmart_server.Models;

import com.g41.trashsmart_server.Enums.DispatchType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table
public class OrganizationDispatch extends Dispatch {
    public OrganizationDispatch() {
        this.setDispatchType(DispatchType.ORGANIZATION);
    }

    public OrganizationDispatch(LocalDateTime dateTime, Suburb suburb, GarbageTruck garbageTruck, Driver driver) {
        super(dateTime, suburb, garbageTruck, driver, DispatchType.ORGANIZATION);
    }
}
