package com.g41.trashsmart_server.Configuration;

import com.g41.trashsmart_server.Enums.BinSize;
import com.g41.trashsmart_server.Enums.BinStatus;
import com.g41.trashsmart_server.Enums.WasteType;
import com.g41.trashsmart_server.Models.SmartBin;
import com.g41.trashsmart_server.Repositories.SmartBinRepository;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Configuration
public class SmartBinConfig {

    private final SmartBinRepository smartBinRepository;

    public SmartBinConfig(SmartBinRepository smartBinRepository) {
        this.smartBinRepository = smartBinRepository;
    }

    @PostConstruct
    public void addDummyData() {
        // Check if the repository is already populated
        if (smartBinRepository.count() > 0) {
            return; // Skip adding data if it's already populated
        }

        // Create dummy SmartBin instances
        SmartBin bin1 = new SmartBin(79.8612, 6.9271, WasteType.BIO_DEGRADABLE, BinSize.GENERAL);
        bin1.setAPIKEY("APIKEY1");
        bin1.setFillLevel(25.0);
        bin1.setBinStatus(BinStatus.NORMAL);

        SmartBin bin2 = new SmartBin(79.8745, 6.9101, WasteType.NON_BIO_DEGRADABLE, BinSize.MEDIUM);
        bin2.setAPIKEY("APIKEY2");
        bin2.setFillLevel(0.0);
        bin2.setBinStatus(BinStatus.EMPTY);

        SmartBin bin3 = new SmartBin(79.8887, 6.9054, WasteType.RECYCLABLE, BinSize.MEGA);

        bin3.setAPIKEY("APIKEY3");
        bin3.setFillLevel(100.0);
        bin3.setBinStatus(BinStatus.FULL);

        // Save the bins to the database
        smartBinRepository.saveAll(List.of(bin1, bin2, bin3));
    }
}
