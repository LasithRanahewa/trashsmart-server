package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.Configuration.SMTPGmailSenderService;
import com.g41.trashsmart_server.Configuration.SecurityConfig;
import com.g41.trashsmart_server.DTO.RecyclingPlantDTO;
import com.g41.trashsmart_server.DTO.RecyclingPlantDTOMapper;
import com.g41.trashsmart_server.Enums.Role;
import com.g41.trashsmart_server.Models.RecyclingPlant;
import com.g41.trashsmart_server.Repositories.RecyclingPlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecyclingPlantService {
    private final RecyclingPlantRepository recyclingPlantRepository;
    private final RecyclingPlantDTOMapper recyclingPlantDTOMapper;

    @Autowired
    private SMTPGmailSenderService smtpGmailSenderService;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RecyclingPlantService(RecyclingPlantRepository recyclingPlantRepository, RecyclingPlantDTOMapper recyclingPlantDTOMapper) {
        this.recyclingPlantRepository = recyclingPlantRepository;
        this.recyclingPlantDTOMapper = recyclingPlantDTOMapper;
    }

    public RecyclingPlant addRecyclingPlant(RecyclingPlant recyclingPlant) {
        Optional<RecyclingPlant> existingPlantByBRN = recyclingPlantRepository.findByBRN(recyclingPlant.getBRN());
        Optional<RecyclingPlant> existingByEmail = recyclingPlantRepository.findByEmail(recyclingPlant.getEmail());

        if (recyclingPlant.getEmail().isEmpty()) {
            throw new IllegalStateException("Email is essential.");
        }

        // Check if the recycling plant already exists by BRN or email
        if (existingByEmail.isPresent()) {
            throw new IllegalStateException("Recycling Plant with email " + recyclingPlant.getEmail() + " already exists.");
        }
        if (existingPlantByBRN.isPresent()) {
            throw new IllegalStateException("Recycling Plant with BRN " + recyclingPlant.getBRN() + " already exists.");
        }

        if (recyclingPlant.getPassword() == null || recyclingPlant.getPassword().isEmpty()) {
            recyclingPlant.setPassword(securityConfig.generateRandomPassword(10));

            String subject = "TrashSmart Account Created";
            String body = "Hello, " + recyclingPlant.getFirstName() + "!\n" +
                    "Your TrashSmart account has been created successfully.\n" +
                    "Your login credentials are as follows:\n" +
                    "Email: " + recyclingPlant.getEmail() + "\n" +
                    "Password: " + recyclingPlant.getPassword() + "\n" +
                    "Please change your password after logging in.\n" +
                    "Thank you for choosing TrashSmart!";
            smtpGmailSenderService.sendEmail(recyclingPlant.getEmail(), subject, body);
        }

        recyclingPlant.setRole(Role.RECYCLING_PLANT);

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


    public void updateRecyclingPlant(Long plantId, RecyclingPlant recyclingPlant) {
        Optional<RecyclingPlant> recyclingPlantOptional = recyclingPlantRepository.findById(plantId);

        if (recyclingPlantOptional.isEmpty()) {
            throw new IllegalStateException("Recycling Plant with ID " + plantId + " does not exist.");
        }

        RecyclingPlant existingPlant = recyclingPlantOptional.get();

        // Check for email conflict if the email is being updated
        if (recyclingPlant.getEmail() != null && !recyclingPlant.getEmail().equals(existingPlant.getEmail())) {
            Optional<RecyclingPlant> existingByEmail = recyclingPlantRepository.findByEmail(recyclingPlant.getEmail());
            if (existingByEmail.isPresent()) {
                throw new IllegalStateException("Recycling Plant with email " + recyclingPlant.getEmail() + " already exists.");
            }
            existingPlant.setEmail(recyclingPlant.getEmail());
        }

        // Check for BRN conflict if the BRN is being updated
        if (recyclingPlant.getBRN() != null && !recyclingPlant.getBRN().equals(existingPlant.getBRN())) {
            Optional<RecyclingPlant> existingByBRN = recyclingPlantRepository.findByBRN(recyclingPlant.getBRN());
            if (existingByBRN.isPresent()) {
                throw new IllegalStateException("Recycling Plant with BRN " + recyclingPlant.getBRN() + " already exists.");
            }
            existingPlant.setBRN(recyclingPlant.getBRN());
        }

        //Update other fields if provided
        if (recyclingPlant.getFirstName() != null) {
            existingPlant.setFirstName(recyclingPlant.getFirstName());
        }
        if (recyclingPlant.getLastName() != null) {
            existingPlant.setLastName(recyclingPlant.getLastName());
        }
        if (recyclingPlant.getContactNo() != null) {
            existingPlant.setContactNo(recyclingPlant.getContactNo());
        }
        if (recyclingPlant.getAddress() != null) {
            existingPlant.setAddress(recyclingPlant.getAddress());
        }
        if (recyclingPlant.getProfileURL() != null) {
            existingPlant.setProfileURL(recyclingPlant.getProfileURL());
        }
        if (recyclingPlant.getPassword() != null && !existingPlant.getPassword().equals(recyclingPlant.getPassword())) {
            existingPlant.setPassword(passwordEncoder.encode(recyclingPlant.getPassword()));
        }

        recyclingPlantRepository.save(existingPlant);
    }
}
