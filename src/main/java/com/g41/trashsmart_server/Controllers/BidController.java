package com.g41.trashsmart_server.Controllers;

import com.g41.trashsmart_server.DTO.BidDTO;
import com.g41.trashsmart_server.Services.BidService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/bid")
public class BidController {
    @Autowired
    private BidService bidService;

    @Operation(
            description = "Place a bid for a live auction",
            summary = "Recycling plant places a bid for a specific auction",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Invalid Bid", responseCode = "400")
            }
    )
    @PostMapping(path = "/place")
    public ResponseEntity<BidDTO> placeBid(
            @RequestParam Long auctionId,
            @RequestParam Long recyclingPlantId,
            @RequestParam Double bidAmount) {
        BidDTO bidDTO = bidService.placeBid(auctionId, recyclingPlantId, bidAmount);
        return ResponseEntity.ok(bidDTO);
    }


    @Operation(
            description = "Get bid history for a specific auction",
            summary = "View all bids placed for a specific auction",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Unautherized", responseCode = "401")
            }
    )
    @GetMapping(path = "/history/{auctionId}")
    public ResponseEntity<List<BidDTO>> getBidHistory(@PathVariable Long auctionId) {
        List<BidDTO> bidHistory = bidService.getBidHistory(auctionId);
        return ResponseEntity.ok(bidHistory);
    }


    @Operation(
            description = "Get the winning bid for a specific auction",
            summary = "View the winning bid for a specific auction after it has ended",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Auction Not Ended", responseCode = "400")
            }
    )
    @GetMapping("/winning-bid/{auctionId}")
    public ResponseEntity<BidDTO> getWinningBid(@PathVariable Long auctionId) {
        BidDTO winningBid = bidService.getWinningBid(auctionId);
        return ResponseEntity.ok(winningBid);
    }

}
