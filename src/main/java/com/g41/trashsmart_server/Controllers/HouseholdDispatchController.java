package com.g41.trashsmart_server.Controllers;

import com.g41.trashsmart_server.Enums.DispatchStatus;
import com.g41.trashsmart_server.Models.HouseholdDispatch;
import com.g41.trashsmart_server.Models.OrganizationDispatch;
import com.g41.trashsmart_server.Services.HouseholdDispatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/household_dispatch")
public class HouseholdDispatchController {
    private final HouseholdDispatchService householdDispatchService;

    @Autowired
    public HouseholdDispatchController(HouseholdDispatchService householdDispatchService) {
        this.householdDispatchService = householdDispatchService;
    }

    // Retrieve all household dispatches
    @GetMapping
    @Operation(
            description = "Retrieve all household dispatches",
            summary = "All household dispatches will be retrieved",
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
    public List<HouseholdDispatch> getAllHouseholdDispatches() {
        return householdDispatchService.getAllHouseholdDispatches();
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
    public HouseholdDispatch getHouseholdDispatchById(@PathVariable("dispatch_id") Long id) {
        return householdDispatchService.getHouseholdDispatch(id);
    }

    // Create a new household dispatch
    @PostMapping
    @Operation(
            description = "Create a new household dispatch",
            summary = "Create a new household dispatch when the details are sent in the body of the POST request",
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
    public HouseholdDispatch createHouseholdDispatch(@RequestBody HouseholdDispatch dispatch) {
        return householdDispatchService.createHouseholdDispatch(dispatch);
    }

    // Update household dispatch details
    @PutMapping(path = "{id}")
    @Operation(
            description = "Update household dispatch details",
            summary = "Household dispatch details will be updated based on the object passed",
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
    public HouseholdDispatch updateHouseholdDispatch(@PathVariable Long id, @RequestBody HouseholdDispatch dispatch) {
        dispatch.setId(id);
        return householdDispatchService.updateHouseholdDispatch(dispatch);
    }

    // delete a household dispatch from the system
    @DeleteMapping(path = "{id}")
    @Operation(
            description = "Logically delete a household dispatch from the system",
            summary = "Household dispatch will be logically deleted from the system",
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
    public void deleteHouseholdDispatch(@PathVariable Long id) {
        householdDispatchService.deleteHouseholdDispatch(id);
    }

    // Update household dispatch status
    @PutMapping(path = "update_status/{id}")
    @Operation(
            description = "Update household dispatch status",
            summary = "Household dispatch status will be updated based on the status passed",
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
    public HouseholdDispatch updateHouseholdDispatchStatus(@PathVariable Long id, @RequestBody DispatchStatus status) {
        return householdDispatchService.updateHouseholdDispatchStatus(id, status);
    }
}

