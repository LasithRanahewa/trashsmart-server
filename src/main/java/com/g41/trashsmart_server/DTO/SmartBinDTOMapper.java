package com.g41.trashsmart_server.DTO;

import com.g41.trashsmart_server.Models.SmartBin;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SmartBinDTOMapper implements Function<SmartBin, SmartBinDTO>{
    @Override
    public SmartBinDTO apply(SmartBin smartBin) {
        return new SmartBinDTO(
                smartBin.getId(),
                smartBin.getLongitude(),
                smartBin.getLatitude(),
                smartBin.getLastMaintenanceDate(),
                smartBin.getFillLevel(),
                smartBin.getBinStatus(),
                smartBin.getWasteType(),
                smartBin.getLastCollectionDate(),
                smartBin.getBinSize(),
                smartBin.getDeleted(),
                smartBin.getMaintenanceRequests(),
                smartBin.getSuburb()
        );
    }
}
