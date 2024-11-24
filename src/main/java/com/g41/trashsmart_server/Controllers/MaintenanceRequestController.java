package com.g41.trashsmart_server.Controllers;

import com.g41.trashsmart_server.DTO.MaintenanceRequestDTO;
import com.g41.trashsmart_server.Models.CommercialBin;
import com.g41.trashsmart_server.Models.MaintenanceRequest;
import com.g41.trashsmart_server.Services.MaintenanceRequestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/maintenance_request")
public class MaintenanceRequestController {
    private final MaintenanceRequestService maintenanceRequestService;

    @Autowired
    public MaintenanceRequestController(MaintenanceRequestService maintenanceRequestService) {
        this.maintenanceRequestService = maintenanceRequestService;
    }

    // Retrieve all the maintenance requests
    @GetMapping(path = "")
    @Operation(
            description = "Retrieve all the maintenance requests",
            summary = "All the registered and existing maintenance requests will be retrieved",
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
    public List<MaintenanceRequestDTO> getAllMaintenanceRequests() {
        return maintenanceRequestService.getAllMaintenanceRequests();
    }

    // Retrieve all the maintenance request details (Admin Privilege)
    @GetMapping(path = "getAllAdmin")
    @Operation(
            description = "Retrieve all the maintenance requests",
            summary = "All maintenance requests will be retrieved with all the details",
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
    public List<MaintenanceRequest> getMaintenanceRequestsAdmin() {
        return maintenanceRequestService.getMaintenanceRequestsAdmin();
    }

    // Retrieve a specific maintenance request given the id
    @GetMapping(path = "{MaintenanceRequest_id}")
    @Operation(
            description = "Retrieve a specific maintenance request given the id",
            summary = "A specific maintenance request with the given id in the path will be retrieved",
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
    public MaintenanceRequestDTO getSpecificMaintenanceRequest(@PathVariable("MaintenanceRequest_id") Long id) {
        return maintenanceRequestService.getSpecificMaintenanceRequest(id);
    }

    // Create a new waste collection request
    @PostMapping(path = "{bin_id}")
    @Operation(
            description = "Create a new maintenance request",
            summary = "New maintenance request is created. Commercial bin id has to be given",
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
    public void registerNewMaintenanceRequest(@RequestBody MaintenanceRequest maintenanceRequest, @PathVariable("bin_id") Long id) {
        maintenanceRequestService.addNewMaintenanceRequest(maintenanceRequest, id);
    }

    // Update maintenance request details
    @PutMapping(path = "{request_id}")
    @Operation(
            description = "Update maintenance request details",
            summary = "Request details will be updated based on the object passed",
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
    public void updateMaintenanceRequest(@PathVariable("request_id") Long id,
                                    @RequestBody MaintenanceRequest maintenanceRequest) {
        maintenanceRequestService.updateRequest(id, maintenanceRequest);
    }

    // Logically delete a request from the system
    @DeleteMapping(path = "{req_id}")
    @Operation(
            description = "Logically delete a request from the system",
            summary = "Maintenance request will be logically deleted from the system",
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
    public void deleteMaintenanceRequest(@PathVariable("req_id") Long id) {
        maintenanceRequestService.deleteRequest(id);
    }

    // Retrieve the total number of maintenance requests count
    @GetMapping(path = "count")
    @Operation(
            description = "Retrieve the total number of request count",
            summary = "Returns the total number of maintenance requests",
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
    public Long getMaintenanceRequests() {
        return maintenanceRequestService.getMaintenanceRequestsCount();
    }
}
