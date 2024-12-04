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
                    "35, Reid Avenue, Colombo",
                    "www.google.com",
                    LocalDate.of(1995, 12, 12),
                    "200168686830"
            );

            Cleaner Ravija = new Cleaner(
                    "Ravija",
                    "Salpitikorala",
                    passwordEncoder.encode("password123"),
                    "0712990638",
                    "35, Reid Avenue, Gampaha",
                    "ravija@gmail.com",
                    "www.google.com",
                    LocalDate.of(1995, 12, 12),
                    "200168686831"
            );
            Cleaner Lasith = new Cleaner(
                    "Lasith",
                    "Ranahewa",
                    passwordEncoder.encode("password123"),
                    "0712990638",
                    "35, Reid Avenue, Colombo",
                    "lasith@gmail.com",
                    "www.google.com",
                    LocalDate.of(1995, 12, 12),
                    "200168686832"
            );
            Cleaner Kasun = new Cleaner(
                    "Lakruwan",
                    "Kasun",
                    passwordEncoder.encode("password123"),
                    "0712990638",
                    "35, Reid Avenue, Gampaha",
                    "kasun@gmail.com",
                    "www.google.com",
                    LocalDate.of(1995, 12, 12),
                    "200168686833"
            );
            Cleaner Pahasara = new Cleaner(
                    "Pahasara",
                    "Jayasuriya",
                    passwordEncoder.encode("password123"),
                    "0712990638",
                    "35, Reid Avenue, Kottawa",
                    "pahasara@gmail.com",
                    "www.google.com",
                    LocalDate.of(1995, 12, 12),
                    "200168686834"
            );
            Cleaner Bimsara = new Cleaner(
                    "Bimsara",
                    "Jayadewa",
                    passwordEncoder.encode("password123"),
                    "0712990638",
                    "35, Reid Avenue, Kalutara",
                    "bimsara@gmail.com",
                    "www.google.com",
                    LocalDate.of(1995, 12, 12),
                    "200168686836"
            );

            cleanerRepository.saveAll(List.of(Rusara, Ravija, Kasun, Pahasara, Bimsara));
        };
    }
}
