package com.g41.trashsmart_server.Configuration;

import com.g41.trashsmart_server.Enums.AuctionWasteType;
import com.g41.trashsmart_server.Models.Auction;
import com.g41.trashsmart_server.Repositories.AuctionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
public class AuctionConfig {
    @Bean
    CommandLineRunner auctionCommandLineRunner (AuctionRepository auctionRepository) {
        return args -> {

            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

            Auction plasticAuction = new Auction(
                    AuctionWasteType.PLASTIC,
                    500.0,
                    5000.0,
                    LocalDateTime.parse("2024-02-05T11:59:11.332Z", formatter),
                    LocalDateTime.parse("2024-03-05T11:59:11.332Z", formatter)
            );
            Auction polytheneAuction = new Auction(
                    AuctionWasteType.POLYTHENE,
                    1000.0,
                    10000.0,
                    LocalDateTime.parse("2024-11-05T11:59:11.332Z", formatter),
                    LocalDateTime.parse("2024-12-05T11:59:11.332Z", formatter)
            );
            Auction metalAuction = new Auction(
                    AuctionWasteType.METAL,
                    1000.0,
                    10000.0,
                    LocalDateTime.parse("2024-12-05T11:59:11.332Z", formatter),
                    LocalDateTime.parse("2024-12-25T11:59:11.332Z", formatter)
            );

            auctionRepository.saveAll(List.of(plasticAuction, polytheneAuction, metalAuction));
        };
    }
}
