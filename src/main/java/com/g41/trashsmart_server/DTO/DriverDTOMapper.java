package com.g41.trashsmart_server.DTO;

import com.g41.trashsmart_server.Enums.Status;
import com.g41.trashsmart_server.Models.Driver;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class DriverDTOMapper implements Function<Driver, DriverDTOMapper> {
    @Override
    public DriverDTO apply(Driver driver) {
        return new DriverDTO (
                driver.getId(),
                driver.getFirstName(),
                driver.getLastName(),
                driver.getEmail(),
                driver.getContactNo(),
                driver.getAddress(),
                driver.getRole(),
                driver.getProfileURL(),
                driver.getDob(),
                driver.getNic(),
                driver.getStatus(),
                driver.getTotalCollections(),
                driver.getCurrentStreak(),
                driver.getLongestStreak(),
                driver.getTotalActiveDays(),
                driver.getNumberOfHolidays(),
                driver.getDeleted()
        );
    }
}