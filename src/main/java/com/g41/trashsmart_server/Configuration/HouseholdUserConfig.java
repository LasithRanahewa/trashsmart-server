package com.g41.trashsmart_server.Configuration;

import com.g41.trashsmart_server.Models.HouseholdUser;
import com.g41.trashsmart_server.Repositories.HouseholdUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class HouseholdUserConfig {
    @Bean
    CommandLineRunner householdUserCommandLineRunner(HouseholdUserRepository householdUserRepository) {
        return args -> {
            HouseholdUser rusara = new HouseholdUser(
                    "Rusara",
                    "Wimalasena",
                    "rusara.wimalasena123@gmail.com",
                    "password@12345678",
                    "0712990638",
                    "35, Reid Avenue, Colombo 07",
                    "www.google.com"
            );
            HouseholdUser lasith = new HouseholdUser(
                    "Lasith",
                    "Ranahewa",
                    "lasith.ranahewa@gmail.com",
                    "password@12345678",
                    "0712990639",
                    "50, Reid Avenue, Colombo 07",
                    "www.facebook.com"
            );

            householdUserRepository.saveAll(List.of(rusara, lasith));
        };
    }
}
