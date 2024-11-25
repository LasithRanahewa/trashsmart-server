package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.DTO.CommercialBinDTO;
import com.g41.trashsmart_server.DTO.CommercialBinDTOMapper;
import com.g41.trashsmart_server.Models.CommercialBin;
import com.g41.trashsmart_server.Models.CommunalBin;
import com.g41.trashsmart_server.Models.Organization;
import com.g41.trashsmart_server.Repositories.CommercialBinRepository;
import com.g41.trashsmart_server.Repositories.OrganizationRepository;
import com.g41.trashsmart_server.Repositories.SmartBinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommercialBinService {
    private final CommercialBinRepository commercialBinRepository;
    private final CommercialBinDTOMapper commercialBinDTOMapper;
    private final OrganizationRepository organizationRepository;
    private final SmartBinRepository smartBinRepository;

    @Autowired
    public CommercialBinService(CommercialBinRepository commercialBinRepository,
                                CommercialBinDTOMapper commercialBinDTOMapper, OrganizationRepository organizationRepository, SmartBinRepository smartBinRepository) {
        this.commercialBinRepository = commercialBinRepository;
        this.commercialBinDTOMapper = commercialBinDTOMapper;
        this.organizationRepository = organizationRepository;
        this.smartBinRepository = smartBinRepository;
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

        if(CommercialBinOptional.isEmpty()) {
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
        String apiKey = generateUniqueApiKey();
        Organization organization = organizationOptional.get();
        commercialBin.setOrganization(organization);
        commercialBin.setAPIKEY(apiKey);
        commercialBinRepository.save(commercialBin);
    }
    private String generateUniqueApiKey() {
        String apiKey;
        do {
            apiKey = UUID.randomUUID().toString();
        } while (smartBinRepository.findSmartBinByAPIKey(apiKey).isPresent());
        return apiKey;
    }


    // Logically delete a commercial bin from the system
    public void deleteCommercialBin(Long id) {
        Optional<CommercialBin> CommercialBinOptional = commercialBinRepository.findById(id);
        if(CommercialBinOptional.isEmpty()) {
            throw new IllegalStateException("Garbage truck with id " + id + " does not exist");
        }
        CommercialBin CommercialBinToDelete = CommercialBinOptional.get();
        CommercialBinToDelete.setDeleted(true);
        commercialBinRepository.save(CommercialBinToDelete);
    }


    // Permanently delete a commercial bin from the system
    public void deletePermanentCommercialBin(Long id) {
        boolean exists = commercialBinRepository.existsById(id);
        if(!exists) {
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
        if(CommercialBin.getWasteType() != null  &&
                !CommercialBinToUpdate.getWasteType().equals(CommercialBin.getWasteType())) {
            CommercialBinToUpdate.setWasteType(CommercialBin.getWasteType());
        }
        if(!CommercialBinToUpdate.getBinStatus().equals(CommercialBin.getBinStatus())) {
            CommercialBinToUpdate.setBinStatus(CommercialBin.getBinStatus());
        }
        commercialBinRepository.save(CommercialBinToUpdate);
    }
}
