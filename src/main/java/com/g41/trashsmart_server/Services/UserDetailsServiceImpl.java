package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.Models.HouseholdUser;
import com.g41.trashsmart_server.Models.Contractor;
import com.g41.trashsmart_server.Repositories.HouseholdUserRepository;
import com.g41.trashsmart_server.Repositories.ContractorRepository;
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

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}