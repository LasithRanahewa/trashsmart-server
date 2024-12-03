package com.g41.trashsmart_server.Configuration;

import com.g41.trashsmart_server.Enums.BinSize;
import com.g41.trashsmart_server.Enums.OrgType;
import com.g41.trashsmart_server.Enums.Scale;
import com.g41.trashsmart_server.Enums.WasteType;
import com.g41.trashsmart_server.Models.CommercialBin;
import com.g41.trashsmart_server.Models.Organization;
import com.g41.trashsmart_server.Repositories.CommercialBinRepository;
import com.g41.trashsmart_server.Repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Configuration
public class CommercialBinConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner commercialbinCommandLineRunner(CommercialBinRepository commercialBinRepository,
                                                     OrganizationRepository organizationRepository) {
        return args -> {
            Organization fon = new Organization(
                    "FON",
                    "FON Admin",
                    "fon@cmb.ac.lk",
                    passwordEncoder.encode("password123"),
                    "0712990639",
                    "50, Reid Avenue, Colombo 07",
                    "www.facebook.com",
                    Scale.MEDIUM,
                    OrgType.EDUCATION,
                    6.8688460742715725,
                    79.92756809703607
            );

            organizationRepository.saveAll(List.of(fon));

            Optional<Organization> fonOptional = organizationRepository.findOrganizationByEmail("fon@cmb.ac.lk");

            if (fonOptional.isPresent()) {
                Organization organization = fonOptional.get();

                CommercialBin Bin001 = new CommercialBin(
                        fon.getLatitude(),
                        fon.getLongitude(),
                        WasteType.NON_BIO_DEGRADABLE,
                        BinSize.MEGA,
                        "TSB001"
                );
                Bin001.setOrganization(organization);

                CommercialBin Bin002 = new CommercialBin(
                        fon.getLatitude(),
                        fon.getLongitude(),
                        WasteType.BIO_DEGRADABLE,
                        BinSize.MEDIUM,
                        "TSB002"
                );
                Bin002.setOrganization(organization);
                commercialBinRepository.saveAll(List.of(Bin001, Bin002));
            } else {
                System.out.println("Organization not found!");
            }
        };
    }
}