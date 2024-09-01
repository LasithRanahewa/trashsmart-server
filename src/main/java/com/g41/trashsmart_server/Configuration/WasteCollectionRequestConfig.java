package com.g41.trashsmart_server.Configuration;

import com.g41.trashsmart_server.Enums.OrgType;
import com.g41.trashsmart_server.Enums.Scale;
import com.g41.trashsmart_server.Enums.WasteType;
import com.g41.trashsmart_server.Models.Organization;
import com.g41.trashsmart_server.Models.WasteCollectionRequest;
import com.g41.trashsmart_server.Repositories.OrganizationRepository;
import com.g41.trashsmart_server.Repositories.WasteCollectionRequestRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class WasteCollectionRequestConfig {
    @Bean
    CommandLineRunner wasteCollectionRequestCommandLineRunner(
            WasteCollectionRequestRepository wasteCollectionRequestRepository,
            OrganizationRepository organizationRepository
    ) {
        Organization foa = new Organization(
                "FOA",
                "FOA Admin",
                "foa@cmb.ac.lk",
                "password@12345678",
                "0712990638",
                "35, Reid Avenue, Colombo 07",
                "www.google.com",
                Scale.SMALL,
                OrgType.EDUCATION
        );
        Organization fos = new Organization(
                "FOL",
                "FOL Admin",
                "fol@cmb.ac.lk",
                "password@12345678",
                "0712990639",
                "50, Reid Avenue, Colombo 07",
                "www.facebook.com",
                Scale.MEDIUM,
                OrgType.EDUCATION
        );

        organizationRepository.saveAll(List.of(foa, fos));

        Optional<Organization> foaOptional = organizationRepository.findOrganizationByEmail("foa@cmb.ac.lk");
        Optional<Organization> folOptional = organizationRepository.findOrganizationByEmail("fol@cmb.ac.lk");

        return args -> {
            WasteCollectionRequest wcr1 = new WasteCollectionRequest(
                    620.35,
                    WasteType.BIO_DEGRADABLE,
                    6.901655107547004,
                    79.86182154331027
            );
            if (foaOptional.isEmpty()) {
                throw new IllegalStateException("No Organization");
            }
            wcr1.setOrganization(foaOptional.get());

            WasteCollectionRequest wcr2 = new WasteCollectionRequest(
                    35.34,
                    WasteType.NON_BIO_DEGRADABLE,
                    6.901786908082538,
                    79.8590790478905
            );
            if (folOptional.isEmpty()) {
                throw new IllegalStateException("No Organization");
            }
            wcr2.setOrganization(folOptional.get());

            wasteCollectionRequestRepository.saveAll(List.of(wcr1, wcr2));
        };
    }
}
