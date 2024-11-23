package com.g41.trashsmart_server.DTO;

import com.g41.trashsmart_server.Enums.RequestStatus;
import com.g41.trashsmart_server.Models.MaintenanceRequest;
import com.g41.trashsmart_server.Models.SmartBin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceRequestDTO {
    private Long id;
    private LocalDateTime createdTimeStamp;
    private RequestStatus requestStatus;
    private SmartBin smartBin;
    private String otherNotes;
}
