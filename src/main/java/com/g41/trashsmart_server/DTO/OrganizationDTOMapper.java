package com.g41.trashsmart_server.DTO;

import com.g41.trashsmart_server.Models.Organization;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class OrganizationDTOMapper implements Function<Organization, OrganizationDTO> {
    @Override
    public OrganizationDTO apply(Organization organization) {
        return new OrganizationDTO(
                organization.getId(),
                organization.getFirstName(),
                organization.getLastName(),
                organization.getEmail(),
                organization.getContactNo(),
                organization.getAddress(),
                organization.getRole(),
                organization.getProfileURL(),
                organization.getWeeklyWaste(),
                organization.getTotalWaste(),
                organization.getRecyclableWaste(),
                organization.getScale(),
                organization.getOrgType(),
                organization.getContractStartDate(),
                organization.getContractEndDate(),
                organization.getLatitude(),
                organization.getLongitude(),
                organization.getCommercialBins(),
                organization.getWasteCollectionRequests()
        );
    }
}
