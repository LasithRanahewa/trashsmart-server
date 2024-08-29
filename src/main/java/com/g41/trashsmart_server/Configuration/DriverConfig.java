package com.g41.trashsmart_server.Configuration;

import com.g41.trashsmart_server.Models.Driver;
import com.g41.trashsmart_server.Repositories.DriverRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.util.List;

@Configuration
public class DriverConfig {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Bean
    CommandLineRunner driverCommandLineRunner(DriverRepository driverRepository) {
        return args -> {
            Driver rumal = new Driver(
                    "Rumal",
                    "Gunawardana",
                    passwordEncoder.encode("password123"),
                    "0714394788",
                    "Kurunegala",
                    "www.google.com",
                    LocalDate.of(2001, 4, 9),
                    "200110004143"
            );
            Driver winditha = new Driver(
                    "Winditha",
                    "Gunawardana",
                    passwordEncoder.encode("password123"),
                    "0789209595",
                    "Kurunegala",
                    "rumalg123@gmail.com",
                    "www.google.com",
                    LocalDate.of(2001, 4, 9),
                    "200110004143"
            );
           

            driverRepository.saveAll(List.of(rumal,winditha));
        };
    }
}
