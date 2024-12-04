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
                    "Green Eco",
                    "Lasith Ranahewa",
                    "green_eco@gmail.com",
                    passwordEncoder.encode("password123"),
                    "0779791446",
                    "Hansagir rd, Gampaha",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    "2024/Plant/01"
            );
            RecyclingPlant plant2 = new RecyclingPlant(
                    "Happy Leaf",
                    "Ravija Salpitikorala",
                    "happy_leaf@gmail.com",
                    passwordEncoder.encode("password123"),
                    "0779791446",
                    "Hansagir rd, Gampaha",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    "2024/Plant/02"
            );
            RecyclingPlant plant3 = new RecyclingPlant(
                    "Renew Earth Center",
                    "Lakruwan Kasun",
                    "renew_earth_center@gmail.com",
                    passwordEncoder.encode("password123"),
                    "0779791446",
                    "Hansagir rd, Gampaha",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    "2024/Plant/03"
            );
            RecyclingPlant plant4 = new RecyclingPlant(
                    "Planet Reclaim",
                    "Masha Wicky",
                    "planet_reclaim@gmail.com",
                    passwordEncoder.encode("password123"),
                    "0779791446",
                    "Hansagir rd, Gampaha",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    "2024/Plant/04"
            );
            RecyclingPlant plant5 = new RecyclingPlant(
                    "EverGreen Recovery",
                    "Rusara Wimalasena",
                    "evergreen_recovery@gmail.com",
                    passwordEncoder.encode("password123"),
                    "0779791446",
                    "Hansagir rd, Gampaha",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    "2024/Plant/05"
            );
            RecyclingPlant plant6 = new RecyclingPlant(
                    "Sustainergy Recycling",
                    "Pahasara Jayasuriya",
                    "sustainergy_recycling@gmail.com",
                    passwordEncoder.encode("password123"),
                    "0779791446",
                    "Hansagir rd, Gampaha",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    "2024/Plant/06"
            );

            recyclingPlantRepository.saveAll(List.of(plant1, plant2, plant3, plant4, plant5, plant6));
        };
    }
}
