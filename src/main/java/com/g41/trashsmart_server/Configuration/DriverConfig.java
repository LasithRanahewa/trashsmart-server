package com.g41.trashsmart_server.Configuration;

import com.g41.trashsmart_server.Enums.Status;
import com.g41.trashsmart_server.Models.Driver;
import com.g41.trashsmart_server.Repositories.DriverRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DriverConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner driverCommandLineRunner(DriverRepository driverRepository) {
        return args -> {
            Driver Rusara = new Driver(
                    "Rusara",
                    "Wimalasena",
                    passwordEncoder.encode("password123"),
                    "0712990638",
                    "35, Reid Avenue, Colombo 07",
                    "rusara.wimalasena123@gmail.com",
                    "www.google.com",
                    LocalDate.of(1995, 12, 12),
                    "200168686830"
            );

            Driver Ravija = new Driver(
                    "Ravija",
                    "Salpitikorala",
                    passwordEncoder.encode("password123"),
                    "0712990639",
                    "36, Reid Avenue, Colombo 07",
                    "ravija@gmail.com",
                    "www.google.com",
                    LocalDate.of(1995, 12, 12),
                    "200168686831"
            );

            Driver Masha = new Driver(
                    "Masha",
                    "Wickramasinghe",
                    passwordEncoder.encode("password123"),
                    "0712990640",
                    "37, Reid Avenue, Colombo 07",
                    "masha@gmail.com",
                    "www.google.com",
                    LocalDate.of(1995, 12, 12),
                    "200168686832"
            );

            Driver Lasith = new Driver(
                    "Lasith",
                    "Ranahewa",
                    passwordEncoder.encode("password123"),
                    "0712990641",
                    "38, Reid Avenue, Colombo 07",
                    "lasith@gmail.com",
                    "www.google.com",
                    LocalDate.of(1995, 12, 12),
                    "200168686833"
            );

            Driver Dasun = new Driver(
                    "Dasun",
                    "Srikantha",
                    passwordEncoder.encode("password123"),
                    "0712990642",
                    "39, Reid Avenue, Colombo 07",
                    "dasun@gmail.com",
                    "www.google.com",
                    LocalDate.of(1995, 12, 12),
                    "200168686834"
            );

            Driver Lakruwan = new Driver(
                    "Lakruwan",
                    "Kasun",
                    passwordEncoder.encode("password123"),
                    "0712990643",
                    "35, Reid Avenue, Colombo 07",
                    "lakruwan@gmail.com",
                    "www.google.com",
                    LocalDate.of(1995, 12, 12),
                    "200168686835"
            );

            driverRepository.saveAll(List.of(
                    Rusara,
                    Ravija,
                    Masha,
                    Lasith,
                    Dasun,
                    Lakruwan
            ));
        };
    }
}
