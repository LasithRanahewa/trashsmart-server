package com.g41.trashsmart_server.DTO;

import com.g41.trashsmart_server.Models.GarbageTruck;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class GarbageTruckDTOMapper implements Function<GarbageTruck, GarbageTruckDTO> {
    @Override
    public GarbageTruckDTO apply(GarbageTruck garbagetruck) {
        return new GarbageTruckDTO(
                garbagetruck.getId(),
                garbagetruck.getLicencePlateNo(),
                garbagetruck.getMileage(),
                garbagetruck.getMaxVolume(),
                garbagetruck.getTruckStatus(),
                garbagetruck.getDeleted()
          
        );
    }
}