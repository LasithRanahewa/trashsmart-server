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
                    100
            );
            GarbageTruck DEF1234 = new GarbageTruck(
                    "DEF1234",
                    35678,
                    450
            );
            garbageTruckRepository.saveAll(List.of(ABC1234, DEF1234));
        };
    }
}
