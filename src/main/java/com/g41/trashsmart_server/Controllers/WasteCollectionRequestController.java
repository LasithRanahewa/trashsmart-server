package com.g41.trashsmart_server.Controllers;

import com.g41.trashsmart_server.Enums.WasteCollectionRequestStatus;
import com.g41.trashsmart_server.Models.WasteCollectionRequest;
import com.g41.trashsmart_server.Services.WasteCollectionRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/wcr")
public class WasteCollectionRequestController {
    private final WasteCollectionRequestService wasteCollectionRequestService;

    public WasteCollectionRequestController(WasteCollectionRequestService wasteCollectionRequestService) {
        this.wasteCollectionRequestService = wasteCollectionRequestService;
    }

    // Retrieve all waste collection requests
    @GetMapping
    @Operation(
            description = "Retrieve all waste collection requests",
            summary = "All the waste collection requests of the system will be retrieved",
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
    public List<WasteCollectionRequest> getAllRequests() {
        return wasteCollectionRequestService.getAllRequests();
    }

    // Retrieve all the waste collection requests opened by a given organization
    @GetMapping(path = "organization/{org_id}/{status}")
    @Operation(
            description = "Retrieve all the waste collection requests opened by a given organization",
            summary = "All the waste collection requests of the given organization will be retrieved",
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
    public List<WasteCollectionRequest> getAllRequestsByOrganization(@PathVariable("org_id") Long id,
                                                                     @PathVariable("status") Integer status) {
        return wasteCollectionRequestService.getAllRequestsByOrganization(id, status);
    }

    // Retrieve specific by id
    @GetMapping(path = "{id}")
    @Operation(
            description = "Retrieve a specific waste collection request by id",
            summary = "Waste collection request with the given id is retrieved",
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
    public WasteCollectionRequest getRequest(@PathVariable("id") Long id) {
        return wasteCollectionRequestService.getRequest(id);
    }

    // Create a new waste collection request
    @PostMapping(path = "{org_id}")
    @Operation(
            description = "Create a new waste collection request",
            summary = "New waste collection request is created. Organization id has to be given",
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
    public void createRequest(@RequestBody WasteCollectionRequest wasteCollectionRequest, @PathVariable("org_id") Long id) {
        wasteCollectionRequestService.createRequest(wasteCollectionRequest, id);
    }

    // Delete a waste collection request
    @DeleteMapping(path = "{id}")
    @Operation(
            description = "Delete a waste collection request",
            summary = "Waste collection request with the given id is deleted",
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
    public void deleteRequest(@PathVariable("id") Long id) {
        wasteCollectionRequestService.deleteRequest(id);
    }

    // Update a waste collection request
    @PutMapping(path = "{id}")
    @Operation(
            description = "Update a waste collection request",
            summary = "Waste collection request with the given id is updated with the given values",
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
    public void updateRequest(@PathVariable("id") Long id, @RequestBody WasteCollectionRequest wasteCollectionRequest) {
        wasteCollectionRequestService.updateRequest(id, wasteCollectionRequest);
    }
}
