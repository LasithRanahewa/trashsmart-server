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
                    "077 123 4567",
                    "No. 45, Galle Road, Colombo 03",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    "2024/Plant/01"
            );
            RecyclingPlant plant2 = new RecyclingPlant(
                    "Happy Leaf",
                    "Ravija Salpitikorala",
                    "happy_leaf@gmail.com",
                    passwordEncoder.encode("password123"),
                    "071 234 5678",
                    "No. 12, Temple Road, Kandy",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    "2024/Plant/02"
            );
            RecyclingPlant plant3 = new RecyclingPlant(
                    "Renew Earth Center",
                    "Lakruwan Kasun",
                    "renew_earth_center@gmail.com",
                    passwordEncoder.encode("password123"),
                    "075 987 6543",
                    "No. 89, Station Road, Negombo",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    "2024/Plant/03"
            );
            RecyclingPlant plant4 = new RecyclingPlant(
                    "Planet Reclaim",
                    "Masha Wicky",
                    "planet_reclaim@gmail.com",
                    passwordEncoder.encode("password123"),
                    "076 555 1122",
                    "No. 210, Main Street, Jaffna",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    "2024/Plant/04"
            );
            RecyclingPlant plant5 = new RecyclingPlant(
                    "EverGreen Recovery",
                    "Rusara Wimalasena",
                    "evergreen_recovery@gmail.com",
                    passwordEncoder.encode("password123"),
                    "072 678 9012",
                    "No. 34/5, Hospital Road, Galle",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    "2024/Plant/05"
            );
            RecyclingPlant plant6 = new RecyclingPlant(
                    "Sustainergy Recycling",
                    "Pahasara Jayasuriya",
                    "sustainergy_recycling@gmail.com",
                    passwordEncoder.encode("password123"),
                    "074 345 6789",
                    "No. 18, High Level Road, Maharagama",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    "2024/Plant/06"
            );

            recyclingPlantRepository.saveAll(List.of(plant1, plant2, plant3, plant4, plant5, plant6));
        };
    }
}
