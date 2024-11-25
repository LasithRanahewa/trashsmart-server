package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.DTO.CommunalBinDTO;
import com.g41.trashsmart_server.DTO.CommunalBinDTOMapper;
import com.g41.trashsmart_server.Models.CommunalBin;
import com.g41.trashsmart_server.Repositories.CommunalBinRepository;
import com.g41.trashsmart_server.Repositories.SmartBinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommunalBinService {
    private final CommunalBinRepository communalBinRepository;
    private final CommunalBinDTOMapper communalBinDTOMapper;
    private final SmartBinRepository smartBinRepository;

    @Autowired
    public CommunalBinService(CommunalBinRepository communalBinRepository,
                              CommunalBinDTOMapper communalBinDTOMapper, SmartBinRepository smartBinRepository) {
        this.communalBinRepository = communalBinRepository;
        this.communalBinDTOMapper = communalBinDTOMapper;
        this.smartBinRepository = smartBinRepository;
    }


//    @Autowired
//    private SecurityConfig securityConfig;


    // Retrieve all registered communal bin
    public List<CommunalBinDTO> getAllCommunalBins() {
        List<CommunalBin> CommunalBins = communalBinRepository.findAllCommunalBinsUnFiltered();
        return CommunalBins.stream()
                .map(communalBinDTOMapper)
                .collect(Collectors.toList());
    }


    // Retrieve all active communal bin
    public List<CommunalBinDTO> getCommunalBins() {
        List<CommunalBin> CommunalBins = communalBinRepository.findAllCommunalBins(false);
        return CommunalBins.stream()
                .map(communalBinDTOMapper)
                .collect(Collectors.toList());
    }

    // Retrieve all logically deleted communal bin
    public List<CommunalBinDTO> getDeletedCommunalBins() {
        List<CommunalBin> CommunalBins = communalBinRepository.findAllCommunalBins(true);
        return CommunalBins.stream()
                .map(communalBinDTOMapper)
                .collect(Collectors.toList());
    }


    // Retrieve all communal bin details
    public List<CommunalBin> getCommunalBinsAdmin() {
        return communalBinRepository.findAll();
    }


    // Retrieve a specific communal bin given the id (logically active)
    public CommunalBinDTO getSpecificCommunalBin(Long id) {
        Optional<CommunalBin> CommunalBinOptional = communalBinRepository.findCommunalBinById(id, false);

        if(CommunalBinOptional.isEmpty()) {
            throw new IllegalStateException("communal bin with id " + id + " does not exist");
        }
        return communalBinDTOMapper.apply(CommunalBinOptional.get());
    }

    // Create a new communal bin
    public void addNewCommunalBin(CommunalBin communalBin) {
        String apikey = generateUniqueApiKey();
        communalBin.setAPIKEY(apikey);
        communalBinRepository.save(communalBin);
    }

    private String generateUniqueApiKey() {
        String apiKey;
        do {
            apiKey = UUID.randomUUID().toString();
        } while (smartBinRepository.findSmartBinByAPIKey(apiKey).isPresent());
        return apiKey;
    }
    // Logically delete a communal bin from the system
    public void deleteCommunalBin(Long id) {
        Optional<CommunalBin> CommunalBinOptional = communalBinRepository.findById(id);
        if(CommunalBinOptional.isEmpty()) {
            throw new IllegalStateException("Garbage truck with id " + id + " does not exist");
        }
        CommunalBin CommunalBinToDelete = CommunalBinOptional.get();
        CommunalBinToDelete.setDeleted(true);
        communalBinRepository.save(CommunalBinToDelete);
    }


    // Permanently delete a communal bin from the system
    public void deletePermanentCommunalBin(Long id) {
        boolean exists = communalBinRepository.existsById(id);
        if(!exists) {
            throw new IllegalStateException("Communal bin with id " + id + " does not exist");
        }
        communalBinRepository.deleteById(id);
    }

    // Update communal bin details
    public void updateCommunalBin(Long id, CommunalBin CommunalBin) {
        CommunalBin CommunalBinToUpdate = communalBinRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("communal bin with id " + id + " does not exist")
        );
        if (CommunalBin.getLongitude() != null &&
                !CommunalBinToUpdate.getLongitude().equals(CommunalBin.getLongitude())) {
            CommunalBinToUpdate.setLongitude(CommunalBin.getLongitude());
        }
        if (CommunalBin.getLatitude() != null &&
                !CommunalBinToUpdate.getLatitude().equals(CommunalBin.getLatitude())) {
            CommunalBinToUpdate.setLatitude(CommunalBin.getLatitude());
        }
        if(CommunalBin.getWasteType() != null  &&
                !CommunalBinToUpdate.getWasteType().equals(CommunalBin.getWasteType())) {
            CommunalBinToUpdate.setWasteType(CommunalBin.getWasteType());
        }
        if(!CommunalBinToUpdate.getBinStatus().equals(CommunalBin.getBinStatus())) {
            CommunalBinToUpdate.setBinStatus(CommunalBin.getBinStatus());
        }
        communalBinRepository.save(CommunalBinToUpdate);
    }
}
