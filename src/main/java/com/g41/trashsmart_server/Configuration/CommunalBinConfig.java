package com.g41.trashsmart_server.Configuration;

import com.g41.trashsmart_server.Enums.BinSize;
import com.g41.trashsmart_server.Enums.WasteType;
import com.g41.trashsmart_server.Models.CommunalBin;
import com.g41.trashsmart_server.Repositories.CommunalBinRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CommunalBinConfig {
    @Bean
    CommandLineRunner CommunalBinCommandLineRunner(CommunalBinRepository communalBinRepository) {
        return args -> {
            CommunalBin Bin001 = new CommunalBin(
                    6.927079, // Galle Face Green
                    79.861244,
                    WasteType.NON_BIO_DEGRADABLE,
                    BinSize.MEGA
            );
            Bin001.setFillLevel(80.0);

            CommunalBin Bin002 = new CommunalBin(
                    6.934507, // Colombo Fort Railway Station
                    79.848173,
                    WasteType.BIO_DEGRADABLE,
                    BinSize.MEDIUM
            );
            Bin002.setFillLevel(45.0);

            CommunalBin Bin003 = new CommunalBin(
                    6.912184, // Independence Square
                    79.867691,
                    WasteType.NON_BIO_DEGRADABLE,
                    BinSize.MEDIUM
            );
            Bin003.setFillLevel(60.0);

            CommunalBin Bin004 = new CommunalBin(
                    6.865491, // Dehiwala Zoo
                    79.877640,
                    WasteType.BIO_DEGRADABLE,
                    BinSize.GENERAL
            );
            Bin004.setFillLevel(30.0);

            CommunalBin Bin005 = new CommunalBin(
                    6.795667, // Mount Lavinia Beach
                    79.882758,
                    WasteType.NON_BIO_DEGRADABLE,
                    BinSize.MEGA
            );
            Bin005.setFillLevel(50.0);

            CommunalBin Bin006 = new CommunalBin(
                    6.917619, // Viharamahadevi Park
                    79.865364,
                    WasteType.BIO_DEGRADABLE,
                    BinSize.GENERAL
            );
            Bin006.setFillLevel(70.0);

            CommunalBin Bin007 = new CommunalBin(
                    6.902082, // Bambalapitiya
                    79.860962,
                    WasteType.NON_BIO_DEGRADABLE,
                    BinSize.MEDIUM
            );
            Bin007.setFillLevel(40.0);

            CommunalBin Bin008 = new CommunalBin(
                    6.956758, // Pettah Market
                    79.854449,
                    WasteType.BIO_DEGRADABLE,
                    BinSize.GENERAL
            );
            Bin008.setFillLevel(90.0);

            communalBinRepository.saveAll(List.of(Bin001, Bin002, Bin003, Bin004, Bin005, Bin006, Bin007, Bin008));
        };
    }
}
