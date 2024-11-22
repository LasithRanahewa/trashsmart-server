package com.g41.trashsmart_server.Configuration;

import com.g41.trashsmart_server.Models.*;
import com.g41.trashsmart_server.Repositories.MaintenanceRequestRepository;
import com.g41.trashsmart_server.Repositories.SmartBinRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class MaintenanceRequestConfig {
    @Bean
    CommandLineRunner MaintenanceRequestCommandLineRunner(
            MaintenanceRequestRepository maintenanceRequestRepository,
            SmartBinRepository smartBinRepository) {
        return args -> {
            Optional<SmartBin> optionalSmartBin = smartBinRepository.findSmartBinById(1L, false);

            if (optionalSmartBin.isPresent()) {
                SmartBin smartBin = optionalSmartBin.get();

                MaintenanceRequest MNT001 = new MaintenanceRequest(
                        smartBin,
                        "Replace lid"
                );
                MaintenanceRequest MNT002 = new MaintenanceRequest(
                        smartBin,
                        "Replace battery"
                );
                maintenanceRequestRepository.saveAll(List.of(MNT001, MNT002));
            } else {
                System.err.println("CommercialBin not found");
            }
        };
    }
}