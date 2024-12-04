package com.g41.trashsmart_server.Configuration;

import com.g41.trashsmart_server.Models.HouseholdUser;
import com.g41.trashsmart_server.Repositories.HouseholdUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Configuration
public class HouseholdUserConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner householdUserCommandLineRunner(HouseholdUserRepository householdUserRepository) {
        return args -> {
            HouseholdUser rusara = new HouseholdUser(
                    "Rusara",
                    "Wimalasena",
                    "rusara.wimalasena123@gmail.com",
                    passwordEncoder.encode("password123"),
                    "0712990638",
                    "35, Reid Avenue, Colombo 07",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png"
            );
            HouseholdUser lasith = new HouseholdUser(
                    "Lasith",
                    "Ranahewa",
                    "lasith.ranahewa@gmail.com",
                    passwordEncoder.encode("password@12345678"),
                    "0712990639",
                    "50, Reid Avenue, Colombo 07",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png"
            );

            householdUserRepository.saveAll(List.of(rusara, lasith));
        };
    }
}