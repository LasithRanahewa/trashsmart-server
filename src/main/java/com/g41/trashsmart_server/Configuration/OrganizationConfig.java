package com.g41.trashsmart_server.Configuration;

import com.g41.trashsmart_server.Enums.OrgType;
import com.g41.trashsmart_server.Enums.Scale;
import com.g41.trashsmart_server.Models.HouseholdUser;
import com.g41.trashsmart_server.Models.Organization;
import com.g41.trashsmart_server.Repositories.OrganizationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OrganizationConfig {
    @Bean
    CommandLineRunner organizationCommandLineRunner(OrganizationRepository organizationRepository) {
        return args -> {
            Organization ucsc = new Organization(
                    "UCSC",
                    "UCSC Admin",
                    "ucsc@cmb.ac.lk",
                    "password@12345678",
                    "0712990638",
                    "35, Reid Avenue, Colombo 07",
                    "www.google.com",
                    Scale.SMALL,
                    OrgType.EDUCATION
            );
            Organization fos = new Organization(
                    "FOS",
                    "FOS Admin",
                    "fos@cmb.ac.lk",
                    "password@12345678",
                    "0712990639",
                    "50, Reid Avenue, Colombo 07",
                    "www.facebook.com",
                    Scale.MEDIUM,
                    OrgType.EDUCATION
            );

            organizationRepository.saveAll(List.of(ucsc, fos));
        };
    }
}
