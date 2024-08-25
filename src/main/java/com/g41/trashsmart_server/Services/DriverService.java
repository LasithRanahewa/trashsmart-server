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
    
    
}
