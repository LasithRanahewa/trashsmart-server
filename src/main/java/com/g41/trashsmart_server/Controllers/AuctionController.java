package com.g41.trashsmart_server.Controllers;

import com.g41.trashsmart_server.DTO.AuctionDTO;
import com.g41.trashsmart_server.Models.Auction;
import com.g41.trashsmart_server.Services.AuctionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/auction")
public class AuctionController {
    private final AuctionService auctionService;

    @Autowired
    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @Operation(
            description = "Creating a new auction",
            summary = "A new auction will be created with the stating time and base price",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unautherized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Auction> createAuction(@RequestBody Auction auction) {
        Auction createdAuction = auctionService.createAuction(auction);
        return ResponseEntity.ok(createdAuction);
    }


    @Operation(
            description = "Get all auctions",
            summary = "Fetch all auctions with any status",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401")
            }
    )
    @GetMapping
    public List<AuctionDTO> getAllAuctions() {
        return auctionService.getAllAuctions();
    }


    @Operation(
            description = "Get Live Ongoing Auctions",
            summary = "All the ongoing auctions will be displayed",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unautherized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @GetMapping(path = "/live")
    public List<AuctionDTO> getLiveAuctions() {
        return auctionService.getLiveAuctions();
    }


    @Operation(
            description = "Get Past Auctions",
            summary = "All the past auctions will be displayed",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unautherized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @GetMapping(path = "/past")
    public List<AuctionDTO> getPastAuctions() {
        return auctionService.getPastAuctions();
    }


    @Operation(
            description = "Get Canceled Auctions",
            summary = "All the canceled auctions before the ending time period will be displayed",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unautherized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @GetMapping(path = "/canceled")
    public List<AuctionDTO> getCanceledAuctions() {
        return auctionService.getCanceledAuctions();
    }


    @Operation(
            description = "Get Upcoming Auctions",
            summary = "All the upcoming auctions will be displayed",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unautherized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @GetMapping(path = "/upcoming")
    public List<AuctionDTO> getUpcomingAuctions() {
        return auctionService.getUpcomingAuctions();
    }

    @Operation(
            description = "Get Upcoming Auctions",
            summary = "All the upcoming auctions will be displayed",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unautherized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @GetMapping(path = "/deleted")
    public List<AuctionDTO> getDeletedAuctions() {
        return auctionService.getDeletedAuctions();
    }

    @Operation(
            description = "Get specific auction by ID",
            summary = "Fetch details of a specific auction",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            }
    )
    @GetMapping("/{auctionId}")
    public AuctionDTO getSpecificAuction(@PathVariable Long auctionId) {
        return auctionService.getSpecificAuction(auctionId);
    }

    @Operation(
            description = "Get specific live auction by ID",
            summary = "Fetch details of a specific live auction",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            }
    )
    @GetMapping("/live/{auctionId}")
    public AuctionDTO getLiveAuctionById(@PathVariable Long auctionId) {
        return auctionService.getAuctionById(auctionId, "Live");
    }


    @Operation(
            description = "Get specific past auction by ID",
            summary = "Fetch details of a specific past auction",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            }
    )
    @GetMapping("/past/{auctionId}")
    public AuctionDTO getPastAuctionById(@PathVariable Long auctionId) {
        return auctionService.getAuctionById(auctionId, "Past");
    }


    @Operation(
            description = "Get specific canceled auction by ID",
            summary = "Fetch details of a specific canceled auction",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            }
    )
    @GetMapping("/canceled/{auctionId}")
    public AuctionDTO getCanceledAuctionById(@PathVariable Long auctionId) {
        return auctionService.getAuctionById(auctionId, "Canceled");
    }


    @Operation(
            description = "Get specific upcoming auction by ID",
            summary = "Fetch details of a specific upcoming auction",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            }
    )
    @GetMapping("/upcoming/{auctionId}")
    public AuctionDTO getUpcomingAuctionById(@PathVariable Long auctionId) {
        return auctionService.getAuctionById(auctionId, "Upcoming");
    }


    @Operation(
            description = "Manually cancel a live auction",
            summary = "Contractor cancels a live auction",
            responses = {
                    @ApiResponse(
                            description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            }
    )
    @PutMapping(path = "/cancel/{auctionId}")
    public ResponseEntity<Void> cancelAuction(@PathVariable Long auctionId) {
        auctionService.cancelLiveAuction(auctionId);
        return ResponseEntity.ok().build();
    }


    @Operation(
            description = "Delete specific past auction by ID",
            summary = "Delete a past auction",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            }
    )
    @PutMapping(path = "/past/{auctionId}")
    public ResponseEntity<Void> deletePastAuction(@PathVariable Long auctionId) {
        auctionService.deletePastAuction(auctionId);
        return ResponseEntity.ok().build();
    }


    @Operation(
            description = "Delete specific upcoming auction by ID",
            summary = "Delete an upcoming auction",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            }
    )
    @DeleteMapping(path = "/upcoming/{auctionId}")
    public ResponseEntity<Void> deleteUpcomingAuction(@PathVariable Long auctionId) {
        auctionService.deleteUpcomingAuction(auctionId);
        return ResponseEntity.ok().build();
    }


    @Operation(
            description = "Recycle Plant has to be registered in the auction for them to bid.",
            summary = "Recycle Plant registering for an auction.",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            }
    )
    @PostMapping(path = "{auctionId}/register")
    public ResponseEntity<String> registerForAuction(@PathVariable Long auctionId, @RequestParam Long recyclingPlantId) {
        String response = auctionService.registerForAuction(auctionId, recyclingPlantId);
        return ResponseEntity.ok(response);
    }

}
