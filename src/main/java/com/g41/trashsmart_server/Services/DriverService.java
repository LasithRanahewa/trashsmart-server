package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.DTO.DriverDTO;
import com.g41.trashsmart_server.DTO.DriverDTOMapper;
import com.g41.trashsmart_server.Enums.DispatchStatus;
import com.g41.trashsmart_server.Enums.Role;
import com.g41.trashsmart_server.Enums.Status;
import com.g41.trashsmart_server.Models.Dispatch;
import com.g41.trashsmart_server.Models.Driver;
import com.g41.trashsmart_server.Repositories.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
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
    @Transactional
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
                !driverToUpdate.getNumberOfHolidays().equals(driver.getNumberOfHolidays())) {
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
        if (driver.getStatus() != null &&
                !driverToUpdate.getStatus().equals(driver.getStatus())) {
            driverToUpdate.setStatus(driver.getStatus());
        }
        if (driver.getFirstName() != null &&
                !driverToUpdate.getFirstName().equals(driver.getFirstName())) {
            driverToUpdate.setFirstName(driver.getFirstName());
        }
        if (driver.getLastName() != null && !driver.getLastName().isEmpty() &&
                !driverToUpdate.getLastName().equals(driver.getLastName())) {
            driverToUpdate.setLastName(driver.getLastName());
        }
        if (driver.getEmail() != null && !driver.getEmail().isEmpty() &&
                !driverToUpdate.getEmail().equals(driver.getEmail())) {
            Optional<Driver> driverOptional = driverRepository.findDriverByEmail(
                    driver.getEmail()
            );
            if(driverOptional.isPresent()) {
                throw new IllegalStateException("Email Taken");
            }
            driverToUpdate.setEmail(driver.getEmail());
        }
        if (driver.getPassword() != null &&
                !driverToUpdate.getPassword().equals(driver.getPassword())) {
            driverToUpdate.setPassword(driver.getPassword());
        }
        if (driver.getAddress() != null &&
                !driverToUpdate.getAddress().equals(driver.getAddress())) {
            driverToUpdate.setAddress(driver.getAddress());
        }
        if (driver.getContactNo() != null &&
                !driverToUpdate.getContactNo().equals(driver.getContactNo())) {
            driverToUpdate.setContactNo(driver.getContactNo());
        }
        if (driver.getRole() != null && !driverToUpdate.getRole().equals(driver.getRole())) {
            driverToUpdate.setRole(driver.getRole());
        }
        if (driver.getProfileURL() != null && !driver.getProfileURL().isEmpty() &&
                !driverToUpdate.getProfileURL().equals(driver.getProfileURL())) {
            driverToUpdate.setProfileURL(driver.getProfileURL());
        }
        if (driver.getDob() != null &&
                !driverToUpdate.getDob().equals(driver.getDob())) {
            driverToUpdate.setDob(driver.getDob());
        }
        if (driver.getNic() != null &&
                !driverToUpdate.getNic().equals(driver.getNic())) {
            driverToUpdate.setNic(driver.getNic());
        }

        driverRepository.save(driverToUpdate);
    }

    // Get dispatches given a driver id
    public List<Dispatch> getDriverDispatches(Long driverId) {
        // Retrieve the driver
        Driver driver = driverRepository.findById(driverId).orElseThrow(
                () -> new IllegalStateException("Driver with id " + driverId + " does not exist")
        );

        // Filter dispatches by date and status
        List<Dispatch> filteredDispatches = driver.getDispatches().stream()
                .filter(dispatch -> dispatch.getDateTime().toLocalDate().equals(LocalDate.now()))
                .collect(Collectors.toList());

        if (filteredDispatches.isEmpty()) {
            throw new IllegalStateException("No dispatches found for driver with id " + driverId + " on the current date.");
        }

        return filteredDispatches;
    }
}
