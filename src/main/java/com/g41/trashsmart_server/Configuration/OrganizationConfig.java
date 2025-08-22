package com.g41.trashsmart_server.Configuration;

import com.g41.trashsmart_server.Enums.OrgType;
import com.g41.trashsmart_server.Enums.Scale;
import com.g41.trashsmart_server.Models.Organization;
import com.g41.trashsmart_server.Repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

@Configuration
public class OrganizationConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner organizationCommandLineRunner(OrganizationRepository organizationRepository) {
        return args -> {
            Organization ucsc = new Organization(
                    "University of Colombo School of Computing",
                    "UCSC Admin",
                    "aaaa@gmail.com",
                    passwordEncoder.encode("password123"),
                    "0712990638",
                    "35, Reid Avenue, Colombo 07",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    Scale.SMALL,
                    OrgType.EDUCATION,
                    6.902256504201074,
                    79.86115034382995
            );
            ucsc.setTotalWaste(125);
            Organization fos = new Organization(
                    "FOS",
                    "FOS Admin",
                    "fos@cmb.ac.lk",
                    passwordEncoder.encode("password123"),
                    "0712990639",
                    "50, Reid Avenue, Colombo 07",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    Scale.MEDIUM,
                    OrgType.EDUCATION,
                    6.901910343977649,
                    79.86021961730209
            );
            fos.setTotalWaste(87);

            organizationRepository.saveAll(List.of(ucsc, fos));
        };
    }
}
