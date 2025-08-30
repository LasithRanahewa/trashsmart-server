package com.g41.trashsmart_server.Controllers;

import com.g41.trashsmart_server.DTO.OrganizationDTO;
import com.g41.trashsmart_server.Models.Organization;
import com.g41.trashsmart_server.Services.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/organization")
public class OrganizationController {
    private final OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    // Retrieve all active organizations
    @GetMapping
    @Operation(
            description = "Retrieve all active organizations",
            summary = "All the active(deleted = false) organizations will be retrieved",
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
    public List<OrganizationDTO> getOrganizations() {
        return organizationService.getOrganizations();
    }

    // Retrieve all logically deleted organizations
    @GetMapping(path = "getDeletedAll")
    @Operation(
            description = "Retrieve all logically deleted organizations",
            summary = "All the logically deleted(deleted = true) organizations will be retrieved",
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
    public List<OrganizationDTO> getDeletedOrganizations() {
        return organizationService.getDeletedOrganizations();
    }

    // Retrieve all the organizations
    @GetMapping(path = "getAll")
    @Operation(
            description = "Retrieve all the organizations",
            summary = "All the active and logically deleted organizations will be retrieved",
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
    public List<OrganizationDTO> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    // Retrieve all the user details (Admin Privilege)
    @GetMapping(path = "getAllAdmin")
    @Operation(
            description = "Retrieve all the organizations",
            summary = "All organizations will be retrieved with all the details",
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
    public List<Organization> getOrganizationsAdmin() {
        return organizationService.getOrganizationsAdmin();
    }

    // Retrieve a specific organization given the id
    @GetMapping(path = "{organization_id}")
    @Operation(
            description = "Retrieve a specific organization given the id",
            summary = "A specific organization with the given id in the path will be retrieved",
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
    public OrganizationDTO getSpecificOrganization(@PathVariable("organization_id") Long id) {
        return organizationService.getSpecificOrganization(id);
    }

    // Create a new organization
    @PostMapping
    @Operation(
            description = "Create a new organization",
            summary = "Create a new organization when the user details are sent in the body of the POST request",
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
    public void registerNewOrganization(@RequestBody Organization organization) {
        organizationService.addNewOrganization(organization);
    }

    // Logically delete an organization from the system
    @DeleteMapping(path = "{organization_id}")
    @Operation(
            description = "Logically delete an organization from the system",
            summary = "Organization will be logically deleted from the system",
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
    public void deleteOrganization(@PathVariable("organization_id") Long id) {
        organizationService.deleteOrganization(id);
    }

    // Permanently delete an organization from the system (Admin Privilege)
    @DeleteMapping(path = "delete/{organization_id}")
    @Operation(
            description = "Permanently delete an organization from the system",
            summary = "Organization will be permanently deleted from the system",
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
    public void deletePermanentOrganization(@PathVariable("organization_id") Long id) {
        organizationService.deletePermanentOrganization(id);
    }

    // Update organization details
    @PutMapping(path = "{organization_id}")
    @Operation(
            description = "Update organization details",
            summary = "Organization details will be updated based on the object passed",
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
    public void updateOrganization(@PathVariable("organization_id") Long id,
                                    @RequestBody Organization organization) {
        organizationService.updateOrganization(id, organization);
    }

    // Weekly collection request count
    // Collection history

    // Smart bin list
    // WCR history

    // Contact information
}
