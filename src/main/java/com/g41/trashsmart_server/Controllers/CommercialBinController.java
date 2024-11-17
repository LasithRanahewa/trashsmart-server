package com.g41.trashsmart_server.Controllers;

import com.g41.trashsmart_server.DTO.CommercialBinDTO;
import com.g41.trashsmart_server.DTO.CommercialBinRequestDTO;
import com.g41.trashsmart_server.Models.CommercialBin;
import com.g41.trashsmart_server.Models.GarbageTruck;
import com.g41.trashsmart_server.Models.Organization;
import com.g41.trashsmart_server.Services.CommercialBinService;
import com.g41.trashsmart_server.Repositories.OrganizationRepository;
import com.g41.trashsmart_server.Enums.WasteType;
import com.g41.trashsmart_server.Enums.BinSize;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/commercial_bin")
public class CommercialBinController {
    private final CommercialBinService commercialBinService;
    //private final OrganizationRepository organizationRepository;

    @Autowired
    public CommercialBinController(CommercialBinService commercialBinService) {
        this.commercialBinService = commercialBinService;
        //this.organizationRepository = organizationRepository;
    }
    
    // Retrieve all active commercial bins
    @GetMapping
    @Operation(
            description = "Retrieve all active commercial bins",
            summary = "All the active(deleted = false) commercial bins will be retrieved",
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
    public List<CommercialBinDTO> getCommercialBins() {
        return commercialBinService.getCommercialBins();
    }

    // Retrieve all logically deleted commercial bins
    @GetMapping(path = "getDeletedAll")
    @Operation(
            description = "Retrieve all logically deleted commercial bins",
            summary = "All the logically deleted(deleted = true) commercial bins will be retrieved",
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
    public List<CommercialBinDTO> getDeletedCommercialBins() {
        return commercialBinService.getDeletedCommercialBins();
    }


    // Retrieve all the commercial bins
    @GetMapping(path = "getAll")
    @Operation(
            description = "Retrieve all the commercial bins",
            summary = "All the registered and existing commercial bins will be retrieved",
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
    public List<CommercialBinDTO> getAllCommercialBins() {
        return commercialBinService.getAllCommercialBins();
    }

    // Retrieve all the commercial bin details (Admin Privilege)
    @GetMapping(path = "getAllAdmin")
    @Operation(
            description = "Retrieve all the commercial bins",
            summary = "All commercial bins will be retrieved with all the details",
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
    public List<CommercialBin> getCommercialBinsAdmin() {
        return commercialBinService.getCommercialBinsAdmin();
    }

    // Retrieve a specific commercial bin given the id
    @GetMapping(path = "{CommercialBin_id}")
    @Operation(
            description = "Retrieve a specific commercial bin given the id",
            summary = "A specific commercial bin with the given id in the path will be retrieved",
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
    public CommercialBinDTO getSpecificCommercialBin(@PathVariable("CommercialBin_id") Long id) {
        return commercialBinService.getSpecificCommercialBin(id);
    }

    // Create a new commercial bin
    @PostMapping
    @Operation(
            description = "Create a new commercial bin",
            summary = "Create a new commercial bin when the commercial bin details are sent in the body of the POST request",
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
//    public void registerNewCommercialBin(@RequestBody CommercialBinRequestDTO commercialBinRequest) {
//        Organization organization = organizationRepository.findOrganizationById(commercialBinRequest.getOrganizationId(), false)
//                .orElseThrow(() -> new RuntimeException("Organization not found"));
//
//        CommercialBin commercialBin = new CommercialBin(
//                commercialBinRequest.getLongitude(),
//                commercialBinRequest.getLatitude(),
//                WasteType.valueOf(commercialBinRequest.getWasteType()),
//                BinSize.valueOf(commercialBinRequest.getBinSize()),
//                organization
//        );
//
//        commercialBinService.addNewCommercialBin(commercialBin);
//    }
    public void registerNewCommercialBin(@RequestBody CommercialBin commercialBin) {
        commercialBinService.addNewCommercialBin(commercialBin);
    }


    // Logically delete commercial bin from the system
    @DeleteMapping(path = "{CommercialBin_id}")
    @Operation(
            description = "Logically delete a commercial bin from the system",
            summary = "commercial bin will be logically deleted from the system",
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
    public void deletedCommercialBin(@PathVariable("CommercialBin_id") Long id) {
        commercialBinService.deleteCommercialBin(id);
    }


    // Permanently delete a commercial bin from the system (Admin Privilege)
    @DeleteMapping(path = "delete/{CommercialBin_id}")
    @Operation(
            description = "Permanently delete a commercial bin from the system",
            summary = "commercial bin will be permanently deleted from the system",
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
    public void deletePermanentCommercialBin(@PathVariable("CommercialBin_id") Long id) {
        commercialBinService.deletePermanentCommercialBin(id);
    }

    // Update commercial bin details
    @PutMapping(path = "{CommercialBin_id}")
    @Operation(
            description = "Update commercial bin details",
            summary = "commercial bin details will be updated based on the object passed",
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
    public void updateCommercialBin(@PathVariable("CommercialBin_id") Long id,
                                   @RequestBody CommercialBin commercialBin) {
        commercialBinService.updateCommercialBin(id, commercialBin);
    }
}
