package com.g41.trashsmart_server.Configuration;

import com.g41.trashsmart_server.Enums.BinSize;
import com.g41.trashsmart_server.Enums.WasteType;
import com.g41.trashsmart_server.Models.CommunalBin;
import com.g41.trashsmart_server.Repositories.CommunalBinRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Configuration
public class CommunalBinConfig {
    @Bean
    CommandLineRunner CommunalBinCommandLineRunner(CommunalBinRepository communalBinRepository) {
        return args -> {
            CommunalBin Bin001 = new CommunalBin(
                    6.927079,
                    79.861244,
                    WasteType.NON_BIO_DEGRADABLE,
                    BinSize.MEGA,
                    LocalDate.of(2024,8,10)
            );
            Bin001.setFillLevel(80.0);
            CommunalBin Bin002 = new CommunalBin(
                    6.927079,
                    79.861244,
                    WasteType.NON_BIO_DEGRADABLE,
                    BinSize.MEGA,
                    LocalDate.of(2024,10,15)
            );
            Bin002.setFillLevel(45.0);
            communalBinRepository.saveAll(List.of(Bin001, Bin002));
        };
    }
}
