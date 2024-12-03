package com.g41.trashsmart_server.Configuration;

import com.g41.trashsmart_server.Models.RecyclingPlant;
import com.g41.trashsmart_server.Repositories.RecyclingPlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class RecyclingPlantConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner recyclingPlantCommandLineRunner (RecyclingPlantRepository recyclingPlantRepository) {
        return args -> {
            RecyclingPlant plant1 = new RecyclingPlant(
                    "Plant1",
                    "Plastic",
                    "plant1@gmail.com",
                    passwordEncoder.encode("password123"),
                    "0779791446",
                    "Hansagir rd, Gampaha",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    "2024/Plant/01"
            );
            RecyclingPlant plant2 = new RecyclingPlant(
                    "Plant2",
                    "Metal",
                    "plant2@gmail.com",
                    passwordEncoder.encode("password123"),
                    "0779791446",
                    "Hansagir rd, Gampaha",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    "2024/Plant/02"
            );
            RecyclingPlant plant3 = new RecyclingPlant(
                    "Plant3",
                    "polythene",
                    "plant3@gmail.com",
                    passwordEncoder.encode("password123"),
                    "0779791446",
                    "Hansagir rd, Gampaha",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    "2024/Plant/03"
            );

            recyclingPlantRepository.saveAll(List.of(plant1, plant2, plant3));
        };
    }
}
