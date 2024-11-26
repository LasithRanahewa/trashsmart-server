package com.g41.trashsmart_server.DTO;

import com.g41.trashsmart_server.Models.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class BidDTOMapper implements Function<Bid, BidDTO> {

    private final RecyclingPlantDTOMapper recyclingPlantDTOMapper;

    @Autowired
    public BidDTOMapper(RecyclingPlantDTOMapper recyclingPlantDTOMapper) {
        this.recyclingPlantDTOMapper = recyclingPlantDTOMapper;
    }

    @Override
    public BidDTO apply(Bid bid) {
        return new BidDTO(
                bid.getId(),
                bid.getBidAmount(),
                bid.getBidTime(),
                bid.getAuction() != null ? bid.getAuction().getId() : null,
                recyclingPlantDTOMapper.apply(bid.getRecyclingPlant())
        );
    }
}
