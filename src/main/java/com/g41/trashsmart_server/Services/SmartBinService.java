package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.DTO.MaintenanceRequestDTO;
import com.g41.trashsmart_server.DTO.MaintenanceRequestDTOMapper;
import com.g41.trashsmart_server.DTO.SmartBinDTO;
import com.g41.trashsmart_server.DTO.SmartBinDTOMapper;
import com.g41.trashsmart_server.Enums.BinStatus;
import com.g41.trashsmart_server.Models.MaintenanceRequest;
import com.g41.trashsmart_server.Models.SmartBin;
import com.g41.trashsmart_server.Repositories.SmartBinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SmartBinService {
    private final SmartBinRepository smartBinRepository;
    private final SmartBinDTOMapper smartBinDTOMapper;
    private final MaintenanceRequestDTOMapper maintenanceRequestDTOMapper;

    @Autowired
    public SmartBinService(SmartBinRepository smartBinRepository,
                                SmartBinDTOMapper smartBinDTOMapper, MaintenanceRequestDTOMapper maintenanceRequestDTOMapper) {
        this.smartBinRepository = smartBinRepository;
        this.smartBinDTOMapper = smartBinDTOMapper;
        this.maintenanceRequestDTOMapper = maintenanceRequestDTOMapper;
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
        return smartBinRepository.findTotalCount();  // Get count of all active bins
    }

    // Retrieve the total number of FULL smart bins
    public Long getFullSmartBins() {
        return smartBinRepository.findFullLevelCount(BinStatus.FULL);
    }
}
