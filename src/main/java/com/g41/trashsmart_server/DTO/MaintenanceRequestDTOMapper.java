package com.g41.trashsmart_server.DTO;

import com.g41.trashsmart_server.Models.MaintenanceRequest;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class MaintenanceRequestDTOMapper implements Function<MaintenanceRequest, MaintenanceRequestDTO>{
    @Override
    public MaintenanceRequestDTO apply(MaintenanceRequest maintenanceRequest) {
        return new MaintenanceRequestDTO(
                maintenanceRequest.getId(),
                maintenanceRequest.getCreatedTimeStamp(),
                maintenanceRequest.getRequestStatus(),
                maintenanceRequest.getSmartBin(),
                maintenanceRequest.getOtherNotes()
        );
    }
}
