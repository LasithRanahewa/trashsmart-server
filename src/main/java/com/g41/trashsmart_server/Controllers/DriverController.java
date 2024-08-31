package com.g41.trashsmart_server.Controllers;
import com.g41.trashsmart_server.DTO.DriverDTO;
import com.g41.trashsmart_server.Models.Driver;
import com.g41.trashsmart_server.Services.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/driver")
public class DriverController {
    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }
// Retrieve a specific driver given the id
    @GetMapping(path = "{driver_id}")
    @Operation(
            description = "Retrieve a specific driver given the id",
            summary = "A specific household user with the given id in the path will be retrieved",
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
    public DriverDTO getSpecificDriver(@PathVariable("driver_id") Long id) {
        return driverService.getSpecificDriver(id);
    }
// update a specific driver's details
    @PutMapping(path = "update/{driver_id}")
    @Operation(
            description = "Update a specific driver's details",
            summary = "The details of a driver with the given id will be updated",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "403"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            }
    )
    public void updateDriver(@PathVariable("driver_id") Long id, @RequestBody Driver driver) {
        driverService.updateDriver(id, driver);
    }
    
}
