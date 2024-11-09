package com.g41.trashsmart_server.DTO;

import com.g41.trashsmart_server.Models.RecyclingPlant;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class RecyclingPlantDTOMapper implements Function<RecyclingPlant, RecyclingPlantDTO> {
    @Override
    public RecyclingPlantDTO apply(RecyclingPlant recyclingPlant) {
        return new RecyclingPlantDTO(
                recyclingPlant.getId(),
                recyclingPlant.getFirstName(),
                recyclingPlant.getLastName(),
                recyclingPlant.getEmail(),
                recyclingPlant.getContactNo(),
                recyclingPlant.getAddress(),
                recyclingPlant.getRole(),
                recyclingPlant.getProfileURL(),
                recyclingPlant.getBRN()
        );
    }
}
