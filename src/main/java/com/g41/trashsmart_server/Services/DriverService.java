package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.DTO.DriverDTO;
import com.g41.trashsmart_server.DTO.DriverDTOMapper;
import com.g41.trashsmart_server.Enums.Role;
import com.g41.trashsmart_server.Models.Driver;
import com.g41.trashsmart_server.Repositories.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final DriverDTOMapper driverDTOMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DriverService(DriverRepository driverRepository,
                                DriverDTOMapper driverDTOMapper) {
        this.driverRepository = driverRepository;
        this.driverDTOMapper = driverDTOMapper;

    }

    // Retrieve all registered drivers
    public List<DriverDTOMapper> getAllDrivers() {
        List<Driver> drivers = driverRepository.findAllDriversUnFiltered();
        return drivers.stream()
                .map(driverDTOMapper)
                .collect(Collectors.toList());
    }

    // Retrieve all active drivers
    public List<DriverDTOMapper> getDrivers() {
        List<Driver> drivers = driverRepository.findAllDrivers(false);
        return drivers.stream()
                .map(driverDTOMapper)
                .collect(Collectors.toList());
    }

    // Retrieve all logically deleted drivers
    public List<DriverDTOMapper> getDeletedDrivers() {
        List<Driver> drivers = driverRepository.findAllDrivers(true);
        return drivers.stream()
                .map(driverDTOMapper)
                .collect(Collectors.toList());
    }

    // Retrieve all driver details
    public List<Driver> getDriversAdmin() {
        return driverRepository.findAll();
    }


    // Retrieve a specific driver given the id (logically active)
    public DriverDTO getSpecificDriver(Long id) {
        Optional<Driver> driverOptional = driverRepository.findDriverById(id, false);

        if(driverOptional.isEmpty()) {
            throw new IllegalStateException("Driver truck with id " + id + " does not exist");
        }
        return driverDTOMapper.apply(driverOptional.get());
    }

    // Create a new driver
    public void addNewDriver(Driver driver) {
        driver.setPassword(passwordEncoder.encode(driver.getPassword()));
        driver.setRole(Role.DRIVER);

        Optional<Driver> driverOptional = driverRepository.findDriverByEmail(driver.getEmail());
        if(driverOptional.isPresent()) {
            throw new IllegalStateException("Email Taken");
        }
        driverRepository.save(driver);
    }

    // Logically delete a driver from the system
    public void deleteDriver(Long id) {
        Optional<Driver> driverOptional = driverRepository.findById(id);
        if(driverOptional.isEmpty()) {
            throw new IllegalStateException("Driver with id " + id + " does not exist");
        }
        Driver driverToDelete = driverOptional.get();
        driverToDelete.setDeleted(true);
        driverRepository.save(driverToDelete);
    }


    // Permanently delete a driver from the system
    public void deletePermanentDriver(Long id) {
        boolean exists = driverRepository.existsById(id);
        if(!exists) {
            throw new IllegalStateException("Driver with id " + id + " does not exist");
        }
        driverRepository.deleteById(id);
    }

    // Update driver details
    public void updateDriver(Long id, Driver driver) {
        Driver driverToUpdate = driverRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Driver with id " + id + " does not exist")
        );
        if (driver.getLongestStreak() != null &&
                !driverToUpdate.getLongestStreak().equals(driver.getLongestStreak())) {
            driverToUpdate.setLongestStreak(driver.getLongestStreak());
        }
        if (driver.getCurrentStreak() != null &&
                !driverToUpdate.getCurrentStreak().equals(driver.getCurrentStreak())) {
            driverToUpdate.setCurrentStreak(driver.getCurrentStreak());
        }
        if (driver.getNumberOfHolidays() != null &&
                !driver.getNumberOfHolidays().equals(driver.getNumberOfHolidays())) {
            driverToUpdate.setNumberOfHolidays(driver.getNumberOfHolidays());
        }
        if (driver.getTotalCollections() != null &&
                !driverToUpdate.getTotalCollections().equals(driver.getTotalCollections())) {
            driverToUpdate.setTotalCollections(driver.getTotalCollections());
        }
        if (driver.getTotalActiveDays() != null &&
                !driverToUpdate.getTotalActiveDays().equals(driver.getTotalActiveDays())) {
            driverToUpdate.setTotalActiveDays(driver.getTotalActiveDays());
        }
        driverRepository.save(driverToUpdate);
    }
}
