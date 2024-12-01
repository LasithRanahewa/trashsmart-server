package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.DTO.MaintenanceRequestDTOMapper;
import com.g41.trashsmart_server.DTO.SmartBinDTO;
import com.g41.trashsmart_server.DTO.SmartBinDTOMapper;
import com.g41.trashsmart_server.Enums.BinStatus;
import com.g41.trashsmart_server.Models.SmartBin;
import com.g41.trashsmart_server.Repositories.SmartBinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SmartBinService {
    private final SmartBinRepository smartBinRepository;
    private final SmartBinDTOMapper smartBinDTOMapper;

    @Autowired
    public SmartBinService(SmartBinRepository smartBinRepository,
                                SmartBinDTOMapper smartBinDTOMapper) {
        this.smartBinRepository = smartBinRepository;
        this.smartBinDTOMapper = smartBinDTOMapper;
    }

    // Retrieve all registered smart bin
    public List<SmartBinDTO> getAllSmartBins() {
        List<SmartBin> SmartBins = smartBinRepository.findAllSmartBinsUnFiltered();
        return SmartBins.stream()
                .map(smartBinDTOMapper)
                .collect(Collectors.toList());
    }
    
    // Retrieve all active smart bin
    public List<SmartBinDTO> getSmartBins() {
        List<SmartBin> SmartBins = smartBinRepository.findAllSmartBins(false);
        return SmartBins.stream()
                .map(smartBinDTOMapper)
                .collect(Collectors.toList());
    }

    // Retrieve the total number of active SmartBins
    public Long getSmartBinCount() {
        return smartBinRepository.findTotalCount();
    }

    // Retrieve the total number of FULL smart bins
    public Long getFullSmartBins() {
        return smartBinRepository.findFullLevelCount(BinStatus.FULL);
    }

    public SmartBin updateSmartBinData(Long id, SmartBin updatedData) {
        // Find the SmartBin by APIKEY
        Optional<SmartBin> optionalSmartBin = smartBinRepository.findSmartBinById(id,false);
        if (optionalSmartBin.isEmpty()) {
            throw new IllegalArgumentException("SmartBin with ID " + id + " not found.");
        }

        // Update the SmartBin data
        SmartBin existingSmartBin = optionalSmartBin.get();

        // Update fields
        if (updatedData.getFillLevel() != null) {
            existingSmartBin.setFillLevel(updatedData.getFillLevel());
            existingSmartBin.setBinStatus(determineBinStatus(updatedData.getFillLevel())); // Optional logic
        }
        if (updatedData.getLongitude() != null) {
            existingSmartBin.setLongitude(updatedData.getLongitude());
        }
        if (updatedData.getLatitude() != null) {
            existingSmartBin.setLatitude(updatedData.getLatitude());
        }
        existingSmartBin.setLastCollectionDate(LocalDate.now()); // Automatically update

        // Save and return the updated entity
        return smartBinRepository.save(existingSmartBin);
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
    public SmartBin getSmartBinById(Long id) {
        Optional<SmartBin> optionalSmartBin = smartBinRepository.findSmartBinById(id);
        if (optionalSmartBin.isEmpty()) {
            throw new IllegalArgumentException("SmartBin with ID " + id + " not found");
        }
        return optionalSmartBin.get();
    }

}
