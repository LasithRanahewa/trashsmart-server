package com.g41.trashsmart_server.Configuration;

import com.g41.trashsmart_server.Enums.BinSize;
import com.g41.trashsmart_server.Enums.WasteType;
import com.g41.trashsmart_server.Models.CommercialBin;
import com.g41.trashsmart_server.Models.Organization;
import com.g41.trashsmart_server.Repositories.CommercialBinRepository;
import com.g41.trashsmart_server.Repositories.OrganizationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class CommercialBinConfig {
    @Bean
    CommandLineRunner commercialbinCommandLineRunner(CommercialBinRepository commercialBinRepository, OrganizationRepository organizationRepository) {
        return args -> {
            Optional<Organization> optionalOrganization = organizationRepository.findOrganizationByFirstName("FOS", false);
            if (optionalOrganization.isPresent()) {
                Organization organization = optionalOrganization.get();

                CommercialBin Bin001 = new CommercialBin(
                        6.927079,
                        79.861244,
                        WasteType.NON_BIO_DEGRADABLE,
                        BinSize.MEGA
                );
                Bin001.setOrganization(organization);

                CommercialBin Bin002 = new CommercialBin(
                        6.927079,
                        79.861244,
                        WasteType.BIO_DEGRADABLE,
                        BinSize.MEDIUM
                );
                Bin002.setOrganization(organization);
                commercialBinRepository.saveAll(List.of(Bin001, Bin002));
            } else {
                System.out.println("Organization not found!");
            }
        };
    }
}