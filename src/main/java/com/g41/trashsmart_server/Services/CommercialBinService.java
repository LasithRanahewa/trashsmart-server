package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.DTO.CommercialBinDTO;
import com.g41.trashsmart_server.DTO.CommercialBinDTOMapper;
import com.g41.trashsmart_server.Enums.BinStatus;
import com.g41.trashsmart_server.Models.CommercialBin;
import com.g41.trashsmart_server.Models.CommunalBin;
import com.g41.trashsmart_server.Models.HouseholdUser;
import com.g41.trashsmart_server.Models.Organization;
import com.g41.trashsmart_server.Repositories.CommercialBinRepository;
import com.g41.trashsmart_server.Repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommercialBinService {
    private final CommercialBinRepository commercialBinRepository;
    private final CommercialBinDTOMapper commercialBinDTOMapper;
    private final OrganizationRepository organizationRepository;

    @Autowired
    public CommercialBinService(CommercialBinRepository commercialBinRepository,
                                CommercialBinDTOMapper commercialBinDTOMapper, OrganizationRepository organizationRepository) {
        this.commercialBinRepository = commercialBinRepository;
        this.commercialBinDTOMapper = commercialBinDTOMapper;
        this.organizationRepository = organizationRepository;
    }


//    @Autowired
//    private SecurityConfig securityConfig;


    // Retrieve all registered commercial bin
    public List<CommercialBinDTO> getAllCommercialBins() {
        List<CommercialBin> CommercialBins = commercialBinRepository.findAllCommercialBinsUnFiltered();
        return CommercialBins.stream()
                .map(commercialBinDTOMapper)
                .collect(Collectors.toList());
    }


    // Retrieve all active commercial bin
    public List<CommercialBinDTO> getCommercialBins() {
        List<CommercialBin> CommercialBins = commercialBinRepository.findAllCommercialBins(false);
        return CommercialBins.stream()
                .map(commercialBinDTOMapper)
                .collect(Collectors.toList());
    }

    // Retrieve all logically deleted commercial bin
    public List<CommercialBinDTO> getDeletedCommercialBins() {
        List<CommercialBin> CommercialBins = commercialBinRepository.findAllCommercialBins(true);
        return CommercialBins.stream()
                .map(commercialBinDTOMapper)
                .collect(Collectors.toList());
    }


    // Retrieve all commercial bin details
    public List<CommercialBin> getCommercialBinsAdmin() {
        return commercialBinRepository.findAll();
    }


    // Retrieve a specific commercial bin given the id (logically active)
    public CommercialBinDTO getSpecificCommercialBin(Long id) {
        Optional<CommercialBin> CommercialBinOptional = commercialBinRepository.findCommercialBinById(id, false);

        if (CommercialBinOptional.isEmpty()) {
            throw new IllegalStateException("Commercial bin with id " + id + " does not exist");
        }
        return commercialBinDTOMapper.apply(CommercialBinOptional.get());
    }

    // Create a new commercial bin
    public void addNewCommercialBin(CommercialBin commercialBin, Long organizationId) {
        Optional<Organization> organizationOptional = organizationRepository.findById(organizationId);
        if (organizationOptional.isEmpty()) {
            throw new IllegalStateException("Organization with id " + organizationId + " does not exist");
        }
        Organization organization = organizationOptional.get();
        commercialBin.setOrganization(organization);
        commercialBinRepository.save(commercialBin);
    }

    // Logically delete a commercial bin from the system
    public void deleteCommercialBin(Long id) {
        Optional<CommercialBin> CommercialBinOptional = commercialBinRepository.findById(id);
        if (CommercialBinOptional.isEmpty()) {
            throw new IllegalStateException("Garbage truck with id " + id + " does not exist");
        }
        CommercialBin CommercialBinToDelete = CommercialBinOptional.get();
        CommercialBinToDelete.setDeleted(true);
        commercialBinRepository.save(CommercialBinToDelete);
    }


    // Permanently delete a commercial bin from the system
    public void deletePermanentCommercialBin(Long id) {
        boolean exists = commercialBinRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Commercial bin with id " + id + " does not exist");
        }
        commercialBinRepository.deleteById(id);
    }

    // Update commercial bin details
    public void updateCommercialBin(Long id, CommercialBin CommercialBin) {
        CommercialBin CommercialBinToUpdate = commercialBinRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Commercial bin with id " + id + " does not exist")
        );
        if (CommercialBin.getLongitude() != null &&
                !CommercialBinToUpdate.getLongitude().equals(CommercialBin.getLongitude())) {
            CommercialBinToUpdate.setLongitude(CommercialBin.getLongitude());
        }
        if (CommercialBin.getLatitude() != null &&
                !CommercialBinToUpdate.getLatitude().equals(CommercialBin.getLatitude())) {
            CommercialBinToUpdate.setLatitude(CommercialBin.getLatitude());
        }
        if (CommercialBin.getWasteType() != null &&
                !CommercialBinToUpdate.getWasteType().equals(CommercialBin.getWasteType())) {
            CommercialBinToUpdate.setWasteType(CommercialBin.getWasteType());
        }
        if (!CommercialBinToUpdate.getBinStatus().equals(CommercialBin.getBinStatus())) {
            CommercialBinToUpdate.setBinStatus(CommercialBin.getBinStatus());
        }
        commercialBinRepository.save(CommercialBinToUpdate);
    }

    // Create a new commercial bin for contractor
    public void createCommercialBin(CommercialBin commercialBin) {
        Optional<CommercialBin> commercialBinOptional = commercialBinRepository.findCommercialBinByAPIKey(
                commercialBin.getApiKey()
        );
        if (commercialBinOptional.isPresent()) {
            throw new IllegalStateException("API Key Taken");
        }
        commercialBinRepository.save(commercialBin);
    }

    // Assign a commercial bin to an organization
    public void assignCommercialBin(Long org_id, String apiKey) {
        Optional<CommercialBin> commercialBinOptional = commercialBinRepository.findCommercialBinByAPIKey(apiKey);
        if (commercialBinOptional.isEmpty()) {
            throw new IllegalStateException("Commercial Bin with API key " + apiKey + " does not exist");
        }
        Optional<Organization> organizationOptional = organizationRepository.findById(org_id);
        if (organizationOptional.isEmpty()) {
            throw new IllegalStateException("Organization with ID " + org_id + " does not exist");
        }
        CommercialBin commercialBin = commercialBinOptional.get();
        Organization organization = organizationOptional.get();

        commercialBin.setLatitude(organization.getLatitude());
        commercialBin.setLongitude(organization.getLongitude());
        commercialBin.setOrganization(organization);

        commercialBinRepository.save(commercialBin);
    }

    public CommercialBin updatefilllevel(String apiKey, CommercialBin updatedData) {
        Optional<CommercialBin> optionalCommercialBin = commercialBinRepository.findCommercialBinByAPIKey(apiKey);
        if (optionalCommercialBin.isEmpty()) {
            throw new IllegalStateException("No bin with API Key");
        }
        CommercialBin exsistingcommercialbin = optionalCommercialBin.get();
        if (updatedData.getFillLevel() != null) {
            exsistingcommercialbin.setFillLevel(updatedData.getFillLevel());
            exsistingcommercialbin.setBinStatus(determineBinStatus(updatedData.getFillLevel())); // Optional logic
        }
        exsistingcommercialbin.setLastCollectionDate(LocalDate.now()); // Automatically update
        return commercialBinRepository.save(exsistingcommercialbin);
    }

    private BinStatus determineBinStatus(Double fillLevel) {
        // Ensure fillLevel is within 0-100% range
        if (fillLevel == null || fillLevel < 0) {
            fillLevel = 0.0;
        } else if (fillLevel > 100) {
            fillLevel = 100.0;
        }

        // Logic to determine bin status based on fill level
        if (fillLevel == 0) {
            return BinStatus.EMPTY;
        } else if (fillLevel > 0 && fillLevel <= 50) {
            return BinStatus.NORMAL;
        } else if (fillLevel > 50 && fillLevel < 100) {
            return BinStatus.ALMOST_FULL;
        } else if (fillLevel == 100) {
            return BinStatus.FULL;
        }

        // Default fallback (shouldn't happen)
        return BinStatus.EMPTY;
    }
}