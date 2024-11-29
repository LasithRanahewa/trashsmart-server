package com.g41.trashsmart_server.Configuration;

import com.g41.trashsmart_server.Enums.TruckStatus;
import com.g41.trashsmart_server.Models.GarbageTruck;
import com.g41.trashsmart_server.Repositories.GarbageTruckRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class GarbageTruckConfig {
    @Bean
    CommandLineRunner garbagetruckCommandLineRunner(GarbageTruckRepository garbageTruckRepository) {
        return args -> {
            GarbageTruck ABC1234 = new GarbageTruck(
                    "ABC1234",
                    23460,
                    1700
            );
            GarbageTruck DEF1234 = new GarbageTruck(
                    "DEF1234",
                    35678,
                    4500
            );
            GarbageTruck AAB1234 = new GarbageTruck(
                    "AAB1234",
                    36822,
                    5100
            );
            GarbageTruck AAC1234 = new GarbageTruck(
                    "AAC1234",
                    56781,
                    2300
            );
            GarbageTruck AAD1234 = new GarbageTruck(
                    "AAD1234",
                    35600,
                    4800
            );
            GarbageTruck AAE1234 = new GarbageTruck(
                    "AAE1234",
                    67823,
                    3100
            );
            GarbageTruck AAA1234 = new GarbageTruck(
                    "AAA1234",
                    36780,
                    2500
            );
            garbageTruckRepository.saveAll(List.of(
                    ABC1234,
                    DEF1234,
                    AAB1234,
                    AAC1234,
                    AAD1234,
                    AAE1234,
                    AAA1234
            ));
        };
    }
}
