package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.DTO.HouseholdUserDTO;
import com.g41.trashsmart_server.Models.HouseholdUser;
import com.g41.trashsmart_server.Repositories.HouseholdUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HouseholdUserService {
    private final HouseholdUserRepository householdUserRepository;

    @Autowired
    public HouseholdUserService(HouseholdUserRepository householdUserRepository) {
        this.householdUserRepository = householdUserRepository;
    }

    public List<HouseholdUserDTO> getHouseholdUsers() {
        List<HouseholdUser> householdUsers = householdUserRepository.findAll();
        List<HouseholdUserDTO> householdUserDTOS = new ArrayList<>();
        for(HouseholdUser householdUser: householdUsers) {
            HouseholdUserDTO householdUserDTO = new HouseholdUserDTO();
            householdUserDTO.setId(householdUser.getId());
            householdUserDTO.setFirstName(householdUser.getFirstName());
            householdUserDTO.setLastName(householdUser.getLastName());
            householdUserDTO.setEmail(householdUser.getEmail());
            householdUserDTO.setContactNo(householdUser.getContactNo());
            householdUserDTO.setAddress(householdUser.getAddress());
            householdUserDTO.setRole(householdUser.getRole());
            householdUserDTO.setProfileURL(householdUser.getProfileURL());
            householdUserDTO.setSuburb(householdUser.getSuburb());
            householdUserDTOS.add(householdUserDTO);
        }
        return householdUserDTOS;
    }

    public List<HouseholdUser> getHouseholdUsersAdmin() {
        return householdUserRepository.findAll();
    }

    public void addNewHouseholdUser(HouseholdUser householdUser) {
        Optional<HouseholdUser> householdUserOptional = householdUserRepository.findHouseholdUserByEmail(
                householdUser.getEmail()
        );
        if(householdUserOptional.isPresent()) {
            throw new IllegalStateException("Email Taken");
        }
        householdUserRepository.save(householdUser);
    }

    public void deleteHouseholdUser(Long userId) {
        boolean exists = householdUserRepository.existsById(userId);
        if(!exists) {
            throw new IllegalStateException("Household User with id " + userId + " does not exists");
        }
        householdUserRepository.deleteById(userId);
    }

    @Transactional
    public void updateHouseholdUser(HouseholdUser householdUser) {
        HouseholdUser householdUserToUpdate = householdUserRepository.findById(householdUser.getId()).orElseThrow(
                () -> new IllegalStateException("Household User with id " + householdUser.getId() + " does not exists")
        );
        if (householdUser.getFirstName() != null && !householdUser.getFirstName().isEmpty() &&
                !householdUserToUpdate.getFirstName().equals(householdUser.getFirstName())) {
            householdUser.setFirstName(householdUser.getFirstName());
        }
    }
}
