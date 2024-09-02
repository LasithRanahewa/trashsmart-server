package com.g41.trashsmart_server.Controllers;

import com.g41.trashsmart_server.DTO.OrganizationDTO;
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
@RequestMapping(path = "api/v1/smartbin")
public class SmartBinController {
    private final SmartBinService smartBinService;

    @Autowired
    public SmartBinController(SmartBinService smartBinService) {
        this.smartBinService = smartBinService;
    }
    // Retrieve all active organizations
    @GetMapping
    @Operation(
            description = "Retrieve all active smartbins",
            summary = "All the active(deleted = false) smartbins will be retrieved",
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
    
    // Add a new smartbin with random API key
        @PostMapping(path = "add")
        @Operation(
                description = "Add a new smartbin",
                summary = "A new smartbin will be added",
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
        public void addSmartBin(@RequestBody SmartBin smartBin) {
            smartBinService.addSmartBin(smartBin);
        }
// Update bin location, status, fill level
        @PutMapping(path = "update/{id}")
        @Operation(
                description = "Update a smartbin",
                summary = "A smartbin will be updated",
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
        public ResponseEntity<?> updateSmartBin(@PathVariable("id") Long id,@RequestHeader("API-Key") String apiKey, @RequestBody SmartBin smartBin) {
        
        try {
            smartBinService.updateSmartBin(id, apiKey, smartBin);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred");
        }

}
// Delete Smart bin logically
        @DeleteMapping(path = "delete/{id}")
        @Operation(
                description = "Delete a smartbin",
                summary = "A smartbin will be deleted",
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
        public ResponseEntity<?> deleteSmartBin(@PathVariable("id") Long id, @RequestHeader("API-Key") String apiKey) {
            try {
                smartBinService.deleteSmartBin(id, apiKey);
                return ResponseEntity.ok().build();
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.internalServerError().body("An error occurred");
            }
        }
        // Retrieve specfic smartbin by id
        @GetMapping(path = "get/{id}")
        @Operation(
                description = "Retrieve a smartbin",
                summary = "A smartbin will be retrieved",
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
        public SmartBinDTO getspecificBin(@PathVariable("id") Long id) {
            return smartBinService.getSmartBinById(id);
        }       
}
