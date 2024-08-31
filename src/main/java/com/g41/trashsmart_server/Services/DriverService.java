package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.DTO.DriverDTO;
import com.g41.trashsmart_server.DTO.DriverDTOMapper;

import com.g41.trashsmart_server.Models.Driver;

import com.g41.trashsmart_server.Repositories.DriverRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final DriverDTOMapper driverDTOMapper;

    @Autowired
    public DriverService(DriverRepository driverRepository,
                         DriverDTOMapper driverDTOMapper) {
        this.driverRepository = driverRepository;
        this.driverDTOMapper = driverDTOMapper;
    }
    public DriverDTO getSpecificDriver(Long id) {
        Optional<Driver> driverOptional = driverRepository.findById(id);
        if(driverOptional.isEmpty()) {
            throw new IllegalStateException("Driver with id " + id + " does not exists");
        }
        return driverDTOMapper.apply(driverOptional.get());
    }
    
    public void updateDriver(Long id, Driver driver) {
        Driver driverToUpdate = driverRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Driver with id " + id + " does not exists")
        );
        if (driver.getFirstName() != null && !driver.getFirstName().isEmpty() &&
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
                    driver.getEmail(), false
            );
            if (driverOptional.isPresent()) {
                throw new IllegalStateException("Email Taken");
            }
            driverToUpdate.setEmail(driver.getEmail());
        }
        if (driver.getContactNo() != null && !driverToUpdate.getContactNo().equals(driver.getContactNo())) {
            driverToUpdate.setContactNo(driver.getContactNo());
        }
        if (driver.getAddress() != null && !driverToUpdate.getAddress().equals(driver.getAddress())) {
            driverToUpdate.setAddress(driver.getAddress());
        }
        if (driver.getProfileURL() != null && !driverToUpdate.getProfileURL().equals(driver.getProfileURL())) {
            driverToUpdate.setProfileURL(driver.getProfileURL());
        }
        if (driver.getDob() != null && !driverToUpdate.getDob().equals(driver.getDob())) {
            driverToUpdate.setDob(driver.getDob());
        }
        if (driver.getNic() != null && !driverToUpdate.getNic().equals(driver.getNic())) {
            driverToUpdate.setNic(driver.getNic());
        }
        driverRepository.save(driverToUpdate);
    }
    
    
    
}
