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
                    "0712990638",
                    "35, Reid Avenue, Colombo 07",
                    "rusara.wimalasena123@gmail.com",
                    "www.google.com",
                    LocalDate.of(1995, 12, 12),
                    "200168686830"
            );

            driverRepository.saveAll(List.of(Rusara, Ravija));
        };
    }
}
