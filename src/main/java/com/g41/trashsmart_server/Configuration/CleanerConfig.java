package com.g41.trashsmart_server.Configuration;

import com.g41.trashsmart_server.Models.Cleaner;
import com.g41.trashsmart_server.Repositories.CleanerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class CleanerConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner cleanerCommandLineRunner(CleanerRepository cleanerRepository) {
        return args -> {
            Cleaner Rusara = new Cleaner(
                    "Rusara",
                    "Wimalasena",
                    passwordEncoder.encode("password123"),
                    "0712990638",
                    "35, Reid Avenue, Colombo 07",
                    "www.google.com",
                    LocalDate.of(1995, 12, 12),
                    "200168686830"
            );

            Cleaner Ravija = new Cleaner(
                    "Ravija",
                    "Salpitikorala",
                    passwordEncoder.encode("password123"),
                    "0712990638",
                    "35, Reid Avenue, Colombo 07",
                    "ravija@gmail.com",
                    "www.google.com",
                    LocalDate.of(1995, 12, 12),
                    "200168686830"
            );

            cleanerRepository.saveAll(List.of(Rusara, Ravija));
        };
    }
}
