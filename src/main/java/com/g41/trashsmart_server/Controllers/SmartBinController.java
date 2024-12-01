package com.g41.trashsmart_server.Controllers;

import com.g41.trashsmart_server.DTO.SmartBinDTO;
import com.g41.trashsmart_server.Models.SmartBin;
import com.g41.trashsmart_server.Services.SmartBinService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/smart_bin")
public class SmartBinController {
    private final SmartBinService smartBinService;

    @Autowired
    public SmartBinController(SmartBinService smartBinService) {
        this.smartBinService = smartBinService;
    }

    // Retrieve all active smart bins
    @GetMapping
    @Operation(
            description = "Retrieve all active smart bins",
            summary = "All the active(deleted = false) smart bins will be retrieved",
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
    public List<SmartBinDTO> getSmartBins() {
        return smartBinService.getSmartBins();
    }
    
    // Retrieve all the smart bins
    @GetMapping(path = "getAll")
    @Operation(
            description = "Retrieve all the smart bins",
            summary = "All the registered and existing smart bins will be retrieved",
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
    public List<SmartBinDTO> getAllSmartBins() {
        return smartBinService.getAllSmartBins();
    }

    // Retrieve the total number of active smart bins
    @GetMapping(path = "count")
    @Operation(
            description = "Retrieve the total number of active smart bins",
            summary = "Returns the total count of active (deleted = false) smart bins",
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
    public Long getSmartBinCount() {
        return smartBinService.getSmartBinCount();
    }

    // Retrieve the total number of FULL smart bins
    @GetMapping(path = "full")
    @Operation(
            description = "Retrieve the total number of FULL smart bins",
            summary = "Returns the total number of bins that are FULL",
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
    public Long getFullSmartBins() {
        return smartBinService.getFullSmartBins();
    }

    @PutMapping("/update/{id}")
    @Operation(
            description = "Update the Smart Bin fill level",
            summary = "Returns the updated data",
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
    public ResponseEntity<SmartBin> updateSmartBin(
            @PathVariable Long id,
            @RequestBody SmartBin updatedData) {

        SmartBin updatedSmartBin = smartBinService.updateSmartBinData(id, updatedData);
        return ResponseEntity.ok(updatedSmartBin);
    }
    @GetMapping("/{id}")
    @Operation(
            description = "Get Specific bin details using id",
            summary = "Returns the data of sepecific bin with a given id",
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
    public ResponseEntity<SmartBin> getBinByID(@PathVariable Long id){
        SmartBin smartBin = smartBinService.getSmartBinById(id);
        return ResponseEntity.ok(smartBin);

    }
}
