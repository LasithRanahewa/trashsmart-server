package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.Models.Driver;
import com.g41.trashsmart_server.Models.HouseholdUser;
import com.g41.trashsmart_server.Models.Contractor;
import com.g41.trashsmart_server.Models.Organization;
import com.g41.trashsmart_server.Models.RecyclingPlant;
import com.g41.trashsmart_server.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private HouseholdUserRepository householdUserRepository;

    @Autowired
    private ContractorRepository contractorRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private RecyclingPlantRepository recyclingPlantRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        HouseholdUser householdUser = householdUserRepository.findHouseholdUserByEmail(email).orElse(null);
        if (householdUser != null) {
            return householdUser;
        }

        Contractor contractor = contractorRepository.findContractorByEmail(email).orElse(null);
        if (contractor != null) {
            return contractor;
        }

        Driver driver = driverRepository.findDriverByEmail(email).orElse(null);
        if (driver != null) {
            return driver;
        }

        Organization organization = organizationRepository.findOrganizationByEmail(email).orElse(null);
        if (organization != null) {
            return organization;
        }

        RecyclingPlant recyclingPlant = recyclingPlantRepository.findByEmail(email).orElse(null);
        if (recyclingPlant != null) {
            return recyclingPlant;
        }





        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}