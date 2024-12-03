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
            GarbageTruck ADC1234 = new GarbageTruck(
                    "ADC1234",
                    45230,
                    3000
            );
            GarbageTruck DEF5678 = new GarbageTruck(
                    "DEF5678",
                    38900,
                    2800
            );
            GarbageTruck GHI9012 = new GarbageTruck(
                    "GHI9012",
                    51200,
                    3200
            );
            GarbageTruck JKL3456 = new GarbageTruck(
                    "JKL3456",
                    47550,
                    3100
            );
            GarbageTruck MNO7890 = new GarbageTruck(
                    "MNO7890",
                    42800,
                    2900
            );
            GarbageTruck PQR1122 = new GarbageTruck(
                    "PQR1122",
                    39750,
                    2700
            );
            GarbageTruck STU3344 = new GarbageTruck(
                    "STU3344",
                    52340,
                    3500
            );
            GarbageTruck VWX5566 = new GarbageTruck(
                    "VWX5566",
                    44920,
                    2600
            );
            GarbageTruck YZA7788 = new GarbageTruck(
                    "YZA7788",
                    36850,
                    2400
            );
            GarbageTruck BCD9900 = new GarbageTruck(
                    "BCD9900",
                    48300,
                    3300
            );
            GarbageTruck EFG2233 = new GarbageTruck(
                    "EFG2233",
                    41275,
                    2950
            );
            GarbageTruck HIJ4455 = new GarbageTruck(
                    "HIJ4455",
                    50450,
                    3100
            );
            garbageTruckRepository.saveAll(List.of(
                    ABC1234,
                    DEF1234,
                    AAB1234,
                    AAC1234,
                    AAD1234,
                    AAE1234,
                    AAA1234,
                    ADC1234,
                    DEF5678,
                    GHI9012,
                    JKL3456,
                    MNO7890,
                    PQR1122,
                    STU3344,
                    VWX5566,
                    YZA7788,
                    BCD9900,
                    EFG2233,
                    HIJ4455
            ));
        };
    }
}
