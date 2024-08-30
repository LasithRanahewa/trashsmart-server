package com.g41.trashsmart_server.Configuration;

import com.g41.trashsmart_server.Models.Contractor;
import com.g41.trashsmart_server.Repositories.ContractorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class ContractorConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner contractorUserCommandLineRunner(ContractorRepository contractorRepository) {
        return args -> {
            Contractor contractor1 = new Contractor(
                    "Kasun",
                    "Kalhara",
                    "kasun@gmail.com",
                    passwordEncoder.encode("password123"),
                    "123456789V",
                    LocalDate.of(1995, 12, 12)
            );
            contractorRepository.saveAll(List.of(contractor1));
        };
    }
}


