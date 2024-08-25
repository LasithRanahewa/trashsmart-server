package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.DTO.HouseholdUserDTO;
import com.g41.trashsmart_server.DTO.HouseholdUserDTOMapper;
import com.g41.trashsmart_server.Enums.Role;
import com.g41.trashsmart_server.Models.HouseholdUser;
import com.g41.trashsmart_server.Repositories.HouseholdUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HouseholdUserService {
    private final HouseholdUserRepository householdUserRepository;
    private final HouseholdUserDTOMapper householdUserDTOMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public HouseholdUserService(HouseholdUserRepository householdUserRepository,
                                HouseholdUserDTOMapper householdUserDTOMapper) {
        this.householdUserRepository = householdUserRepository;
        this.householdUserDTOMapper = householdUserDTOMapper;
    }

    // Retrieve all active household users
    public List<HouseholdUserDTO> getHouseholdUsers() {
        List<HouseholdUser> householdUsers = householdUserRepository.findAllHouseholdUsers(false);
        return householdUsers.stream()
                .map(householdUserDTOMapper)
                .collect(Collectors.toList());
    }

    // Retrieve all logically deleted household users
    public List<HouseholdUserDTO> getDeletedHouseholdUsers() {
        List<HouseholdUser> householdUsers = householdUserRepository.findAllHouseholdUsers(true);
        return householdUsers.stream()
                .map(householdUserDTOMapper)
                .collect(Collectors.toList());
    }

    // Retrieve all registered household users
    public List<HouseholdUserDTO> getAllHouseholdUsers() {
        List<HouseholdUser> householdUsers = householdUserRepository.findAllHouseholdUsersUnFiltered();
        return householdUsers.stream()
                .map(householdUserDTOMapper)
                .collect(Collectors.toList());
    }

    // Retrieve all the user details (Admin Privilege)
    public List<HouseholdUser> getHouseholdUsersAdmin() {
        return householdUserRepository.findAll();
    }

    // Retrieve a specific household user given the id
    public HouseholdUserDTO getSpecificHouseholdUser(Long id) {
        Optional<HouseholdUser> householdUserOptional = householdUserRepository.findById(id);
        if(householdUserOptional.isEmpty()) {
            throw new IllegalStateException("Household User with id " + id + " does not exists");
        }
        return householdUserDTOMapper.apply(householdUserOptional.get());
    }

    // Create a new household user
    public void addNewHouseholdUser(HouseholdUser householdUser) {
        householdUser.setPassword(passwordEncoder.encode(householdUser.getPassword()));
        householdUser.setRole(Role.HOUSEHOLD_USER);

        Optional<HouseholdUser> householdUserOptional = householdUserRepository.findHouseholdUserByEmail(
                householdUser.getEmail()
        );
        if(householdUserOptional.isPresent()) {
            throw new IllegalStateException("Email Taken");
        }
        householdUserRepository.save(householdUser);
    }

    // Logically delete a household user from the system
    public void deleteHouseholdUser(Long userId) {
        Optional<HouseholdUser> householdUserOptional = householdUserRepository.findById(userId);
        if(householdUserOptional.isEmpty()) {
            throw new IllegalStateException("Household User with id " + userId + " does not exists");
        }
        HouseholdUser householdUserToDelete = householdUserOptional.get();
        householdUserToDelete.setDeleted(true);
        householdUserRepository.save(householdUserToDelete);
    }

    // Permanently delete a household user from the system
    public void deletePermanentHouseholdUser(Long userId) {
        boolean exists = householdUserRepository.existsById(userId);
        if(!exists) {
            throw new IllegalStateException("Household User with id " + userId + " does not exists");
        }
        householdUserRepository.deleteById(userId);
    }

    // Update household user details
    public void updateHouseholdUser(Long id, HouseholdUser householdUser) {
        HouseholdUser householdUserToUpdate = householdUserRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Household User with id " + id + " does not exists")
        );
        if (householdUser.getFirstName() != null && !householdUser.getFirstName().isEmpty() &&
                !householdUserToUpdate.getFirstName().equals(householdUser.getFirstName())) {
            householdUserToUpdate.setFirstName(householdUser.getFirstName());
        }
        if (householdUser.getLastName() != null && !householdUser.getLastName().isEmpty() &&
                !householdUserToUpdate.getLastName().equals(householdUser.getLastName())) {
            householdUserToUpdate.setLastName(householdUser.getLastName());
        }
        if (householdUser.getEmail() != null && !householdUser.getEmail().isEmpty() &&
                !householdUserToUpdate.getEmail().equals(householdUser.getEmail())) {
            Optional<HouseholdUser> householdUserOptional = householdUserRepository.findHouseholdUserByEmail(
                    householdUser.getEmail()
            );
            if(householdUserOptional.isPresent()) {
                throw new IllegalStateException("Email Taken");
            }
            householdUserToUpdate.setEmail(householdUser.getEmail());
        }
        if (householdUser.getPassword() != null && !householdUser.getPassword().isEmpty() &&
                !householdUserToUpdate.getPassword().equals(householdUser.getPassword())) {
            householdUserToUpdate.setPassword(householdUser.getPassword());
        }
        if (householdUser.getAddress() != null && !householdUser.getAddress().isEmpty() &&
                !householdUserToUpdate.getAddress().equals(householdUser.getAddress())) {
            householdUserToUpdate.setAddress(householdUser.getAddress());
        }
        if (householdUser.getContactNo() != null && !householdUser.getContactNo().isEmpty() &&
                !householdUserToUpdate.getContactNo().equals(householdUser.getContactNo())) {
            householdUserToUpdate.setContactNo(householdUser.getContactNo());
        }
        if (householdUser.getRole() != null && !householdUserToUpdate.getRole().equals(householdUser.getRole())) {
            householdUserToUpdate.setRole(householdUser.getRole());
        }
        if (householdUser.getProfileURL() != null && !householdUser.getProfileURL().isEmpty() &&
                !householdUserToUpdate.getProfileURL().equals(householdUser.getProfileURL())) {
            householdUserToUpdate.setProfileURL(householdUser.getProfileURL());
        }
        householdUserRepository.save(householdUserToUpdate);
    }
}
