package com.g41.trashsmart_server.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g41.trashsmart_server.DTO.ContractorDTO;
import com.g41.trashsmart_server.Models.Contractor;
import com.g41.trashsmart_server.Services.ContractorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = "api/v1/contractor")
public class ContractorController {

    private final ContractorService contractorService;

    @Autowired
    public ContractorController(ContractorService contractorService) {
        this.contractorService = contractorService;
    }

    // Retrieve all active contractors
    @GetMapping
    @Operation(
            description = "Retrieve all active contractors",
            summary = "All the active(deleted = false) contractors will be retrieved",
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
    public List<ContractorDTO> getContractors() {
        return contractorService.getContractors();
    }

    // Retrieve all logically deleted contractors
    @GetMapping(path = "getDeletedAll")
    @Operation(
            description = "Retrieve all logically deleted contractors",
            summary = "All the logically deleted(deleted = true) contractors will be retrieved",
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
    public List<ContractorDTO> getDeletedContractors() {
        return contractorService.getDeletedContractors();
    }

    // Retrieve all the contractors
    @GetMapping(path = "getAll")
    @Operation(
            description = "Retrieve all the contractors",
            summary = "All the active and logically deleted contractors will be retrieved",
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
    public List<ContractorDTO> getAllContractors() {
        return contractorService.getAllContractors();
    }

    // Retrieve all the contractor details (Admin Privilege)
    @GetMapping(path = "getAllAdmin")
    @Operation(
            description = "Retrieve all the contractors",
            summary = "All contractors will be retrieved with all the details",
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
    public List<Contractor> getContractorsAdmin() {
        return contractorService.getContractorsAdmin();
    }

    // Retrieve a specific contractor given the id
    @GetMapping(path = "{contractor_id}")
    @Operation(
            description = "Retrieve a specific contractor given the id",
            summary = "A specific contractor with the given id in the path will be retrieved",
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
    public ContractorDTO getSpecificContractor(@PathVariable("contractor_id") Long id) {
        return contractorService.getSpecificContractor(id);
    }

    // Create a new contractor
    @PostMapping(path = "register")
    @Operation(
            description = "Create a new contractor",
            summary = "Create a new contractor when the contractor details are sent in the body of the POST request",
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
    public void registerNewContractor(@RequestBody Contractor contractor) {
        contractorService.addNewContractor(contractor);
    }

    // Logically delete a contractor from the system
    @DeleteMapping(path = "{contractor_id}")
    @Operation(
            description = "Logically delete a contractor from the system",
            summary = "Contractor will be logically deleted from the system",
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
    public void deleteContractor(@PathVariable("contractor_id") Long id) {
        contractorService.deleteContractor(id);
    }

    // Permanently delete a contractor from the system (Admin Privilege)
    @DeleteMapping(path = "delete/{contractor_id}")
    @Operation(
            description = "Permanently delete a contractor from the system",
            summary = "Contractor will be permanently deleted from the system",
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
    public void deletePermanentContractor(@PathVariable("contractor_id") Long id) {
        contractorService.deletePermanentContractor(id);
    }

    // Update contractor details
    @PutMapping(path = "{contractor_id}")
    @Operation(
            description = "Update contractor details",
            summary = "Contractor details will be updated based on the object passed",
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
    public void updateContractor(@PathVariable("contractor_id") Long id,
                                 @RequestBody Contractor contractor) {
        contractorService.updateContractor(id, contractor);
    }
}