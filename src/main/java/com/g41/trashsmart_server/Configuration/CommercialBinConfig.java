package com.g41.trashsmart_server.Configuration;

import com.g41.trashsmart_server.Enums.*;
import com.g41.trashsmart_server.Models.CommercialBin;
import com.g41.trashsmart_server.Models.Organization;
import com.g41.trashsmart_server.Repositories.CommercialBinRepository;
import com.g41.trashsmart_server.Repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
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
                    "www.facebook.com/FON",
                    Scale.MEDIUM,
                    OrgType.EDUCATION,
                    6.868846,
                    79.927568
            );

            Organization sltc = new Organization(
                    "SLTC",
                    "SLTC Admin",
                    "admin@sltc.lk",
                    passwordEncoder.encode("securePass123"),
                    "0701234567",
                    "SLTC Campus, Padukka",
                    "www.sltc.ac.lk",
                    Scale.LARGE,
                    OrgType.EDUCATION,
                    6.825847,
                    80.041227
            );

            Organization zoo = new Organization(
                    "Dehiwala Zoo",
                    "Zoo Admin",
                    "info@zoo.lk",
                    passwordEncoder.encode("zoo1234"),
                    "0112345678",
                    "Dehiwala Zoo, Colombo",
                    "www.zoo.lk",
                    Scale.LARGE,
                    OrgType.TOURISM_AND_HOSPITALITY,
                    6.851549,
                    79.876450
            );

            organizationRepository.saveAll(List.of(fon, sltc, zoo));

            // Creating bins for FON
            Optional<Organization> fonOptional = organizationRepository.findOrganizationByEmail("fon@cmb.ac.lk");
            fonOptional.ifPresent(organization -> {
                CommercialBin Bin001 = new CommercialBin(
                        organization.getLatitude(),
                        organization.getLongitude(),
                        WasteType.NON_BIO_DEGRADABLE,
                        BinSize.MEGA,
                        "TSB001"
                );
                Bin001.setOrganization(organization);
                Bin001.setPurchaseDate(LocalDate.of(2025, 4, 15));

                CommercialBin Bin002 = new CommercialBin(
                        organization.getLatitude(),
                        organization.getLongitude(),
                        WasteType.BIO_DEGRADABLE,
                        BinSize.MEDIUM,
                        "TSB002"
                );
                Bin002.setOrganization(organization);
                Bin002.setPurchaseDate(LocalDate.of(2025, 8, 15));

                commercialBinRepository.saveAll(List.of(Bin001, Bin002));
            });

            // Creating bins for SLTC
            Optional<Organization> sltcOptional = organizationRepository.findOrganizationByEmail("admin@sltc.lk");
            sltcOptional.ifPresent(organization -> {
                CommercialBin Bin003 = new CommercialBin(
                        organization.getLatitude(),
                        organization.getLongitude(),
                        WasteType.NON_BIO_DEGRADABLE,
                        BinSize.GENERAL,
                        "TSB003"
                );
                Bin003.setOrganization(organization);
                Bin003.setBinStatus(BinStatus.ALMOST_FULL);

                CommercialBin Bin004 = new CommercialBin(
                        organization.getLatitude(),
                        organization.getLongitude(),
                        WasteType.BIO_DEGRADABLE,
                        BinSize.MEGA,
                        "TSB004"
                );
                Bin004.setOrganization(organization);
                Bin004.setBinStatus(BinStatus.FULL);
                Bin004.setPurchaseDate(LocalDate.of(2025, 7, 15));

                commercialBinRepository.saveAll(List.of(Bin003, Bin004));
            });

            // Creating bins for Dehiwala Zoo
            Optional<Organization> zooOptional = organizationRepository.findOrganizationByEmail("info@zoo.lk");
            zooOptional.ifPresent(organization -> {
                CommercialBin Bin005 = new CommercialBin(
                        organization.getLatitude(),
                        organization.getLongitude(),
                        WasteType.NON_BIO_DEGRADABLE,
                        BinSize.MEDIUM,
                        "TSB005"
                );
                Bin005.setOrganization(organization);
                Bin005.setBinStatus(BinStatus.NORMAL);
                Bin005.setPurchaseDate(LocalDate.of(2025, 8, 15));

                CommercialBin Bin006 = new CommercialBin(
                        organization.getLatitude(),
                        organization.getLongitude(),
                        WasteType.BIO_DEGRADABLE,
                        BinSize.MEDIUM,
                        "TSB006"
                );
                Bin006.setOrganization(organization);
                Bin006.setFillLevel(75.0);
                Bin006.setBinStatus(BinStatus.ALMOST_FULL);

                commercialBinRepository.saveAll(List.of(Bin005, Bin006));
            });
        };
    }
}
