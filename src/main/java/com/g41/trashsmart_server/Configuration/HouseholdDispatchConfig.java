package com.g41.trashsmart_server.Configuration;

import com.g41.trashsmart_server.Enums.Status;
import com.g41.trashsmart_server.Enums.TruckStatus;
import com.g41.trashsmart_server.Enums.WasteType;
import com.g41.trashsmart_server.Models.Driver;
import com.g41.trashsmart_server.Models.GarbageTruck;
import com.g41.trashsmart_server.Models.HouseholdDispatch;
import com.g41.trashsmart_server.Repositories.DriverRepository;
import com.g41.trashsmart_server.Repositories.GarbageTruckRepository;
import com.g41.trashsmart_server.Repositories.HouseholdDispatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class HouseholdDispatchConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner householdDispatchCommandLineRunner(
            HouseholdDispatchRepository householdDispatchRepository,
            DriverRepository driverRepository,
            GarbageTruckRepository garbageTruckRepository) {
        return args -> {
            // Garbage Trucks
            GarbageTruck garbageTruck = new GarbageTruck(
                    "ABB1234",
                    21980,
                    3400
            );
            GarbageTruck truck1 = new GarbageTruck("DEF1234", 22000, 3600);
            GarbageTruck truck2 = new GarbageTruck("GHI5678", 24000, 4000);
            GarbageTruck truck3 = new GarbageTruck("JKL9012", 26000, 4500);

            truck1.setTruckStatus(TruckStatus.COLLECTING);
            truck2.setTruckStatus(TruckStatus.COLLECTING);
            truck3.setTruckStatus(TruckStatus.COLLECTING);
            garbageTruck.setTruckStatus(TruckStatus.COLLECTING);


            garbageTruckRepository.save(truck1);
            garbageTruckRepository.save(truck2);
            garbageTruckRepository.save(truck3);
            garbageTruckRepository.save(garbageTruck);

            // Drivers
            Driver driver = new Driver(
                    "Kavisha",
                    "Wanigarathne",
                    passwordEncoder.encode("password123"),
                    "0712990644",
                    "55, Reid Avenue, Colombo 07",
                    "kavisha@gmail.com",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    LocalDate.of(1995, 12, 12),
                    "200168686836"
            );
            Driver driver1 = new Driver(
                    "Samantha",
                    "Perera",
                    passwordEncoder.encode("password123"),
                    "0761234567",
                    "15, Flower Road, Colombo 07",
                    "samantha.perera@example.com",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    LocalDate.of(1985, 7, 12),
                    "19857623457"
            );

            Driver driver2 = new Driver(
                    "Anjali",
                    "Dias",
                    passwordEncoder.encode("password123"),
                    "0719876543",
                    "48, Duplication Road, Colombo 04",
                    "anjali.dias@example.com",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    LocalDate.of(1992, 3, 21),
                    "19927654389"
            );

            Driver driver3 = new Driver(
                    "Ruwan",
                    "Silva",
                    passwordEncoder.encode("password123"),
                    "0776543210",
                    "32, Park Road, Colombo 05",
                    "ruwan.silva@example.com",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    LocalDate.of(1989, 9, 15),
                    "19907654328"
            );

            driver1.setStatus(Status.UNAVAILABLE);
            driver2.setStatus(Status.ACTIVE);
            driver3.setStatus(Status.ACTIVE);
            driver.setStatus(Status.UNAVAILABLE);

            driverRepository.save(driver1);
            driverRepository.save(driver2);
            driverRepository.save(driver3);
            driverRepository.save(driver);

            // Routes
            String route1 = "https://www.google.com/maps/dir/?api=1&origin=6.927079,79.861244&destination=6.865,79.8997&waypoints=6.9203,79.8732|6.9344,79.8435";
            String route2 = "https://www.google.com/maps/dir/?api=1&origin=6.9146,79.8692&destination=6.8763,79.8889&waypoints=6.9012,79.8735|6.8904,79.8779";
            String route3 = "https://www.google.com/maps/dir/?api=1&origin=6.9325,79.8445&destination=6.8966,79.8587&waypoints=6.9278,79.8612|6.9090,79.8736";
            String route = "https://www.google.com/maps/dir/?api=1&origin=6.927079,79.861244&destination=6.8649,79.8997&waypoints=6.9325,79.8445|6.8966,79.8587";
            // Household Dispatches
            HouseholdDispatch hd1 = new HouseholdDispatch(
                    LocalDateTime.now(),
                    truck1,
                    driver1,
                    WasteType.RECYCLABLE,
                    route1
            );

            HouseholdDispatch hd2 = new HouseholdDispatch(
                    LocalDateTime.now().minusHours(4),
                    truck2,
                    driver2,
                    WasteType.BIO_DEGRADABLE,
                    route2
            );

            HouseholdDispatch hd3 = new HouseholdDispatch(
                    LocalDateTime.now().minusDays(1),
                    truck3,
                    driver3,
                    WasteType.RECYCLABLE,
                    route3
            );

            HouseholdDispatch hd4 = new HouseholdDispatch(
                    LocalDateTime.now().minusHours(2),
                    truck1,
                    driver2,
                    WasteType.NON_BIO_DEGRADABLE,
                    route2
            );
            HouseholdDispatch hd5 = new HouseholdDispatch(
                    LocalDateTime.now(),
                    garbageTruck,
                    driver,
                    WasteType.RECYCLABLE,
                    route
            );

            householdDispatchRepository.save(hd1);
            householdDispatchRepository.save(hd2);
            householdDispatchRepository.save(hd3);
            householdDispatchRepository.save(hd4);
            householdDispatchRepository.save(hd5);
        };
    }
}
