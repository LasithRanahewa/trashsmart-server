package com.g41.trashsmart_server.DTO;

import com.g41.trashsmart_server.Models.CommunalBin;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CommunalBinDTOMapper implements Function<CommunalBin, CommunalBinDTO>{
    @Override
    public CommunalBinDTO apply(CommunalBin communalBin) {
        return new CommunalBinDTO(
                communalBin.getId(),
                communalBin.getLongitude(),
                communalBin.getLatitude(),
                communalBin.getLastMaintenanceDate(),
                communalBin.getFillLevel(),
                communalBin.getBinStatus(),
                communalBin.getWasteType(),
                communalBin.getLastCollectionDate(),
                communalBin.getBinSize(),
                communalBin.getDeleted(),
                communalBin.getMaintenanceRequests(),
                communalBin.getSuburb(),
                communalBin.getInstallationDate(),
                communalBin.getCleaners()
        );
    }
}
