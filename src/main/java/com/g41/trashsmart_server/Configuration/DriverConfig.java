package com.g41.trashsmart_server.Configuration;

import com.g41.trashsmart_server.Enums.Status;
import com.g41.trashsmart_server.Models.Driver;
import com.g41.trashsmart_server.Repositories.DriverRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DriverConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner driverCommandLineRunner(DriverRepository driverRepository) {
        return args -> {
            Driver Rusara = new Driver(
                    "Rusara",
                    "Wimalasena",
                    passwordEncoder.encode("password123"),
                    "0712990638",
                    "35, Reid Avenue, Colombo 07",
                    "rusara.wimalasena123@gmail.com",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    LocalDate.of(1995, 12, 12),
                    "200168686830"
            );

            Driver Ravija = new Driver(
                    "Ravija",
                    "Salpitikorala",
                    passwordEncoder.encode("password123"),
                    "0712990639",
                    "36, Reid Avenue, Colombo 07",
                    "ravija@gmail.com",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    LocalDate.of(1995, 12, 12),
                    "200168686831"
            );

            Driver Masha = new Driver(
                    "Masha",
                    "Wickramasinghe",
                    passwordEncoder.encode("password123"),
                    "0712990640",
                    "37, Reid Avenue, Colombo 07",
                    "masha@gmail.com",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    LocalDate.of(1995, 12, 12),
                    "200168686832"
            );

            Driver Lasith = new Driver(
                    "Lasith",
                    "Ranahewa",
                    passwordEncoder.encode("password123"),
                    "0712990641",
                    "38, Reid Avenue, Colombo 07",
                    "lasith@gmail.com",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    LocalDate.of(1995, 12, 12),
                    "200168686833"
            );

            Driver Dasun = new Driver(
                    "Dasun",
                    "Srikantha",
                    passwordEncoder.encode("password123"),
                    "0712990642",
                    "39, Reid Avenue, Colombo 07",
                    "dasun@gmail.com",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    LocalDate.of(1995, 12, 12),
                    "200168686834"
            );

            Driver Lakruwan = new Driver(
                    "Lakruwan",
                    "Kasun",
                    passwordEncoder.encode("password123"),
                    "0712990643",
                    "35, Reid Avenue, Colombo 07",
                    "lakruwan@gmail.com",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    LocalDate.of(1995, 12, 12),
                    "200168686835"
            );

            Driver driver1 = new Driver(
                    "Tharindu",
                    "Perera",
                    passwordEncoder.encode("password123"),
                    "0771234567",
                    "12, Galle Road, Colombo 03",
                    "tharindu.perera@gmail.com",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    LocalDate.of(1990, 5, 18),
                    "891234567V"
            );

            Driver driver2 = new Driver(
                    "Nimal",
                    "Wijesinghe",
                    passwordEncoder.encode("password123"),
                    "0712349876",
                    "45, Kandy Road, Kegalle",
                    "nimal.wije@yahoo.com",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    LocalDate.of(1988, 11, 23),
                    "880112345X"
            );

            Driver driver3 = new Driver(
                    "Kavindu",
                    "Fernando",
                    passwordEncoder.encode("password123"),
                    "0789012345",
                    "67, Mahinda Place, Gampaha",
                    "kavindu.fernando@hotmail.com",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    LocalDate.of(1992, 7, 10),
                    "920710234V"
            );

            Driver driver4 = new Driver(
                    "Saman",
                    "De Silva",
                    passwordEncoder.encode("password123"),
                    "0769876543",
                    "21, Temple Road, Ratnapura",
                    "saman.desilva@gmail.com",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    LocalDate.of(1985, 9, 15),
                    "850915678X"
            );

            Driver driver5 = new Driver(
                    "Chathura",
                    "Jayasinghe",
                    passwordEncoder.encode("password123"),
                    "0756785432",
                    "33, Lake Road, Nugegoda",
                    "chathura.jaya@hotmail.com",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    LocalDate.of(1993, 3, 30),
                    "930330123V"
            );

            Driver driver6 = new Driver(
                    "Harsha",
                    "Gunawardena",
                    passwordEncoder.encode("password123"),
                    "0743456789",
                    "98, Flower Road, Colombo 07",
                    "harsha.gun@gmail.com",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    LocalDate.of(1997, 1, 5),
                    "970105678X"
            );

            Driver driver7 = new Driver(
                    "Amali",
                    "Kumari",
                    passwordEncoder.encode("password123"),
                    "0774321987",
                    "50, High Level Road, Maharagama",
                    "amali.kumari@gmail.com",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    LocalDate.of(1996, 4, 20),
                    "960420123V"
            );

            Driver driver8 = new Driver(
                    "Nuwan",
                    "Dias",
                    passwordEncoder.encode("password123"),
                    "0788765432",
                    "72, Peradeniya Road, Kandy",
                    "nuwan.dias@yahoo.com",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    LocalDate.of(1991, 8, 8),
                    "910808765V"
            );

            Driver driver9 = new Driver(
                    "Dilini",
                    "Weerasinghe",
                    passwordEncoder.encode("password123"),
                    "0713456721",
                    "17, Beach Road, Negombo",
                    "dilini.w@yahoo.com",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    LocalDate.of(1989, 6, 25),
                    "890625123V"
            );

            Driver driver10 = new Driver(
                    "Mahesh",
                    "Bandara",
                    passwordEncoder.encode("password123"),
                    "0765439876",
                    "5, Templers Road, Mount Lavinia",
                    "mahesh.b@gmail.com",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    LocalDate.of(1994, 2, 14),
                    "940214567X"
            );

            Driver driver11 = new Driver(
                    "Ruwan",
                    "Ratnayake",
                    passwordEncoder.encode("password123"),
                    "0743216789",
                    "81, Galle Face Road, Colombo 01",
                    "ruwan.ratnayake@gmail.com",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    LocalDate.of(1998, 10, 18),
                    "981018345V"
            );

            Driver driver12 = new Driver(
                    "Nadeeka",
                    "Ekanayake",
                    passwordEncoder.encode("password123"),
                    "0751234567",
                    "39, Dharmapala Mawatha, Colombo 07",
                    "nadeeka.e@gmail.com",
                    "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    LocalDate.of(1993, 12, 9),
                    "931209678X"
            );


            driverRepository.saveAll(List.of(
                    Rusara,
                    Ravija,
                    Masha,
                    Lasith,
                    Dasun,
                    Lakruwan,
                    driver1,
                    driver2,
                    driver3,
                    driver4,
                    driver5,
                    driver6,
                    driver7,
                    driver8,
                    driver9,
                    driver10,
                    driver11,
                    driver12
            ));
        };
    }
}
