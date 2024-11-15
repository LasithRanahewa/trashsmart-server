package com.g41.trashsmart_server.DTO;

import com.g41.trashsmart_server.Models.Cleaner;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CleanerDTOMapper implements Function<Cleaner, CleanerDTO> {

    @Override
    public CleanerDTO apply(Cleaner cleaner) {
        return new CleanerDTO(
                cleaner.getId(),
                cleaner.getFirstName(),
                cleaner.getLastName(),
                cleaner.getEmail(),
                cleaner.getContactNo(),
                cleaner.getAddress(),
                cleaner.getRole(),
                cleaner.getProfileURL(),
                cleaner.getDob(),
                cleaner.getNic(),
                cleaner.getStatus(),
                cleaner.getTotalCollections(),
                cleaner.getCurrentStreak(),
                cleaner.getLongestStreak(),
                cleaner.getLastCollectionDate(),
                cleaner.getTotalActiveDays(),
                cleaner.getNumberOfHolidays(),
                cleaner.getDeleted(),
                cleaner.getCommunalBins()
        );
    }

}
