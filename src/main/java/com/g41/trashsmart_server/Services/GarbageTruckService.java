package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.Configuration.SecurityConfig;
import com.g41.trashsmart_server.DTO.GarbageTruckDTO;
import com.g41.trashsmart_server.DTO.GarbageTruckDTOMapper;
import com.g41.trashsmart_server.Models.GarbageTruck;
import com.g41.trashsmart_server.Repositories.GarbageTruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GarbageTruckService {
    private final GarbageTruckRepository garbagetruckRepository;
    private final GarbageTruckDTOMapper garbagetruckDTOMapper;

    @Autowired
    public GarbageTruckService(GarbageTruckRepository garbagetruckRepository,
                               GarbageTruckDTOMapper garbagetruckDTOMapper) {
        this.garbagetruckRepository = garbagetruckRepository;
        this.garbagetruckDTOMapper = garbagetruckDTOMapper;
    }

//    @Autowired
//    private SecurityConfig securityConfig;

    // Retrieve all registered garbage trucks
    public List<GarbageTruckDTO> getAllGarbageTrucks() {
        List<GarbageTruck> garbagetrucks = garbagetruckRepository.findAllGarbageTrucksGUnFiltered();
        return garbagetrucks.stream()
                .map(garbagetruckDTOMapper)
                .collect(Collectors.toList());
    }

    // Retrieve all active garbage trucks
    public List<GarbageTruckDTO> getGarbageTrucks() {
        List<GarbageTruck> garbagetrucks = garbagetruckRepository.findAllGarbageTrucks(false);
        return garbagetrucks.stream()
                .map(garbagetruckDTOMapper)
                .collect(Collectors.toList());
    }

    // Retrieve all logically deleted garbage trucks
    public List<GarbageTruckDTO> getDeletedGarbageTrucks() {
        List<GarbageTruck> garbagetrucks = garbagetruckRepository.findAllGarbageTrucks(true);
        return garbagetrucks.stream()
                .map(garbagetruckDTOMapper)
                .collect(Collectors.toList());
    }

    // Retrieve all truck details
    public List<GarbageTruck> getGarbageTrucksAdmin() {
        return garbagetruckRepository.findAll();
    }

    // Retrieve a specific garbage truck given the id (logically active)
    public GarbageTruckDTO getSpecificGarbageTruck(Long id) {
        Optional<GarbageTruck> garbagetruckOptional = garbagetruckRepository.findGarbageTruckById(id, false);
        if(garbagetruckOptional.isEmpty()) {
            throw new IllegalStateException("Garbage truck with id " + id + " does not exist");
        }
        return garbagetruckDTOMapper.apply(garbagetruckOptional.get());
    }

    // Create a new garbage truck
    public void addNewGarbageTruck(GarbageTruck garbagetruck) {
        Optional<GarbageTruck> garbagetruckOptional = garbagetruckRepository.findGarbageTruckByLicensePlate(
                garbagetruck.getLicencePlateNo()
        );
        if(garbagetruckOptional.isPresent()) {
            throw new IllegalStateException("Garbage truck already exists");
        }
        garbagetruckRepository.save(garbagetruck);
    }

    // Logically delete a garbage truck from the system
    public void deleteGarbageTruck(Long id) {
        Optional<GarbageTruck> garbagetruckOptional = garbagetruckRepository.findById(id);
        if(garbagetruckOptional.isEmpty()) {
            throw new IllegalStateException("Garbage truck with id " + id + " does not exist");
        }
        GarbageTruck garbagetruckToDelete = garbagetruckOptional.get();
        garbagetruckToDelete.setDeleted(true);
        garbagetruckRepository.save(garbagetruckToDelete);
    }

    // Permanently delete a garbage truck from the system
    public void deletePermanentGarbageTruck(Long id) {
        boolean exists = garbagetruckRepository.existsById(id);
        if(!exists) {
            throw new IllegalStateException("Garbage truck with id " + id + " does not exist");
        }
        garbagetruckRepository.deleteById(id);
    }

    // Update garbage truck details
    public void updateGarbageTruck(Long id, GarbageTruck garbagetruck) {
        GarbageTruck garbagetruckToUpdate = garbagetruckRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Garbage truck with id " + id + " does not exist")
        );
        if (garbagetruck.getLicencePlateNo() != null && !garbagetruck.getLicencePlateNo().isEmpty() &&
                !garbagetruckToUpdate.getLicencePlateNo().equals(garbagetruck.getLicencePlateNo())) {
            garbagetruckToUpdate.setLicencePlateNo(garbagetruck.getLicencePlateNo());
        }
        if (garbagetruck.getMaxVolume() != null &&
                !garbagetruckToUpdate.getMaxVolume().equals(garbagetruck.getMaxVolume())) {
            garbagetruckToUpdate.setMaxVolume(garbagetruck.getMaxVolume());
        }
        if (garbagetruck.getTruckStatus() != null &&
                !garbagetruckToUpdate.getTruckStatus().equals(garbagetruck.getTruckStatus())) {
            garbagetruckToUpdate.setTruckStatus(garbagetruck.getTruckStatus());
        }
        if (garbagetruck.getMileage() != null &&
                !garbagetruckToUpdate.getMileage().equals(garbagetruck.getMileage())) {
            garbagetruckToUpdate.setMileage(garbagetruck.getMileage());
        }
        garbagetruckRepository.save(garbagetruckToUpdate);
    }
}
