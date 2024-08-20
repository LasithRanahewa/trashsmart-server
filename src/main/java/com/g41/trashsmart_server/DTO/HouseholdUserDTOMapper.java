package com.g41.trashsmart_server.DTO;

import com.g41.trashsmart_server.Models.HouseholdUser;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class HouseholdUserDTOMapper implements Function<HouseholdUser, HouseholdUserDTO> {
    @Override
    public HouseholdUserDTO apply(HouseholdUser householdUser) {
        return new HouseholdUserDTO(
                householdUser.getId(),
                householdUser.getFirstName(),
                householdUser.getLastName(),
                householdUser.getEmail(),
                householdUser.getContactNo(),
                householdUser.getAddress(),
                householdUser.getRole(),
                householdUser.getProfileURL(),
                householdUser.getSuburb()
        );
    }
}
