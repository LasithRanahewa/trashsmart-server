package com.g41.trashsmart_server.DTO;

import com.g41.trashsmart_server.Models.CommercialBin;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CommercialBinDTOMapper implements Function<CommercialBin, CommercialBinDTO>{
    @Override
    public CommercialBinDTO apply(CommercialBin commercialBin) {
        return new CommercialBinDTO(
                commercialBin.getId(),
                commercialBin.getLongitude(),
                commercialBin.getLatitude(),
                commercialBin.getLastMaintenanceDate(),
                commercialBin.getFillLevel(),
                commercialBin.getBinStatus(),
                commercialBin.getWasteType(),
                commercialBin.getLastCollectionDate(),
                commercialBin.getBinSize(),
                commercialBin.getDeleted(),
                commercialBin.getMaintenanceRequests(),
                commercialBin.getSuburb(),
                commercialBin.getPurchaseDate(),
                commercialBin.getOrganization(),
                commercialBin.getApiKey()
        );
    }
}
