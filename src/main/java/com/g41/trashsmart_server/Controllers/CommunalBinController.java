package com.g41.trashsmart_server.Controllers;

import com.g41.trashsmart_server.DTO.CommunalBinDTO;
import com.g41.trashsmart_server.Models.CommunalBin;
import com.g41.trashsmart_server.Services.CommunalBinService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/communal_bin")

public class CommunalBinController {
    private final CommunalBinService communalBinService;

    @Autowired
    public CommunalBinController(CommunalBinService communalBinService) {
        this.communalBinService = communalBinService;
    }

    // Retrieve all active communal bins
    @GetMapping
    @Operation(
            description = "Retrieve all active communal bins",
            summary = "All the active(deleted = false) communal bins will be retrieved",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    public List<CommunalBinDTO> getCommunalBins() {
        return communalBinService.getCommunalBins();
    }

    // Retrieve all logically deleted communal bins
    @GetMapping(path = "getDeletedAll")
    @Operation(
            description = "Retrieve all logically deleted communal bins",
            summary = "All the logically deleted(deleted = true) communal bins will be retrieved",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    public List<CommunalBinDTO> getDeletedCommunalBins() {
        return communalBinService.getDeletedCommunalBins();
    }


    // Retrieve all the communal bins
    @GetMapping(path = "getAll")
    @Operation(
            description = "Retrieve all the communal bins",
            summary = "All the registered and existing communal bins will be retrieved",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    public List<CommunalBinDTO> getAllCommunalBins() {
        return communalBinService.getAllCommunalBins();
    }

    // Retrieve all the communal bin details (Admin Privilege)
    @GetMapping(path = "getAllAdmin")
    @Operation(
            description = "Retrieve all the communal bins",
            summary = "All communal bins will be retrieved with all the details",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    public List<CommunalBin> getCommunalBinsAdmin() {
        return communalBinService.getCommunalBinsAdmin();
    }

    // Retrieve a specific communal bin given the id
    @GetMapping(path = "{CommunalBin_id}")
    @Operation(
            description = "Retrieve a specific communal bin given the id",
            summary = "A specific communal bin with the given id in the path will be retrieved",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    public CommunalBinDTO getSpecificCommunalBin(@PathVariable("CommunalBin_id") Long id) {
        return communalBinService.getSpecificCommunalBin(id);
    }

    // Create a new waste collection request
    @PostMapping(path = "")
    @Operation(
            description = "Create a new communal bin",
            summary = "New communal bin is created. Organization id has to be given",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    public void registerNewCommunalBin(@RequestBody CommunalBin communalBin) {
        communalBinService.addNewCommunalBin(communalBin);
    }

    // Logically delete communal bin from the system
    @DeleteMapping(path = "{CommunalBin_id}")
    @Operation(
            description = "Logically delete a communal bin from the system",
            summary = "communal bin will be logically deleted from the system",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    public void deletedCommunalBin(@PathVariable("CommunalBin_id") Long id) {
        communalBinService.deleteCommunalBin(id);
    }


    // Permanently delete a communal bin from the system (Admin Privilege)
    @DeleteMapping(path = "delete/{CommunalBin_id}")
    @Operation(
            description = "Permanently delete a communal bin from the system",
            summary = "communal bin will be permanently deleted from the system",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    public void deletePermanentCommunalBin(@PathVariable("CommunalBin_id") Long id) {
        communalBinService.deletePermanentCommunalBin(id);
    }

    // Update communal bin details
    @PutMapping(path = "{CommunalBin_id}")
    @Operation(
            description = "Update communal bin details",
            summary = "communal bin details will be updated based on the object passed",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    public void updateCommunalBin(@PathVariable("CommunalBin_id") Long id,
                                    @RequestBody CommunalBin communalBin) {
        communalBinService.updateCommunalBin(id, communalBin);
    }

    @PutMapping(path = "assign/{communalBinId}")
    @Operation(
            description = "Assign cleaner to bin",
            summary = "Cleaner will be assigned to a communal bin based on the object passed",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    public void assignCleaner(@PathVariable("communalBinId") Long id, @RequestBody Map<String, Long> requestBody) {
        Long cleanerId = requestBody.get("cleaner_id");
        communalBinService.assignCleaner(id, cleanerId);
    }
}
