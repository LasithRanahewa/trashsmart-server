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
    @Transactional
    public DriverDTO updateDriver(Long id, DriverDTO driverDTO) {
        Driver driver = driverRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("Driver with id " + id + " does not exist"));

        if (driverDTO.getFirstName() != null) {
            driver.setFirstName(driverDTO.getFirstName());
        }

        if (driverDTO.getLastName() != null) {
            driver.setLastName(driverDTO.getLastName());
        }

        if (driverDTO.getEmail() != null) {
            driver.setEmail(driverDTO.getEmail());
        }

        if (driverDTO.getContactNo() != null) {
            driver.setContactNo(driverDTO.getContactNo());
        }

        if (driverDTO.getAddress() != null) {
            driver.setAddress(driverDTO.getAddress());
        }

        

        if (driverDTO.getProfileURL() != null) {
            driver.setProfileURL(driverDTO.getProfileURL());
        }

        

        // Save the updated driver back to the database
        driverRepository.save(driver);

        // Return the updated DriverDTO
        return driverDTOMapper.apply(driver);
    }
    
    
}
