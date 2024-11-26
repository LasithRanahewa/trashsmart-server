package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.Models.Driver;
import com.g41.trashsmart_server.Models.HouseholdUser;
import com.g41.trashsmart_server.Models.Contractor;
import com.g41.trashsmart_server.Models.Organization;
import com.g41.trashsmart_server.Repositories.DriverRepository;
import com.g41.trashsmart_server.Repositories.HouseholdUserRepository;
import com.g41.trashsmart_server.Repositories.ContractorRepository;
import com.g41.trashsmart_server.Repositories.OrganizationRepository;
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





        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}