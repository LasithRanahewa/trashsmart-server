package com.g41.trashsmart_server.Controllers;

import com.g41.trashsmart_server.Enums.DispatchStatus;
import com.g41.trashsmart_server.Enums.WasteType;
import com.g41.trashsmart_server.Models.OrganizationDispatch;
import com.g41.trashsmart_server.Services.OrganizationDispatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/organization_dispatch")
public class OrganizationDispatchController {
    private final OrganizationDispatchService organizationDispatchService;

    @Autowired
    public OrganizationDispatchController(OrganizationDispatchService organizationDispatchService) {
        this.organizationDispatchService = organizationDispatchService;
    }

    // Get all the dispatches
    @GetMapping
    @Operation(
            description = "Get all the dispatches",
            summary = "Get all the dispatches in the system regardless of their status and waste type",
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
    public List<OrganizationDispatch> getAllOrganizationDispatches() {
        return organizationDispatchService.getAllOrganizationDispatches();
    }

    // Get all the dispatches based on the DispatchStatus
    @GetMapping(path = "{dispatch_status}")
    @Operation(
            description = "Get all the dispatches based on status",
            summary = "Get all the dispatches in the system based on the given status in the GET request",
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
    public List<OrganizationDispatch> getOrganizationDispatchesByStatus(
            @PathVariable("dispatch_status") DispatchStatus dispatchStatus
    ) {
        return organizationDispatchService.getOrganizationDispatchesByStatus(dispatchStatus);
    }

    // Get all the dispatches based on the WasteType
    @GetMapping(path = "{waste_type}")
    @Operation(
            description = "Get all the dispatches based on waste type",
            summary = "Get all the dispatches in the system based on the given waste type in the GET request",
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
    public List<OrganizationDispatch> getOrganizationDispatchesByWasteType(
            @PathVariable("waste_type") WasteType wasteType
    ) {
        return organizationDispatchService.getOrganizationDispatchesWasteType(wasteType);
    }

    // Get all the dispatches based on the DispatchStatus and WasteType
    @GetMapping(path = "{dispatch_status}/{waste_type}")
    @Operation(
            description = "Get all the dispatches based on status and waste type",
            summary = "Get all the dispatches in the system based on the given status and waste ype in the GET request",
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
    public List<OrganizationDispatch> getOrganizationDispatchesByStatusAndWasteType(
            @PathVariable("dispatch_status") DispatchStatus dispatchStatus,
            @PathVariable("waste_type") WasteType wasteType
    ) {
        return organizationDispatchService.getOrganizationDispatchesByStatusAndWasteType(dispatchStatus, wasteType);
    }

    // Get dispatch using id
    @GetMapping(path = "{dispatch_id}")
    @Operation(
            description = "Get a dispatch based on id",
            summary = "Get a dispatch based on the given id if it's in the system",
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
    public OrganizationDispatch getOrganizationDispatchById(@PathVariable("dispatch_id") Long id) {
        return organizationDispatchService.getOrganizationDispatchById(id);
    }

    // Create dispatches
    @PostMapping
    @Operation(
            description = "Create new dispatches",
            summary = "Create new dispatches based on the given waste type",
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
    public Map<Integer, OrganizationDispatch> clusterWasteCollectionRequests(@RequestBody WasteType wasteType) {
        return organizationDispatchService.clusterWasteCollectionRequests(wasteType);
    }
}
