package com.g41.trashsmart_server.Configuration;

import com.g41.trashsmart_server.Enums.BinSize;
import com.g41.trashsmart_server.Enums.BinStatus;
import com.g41.trashsmart_server.Enums.WasteType;
import com.g41.trashsmart_server.Models.SmartBin;
import com.g41.trashsmart_server.Repositories.SmartBinRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SmartBinConfig {
    @Bean
    CommandLineRunner smartbinCommandLineRunner(SmartBinRepository smartbinRepository) {
        return args -> {
            SmartBin bin1 = new SmartBin(
                79.8612,
                6.9022,
                WasteType.BIO_DEGRADABLE,
                BinSize.MEGA,
                "123456789"

                    
            );
            

            smartbinRepository.saveAll(List.of(bin1));
        };
    }
}
