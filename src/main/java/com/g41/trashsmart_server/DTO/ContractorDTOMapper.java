package com.g41.trashsmart_server.DTO;

import com.g41.trashsmart_server.Models.Contractor;
import com.g41.trashsmart_server.Models.HouseholdUser;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ContractorDTOMapper implements Function<Contractor, ContractorDTO> {
    @Override
    public ContractorDTO apply(Contractor contractor) {
        return new ContractorDTO(
                contractor.getId(),
                contractor.getFirstName(),
                contractor.getLastName(),
                contractor.getEmail(),
                contractor.getRole(),
                contractor.getDob(),
                contractor.getNic()
        );
    }
}
