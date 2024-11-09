package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.DTO.RecyclingPlantDTO;
import com.g41.trashsmart_server.DTO.RecyclingPlantDTOMapper;
import com.g41.trashsmart_server.Models.RecyclingPlant;
import com.g41.trashsmart_server.Repositories.RecyclingPlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecyclingPlantService {
    private final RecyclingPlantRepository recyclingPlantRepository;
    private final RecyclingPlantDTOMapper recyclingPlantDTOMapper;

    @Autowired
    public RecyclingPlantService(RecyclingPlantRepository recyclingPlantRepository, RecyclingPlantDTOMapper recyclingPlantDTOMapper) {
        this.recyclingPlantRepository = recyclingPlantRepository;
        this.recyclingPlantDTOMapper = recyclingPlantDTOMapper;
    }

    public RecyclingPlant addRecyclingPlant(RecyclingPlant recyclingPlant) {
        Optional<RecyclingPlant> existingPlantByBRN = recyclingPlantRepository.findByBRN(recyclingPlant.getBRN());
        Optional<RecyclingPlant> existingByEmail = recyclingPlantRepository.findByEmail(recyclingPlant.getEmail());

        // Check if the recycling plant already exists by BRN or email
        if (existingByEmail.isPresent()) {
            throw new IllegalStateException("Recycling Plant with email " + recyclingPlant.getEmail() + " already exists.");
        }
        if (existingPlantByBRN.isPresent()) {
            throw new IllegalStateException("Recycling Plant with BRN " + recyclingPlant.getBRN() + " already exists.");
        }

        return recyclingPlantRepository.save(recyclingPlant);
    }


    public List<RecyclingPlantDTO> getAllRecyclingPlants() {
        List<RecyclingPlant> recyclingPlants = recyclingPlantRepository.findAllRecyclingPlants();
        return recyclingPlants.stream()
                .map(recyclingPlantDTOMapper)
                .collect(Collectors.toList());
    }

    public List<RecyclingPlantDTO> getActiveRecyclingPlants() {
        List<RecyclingPlant> recyclingPlants = recyclingPlantRepository.findRecyclingPlantsFiltered(false);
        return recyclingPlants.stream()
                .map(recyclingPlantDTOMapper)
                .collect(Collectors.toList());
    }

    public List<RecyclingPlantDTO> getDeletedRecyclingPlants() {
        List<RecyclingPlant> recyclingPlants = recyclingPlantRepository.findRecyclingPlantsFiltered(true);
        return recyclingPlants.stream()
                .map(recyclingPlantDTOMapper)
                .collect(Collectors.toList());
    }

    public RecyclingPlantDTO getSpecificRecyclingPlant(Long plantId) {
        Optional<RecyclingPlant> recyclingPlantOptional = recyclingPlantRepository.findById(plantId);
        
        if (recyclingPlantOptional.isEmpty()) {
            throw new IllegalStateException("Recycling Plant with ID " + plantId + " does not exists.");
        }
        
        return recyclingPlantDTOMapper.apply(recyclingPlantOptional.get());
    }

    public void deleteRecyclingPlant(Long plantId) {
        Optional<RecyclingPlant> recyclingPlantOptional = recyclingPlantRepository.findById(plantId);

        if (recyclingPlantOptional.isEmpty()) {
            throw new IllegalStateException("Recycling Plant with ID " + plantId + " does not exists.");
        }

        RecyclingPlant recyclingPlantToDelete = recyclingPlantOptional.get();
        recyclingPlantToDelete.setDeleted(true);
        recyclingPlantRepository.save(recyclingPlantToDelete);
    }


    public RecyclingPlantDTO updateRecyclingPlant(Long plantId, RecyclingPlantDTO updateDTO) {
        Optional<RecyclingPlant> recyclingPlantOptional = recyclingPlantRepository.findById(plantId);

        if (recyclingPlantOptional.isEmpty()) {
            throw new IllegalStateException("Recycling Plant with ID " + plantId + " does not exist.");
        }

        RecyclingPlant existingPlant = recyclingPlantOptional.get();

        // Check for email conflict if the email is being updated
        if (updateDTO.getEmail() != null && !updateDTO.getEmail().equals(existingPlant.getEmail())) {
            Optional<RecyclingPlant> existingByEmail = recyclingPlantRepository.findByEmail(updateDTO.getEmail());
            if (existingByEmail.isPresent()) {
                throw new IllegalStateException("Recycling Plant with email " + updateDTO.getEmail() + " already exists.");
            }
            existingPlant.setEmail(updateDTO.getEmail());
        }

        // Check for BRN conflict if the BRN is being updated
        if (updateDTO.getBRN() != null && !updateDTO.getBRN().equals(existingPlant.getBRN())) {
            Optional<RecyclingPlant> existingByBRN = recyclingPlantRepository.findByBRN(updateDTO.getBRN());
            if (existingByBRN.isPresent()) {
                throw new IllegalStateException("Recycling Plant with BRN " + updateDTO.getBRN() + " already exists.");
            }
            existingPlant.setBRN(updateDTO.getBRN());
        }

        //Update other fields if provided
        if (updateDTO.getFirstName() != null) {
            existingPlant.setFirstName(updateDTO.getFirstName());
        }
        if (updateDTO.getLastName() != null) {
            existingPlant.setLastName(updateDTO.getLastName());
        }
        if (updateDTO.getContactNo() != null) {
            existingPlant.setContactNo(updateDTO.getContactNo());
        }
        if (updateDTO.getAddress() != null) {
            existingPlant.setAddress(updateDTO.getAddress());
        }
        if (updateDTO.getProfileURL() != null) {
            existingPlant.setProfileURL(updateDTO.getProfileURL());
        }

        RecyclingPlant updatedPlant = recyclingPlantRepository.save(existingPlant);
        return recyclingPlantDTOMapper.apply(updatedPlant);
    }
}
