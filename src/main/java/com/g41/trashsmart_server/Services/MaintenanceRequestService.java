package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.DTO.MaintenanceRequestDTO;
import com.g41.trashsmart_server.DTO.MaintenanceRequestDTOMapper;
import com.g41.trashsmart_server.Models.MaintenanceRequest;
import com.g41.trashsmart_server.Models.SmartBin;
import com.g41.trashsmart_server.Repositories.MaintenanceRequestRepository;
import com.g41.trashsmart_server.Repositories.SmartBinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MaintenanceRequestService {
    private final MaintenanceRequestRepository maintenanceRequestRepository;
    private final MaintenanceRequestDTOMapper maintenanceRequestDTOMapper;
    private final SmartBinRepository smartBinRepository;

    @Autowired
    public MaintenanceRequestService(MaintenanceRequestRepository maintenanceRequestRepository,
                                     MaintenanceRequestDTOMapper maintenanceRequestDTOMapper, SmartBinRepository smartBinRepository) {
        this.maintenanceRequestRepository = maintenanceRequestRepository;
        this.maintenanceRequestDTOMapper = maintenanceRequestDTOMapper;
        this.smartBinRepository = smartBinRepository;
    }


//    @Autowired
//    private SecurityConfig securityConfig;


    // Retrieve all maintenance requests
    public List<MaintenanceRequestDTO> getAllMaintenanceRequests() {
        List<MaintenanceRequest> MaintenanceRequests = maintenanceRequestRepository.findAllMaintenanceRequestsUnFiltered();
        return MaintenanceRequests.stream()
                .map(maintenanceRequestDTOMapper)
                .collect(Collectors.toList());
    }
    

    // Retrieve all maintenance request details
    public List<MaintenanceRequest> getMaintenanceRequestsAdmin() {
        return maintenanceRequestRepository.findAll();
    }


    // Retrieve a specific maintenance request given the id
    public MaintenanceRequestDTO getSpecificMaintenanceRequest(Long id) {
        Optional<MaintenanceRequest> MaintenanceRequestOptional = maintenanceRequestRepository.findMaintenanceRequestById(id);

        if(MaintenanceRequestOptional.isEmpty()) {
            throw new IllegalStateException("Maintenance request with id " + id + " does not exist");
        }
        return maintenanceRequestDTOMapper.apply(MaintenanceRequestOptional.get());
    }

    // Create a new maintenance request
    public void addNewMaintenanceRequest(MaintenanceRequest maintenanceRequest, Long binId) {
        Optional<SmartBin> smartBinOptional = smartBinRepository.findById(binId);
        if (smartBinOptional.isEmpty()) {
            throw new IllegalStateException("Smart bin with id " + binId + " does not exist");
        }
        SmartBin smartBin = smartBinOptional.get();
        maintenanceRequest.setSmartBin(smartBin);
        maintenanceRequestRepository.save(maintenanceRequest);
    }
}
