package com.g41.trashsmart_server.Configuration;

import com.g41.trashsmart_server.Enums.WasteType;
import com.g41.trashsmart_server.Models.Driver;
import com.g41.trashsmart_server.Models.GarbageTruck;
import com.g41.trashsmart_server.Models.HouseholdDispatch;
import com.g41.trashsmart_server.Repositories.DriverRepository;
import com.g41.trashsmart_server.Repositories.GarbageTruckRepository;
import com.g41.trashsmart_server.Repositories.HouseholdDispatchRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class HouseholdDispatchConfig {

    @Bean
    CommandLineRunner householdDispatchCommandLineRunner(
            HouseholdDispatchRepository householdDispatchRepository,
            DriverRepository driverRepository,
            GarbageTruckRepository garbageTruckRepository) {
        return args -> {
            GarbageTruck garbageTruck = new GarbageTruck();
            Driver driver = new Driver();

            // Save the transient instances first
            garbageTruckRepository.save(garbageTruck);
            driverRepository.save(driver);

            String route = "https://www.google.com/maps/dir/?api=1&origin=40.748817,-73.985428&destination=40.748817,-73.985428&waypoints=40.748817,-73.985428|40.748817,-73.985428";

            HouseholdDispatch hd1 = new HouseholdDispatch(
                    LocalDateTime.now(),
                    garbageTruck,
                    driver,
                    WasteType.RECYCLABLE,
                    route
            );

            householdDispatchRepository.save(hd1);
        };
    }
}