package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.DTO.CommunalBinDTO;
import com.g41.trashsmart_server.DTO.CommunalBinDTOMapper;
import com.g41.trashsmart_server.Models.Cleaner;
import com.g41.trashsmart_server.Models.CommunalBin;
import com.g41.trashsmart_server.Models.Organization;
import com.g41.trashsmart_server.Repositories.CleanerRepository;
import com.g41.trashsmart_server.Repositories.CommunalBinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommunalBinService {
    private final CommunalBinRepository communalBinRepository;
    private final CommunalBinDTOMapper communalBinDTOMapper;
    private final CleanerRepository cleanerRepository;

    @Autowired
    public CommunalBinService(CommunalBinRepository communalBinRepository,
                              CommunalBinDTOMapper communalBinDTOMapper, CleanerRepository cleanerRepository) {
        this.communalBinRepository = communalBinRepository;
        this.communalBinDTOMapper = communalBinDTOMapper;
        this.cleanerRepository = cleanerRepository;
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
        communalBinRepository.save(communalBin);
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

    public void assignCleaner(Long id, Long cleanerId) {
        CommunalBin communalBinToUpdate = communalBinRepository.findCommunalBinById(id).orElseThrow(
                () -> new IllegalStateException("Communal bin with id " + id + " does not exist")
        );

        List<Cleaner> existingCleaners = communalBinToUpdate.getCleaners();
        Optional<Cleaner> cleanerOptional = cleanerRepository.findCleanerById(cleanerId);
        if (cleanerOptional.isEmpty()) {
            throw new IllegalStateException("Cleaner with id " + cleanerId + " does not exist");
        }
        Cleaner cleaner = cleanerOptional.get();

        existingCleaners.add(cleaner);
        communalBinToUpdate.setCleaners(existingCleaners);
        communalBinRepository.save(communalBinToUpdate);

        Optional<CommunalBin> communalBinOptional = communalBinRepository.findCommunalBinById(communalBinToUpdate.getId());
        if (communalBinOptional.isEmpty()) {
            throw new IllegalStateException("Communal bin with id " + communalBinToUpdate.getId() + " does not exist");
        }
        CommunalBin communalBin = communalBinOptional.get();

        List<CommunalBin> existingCommunalBins = cleaner.getCommunalBins();
        existingCommunalBins.add(communalBin);
        cleanerRepository.save(cleaner);
    }
}
