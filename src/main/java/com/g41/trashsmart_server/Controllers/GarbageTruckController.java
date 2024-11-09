package com.g41.trashsmart_server.Controllers;

import com.g41.trashsmart_server.DTO.GarbageTruckDTO;
import com.g41.trashsmart_server.Models.GarbageTruck;
import com.g41.trashsmart_server.Services.GarbageTruckService;
import com.g41.trashsmart_server.Services.OrganizationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/garbage_truck")
public class GarbageTruckController {
    private final GarbageTruckService garbagetruckService;

    @Autowired
    public GarbageTruckController(GarbageTruckService garbagetruckService) {
        this.garbagetruckService = garbagetruckService;
    }


    // Retrieve all active garbage trucks
    @GetMapping
    @Operation(
            description = "Retrieve all active garbage trucks",
            summary = "All the active(deleted = false) garbage trucks will be retrieved",
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
    public List<GarbageTruckDTO> getGarbageTrucks() {
        return garbagetruckService.getGarbageTrucks();
    }

    // Retrieve all logically deleted garbage trucks
    @GetMapping(path = "getDeletedAll")
    @Operation(
            description = "Retrieve all logically deleted garbage trucks",
            summary = "All the logically deleted(deleted = true) garbage trucks will be retrieved",
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
    public List<GarbageTruckDTO> getDeletedGarbageTrucks() {
        return garbagetruckService.getDeletedGarbageTrucks();
    }


    // Retrieve all the garbage trucks
    @GetMapping(path = "getAll")
    @Operation(
            description = "Retrieve all the garbage trucks",
            summary = "All the registered and existing garbage trucks will be retrieved",
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
    public List<GarbageTruckDTO> getAllGarbageTrucks() {
        return garbagetruckService.getAllGarbageTrucks();
    }

    // Retrieve all the garbage truck details (Admin Privilege)
    @GetMapping(path = "getAllAdmin")
    @Operation(
            description = "Retrieve all the garbage trucks",
            summary = "All garbage trucks will be retrieved with all the details",
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
    public List<GarbageTruck> getGarbageTrucksAdmin() {
        return garbagetruckService.getGarbageTrucksAdmin();
    }

    // Retrieve a specific garbage truck given the id
    @GetMapping(path = "{garbagetruck_id}")
    @Operation(
            description = "Retrieve a specific garbage truck given the id",
            summary = "A specific garbage truck with the given id in the path will be retrieved",
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
    public GarbageTruckDTO getSpecificGarbageTruck(@PathVariable("garbagetruck_id") Long id) {
        return garbagetruckService.getSpecificGarbageTruck(id);
    }

    // Create a new garbage truck
    @PostMapping
    @Operation(
            description = "Create a new garbage truck",
            summary = "Create a new garbage truck when the garbage truck details are sent in the body of the POST request",
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
    public void registerNewGarbageTruck(@RequestBody GarbageTruck garbagetruck) {
        garbagetruckService.addNewGarbageTruck(garbagetruck);
    }


    // Logically delete an garbage truck from the system
    @DeleteMapping(path = "{garbagetruck_id}")
    @Operation(
            description = "Logically delete a garbage truck from the system",
            summary = "Garbage truck will be logically deleted from the system",
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
    public void deletedGarbageTruck(@PathVariable("garbagetruck_id") Long id) {
        garbagetruckService.deleteGarbageTruck(id);
    }


    // Permanently delete a garbage truck from the system (Admin Privilege)
    @DeleteMapping(path = "delete/{garbagetruck_id}")
    @Operation(
            description = "Permanently delete a garbage truck from the system",
            summary = "Garbage truck will be permanently deleted from the system",
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
    public void deletePermanentGarbageTruck(@PathVariable("garbagetruck_id") Long id) {
        garbagetruckService.deletePermanentGarbageTruck(id);
    }

    // Update garbage truck details
    @PutMapping(path = "{garbagetruck_id}")
    @Operation(
            description = "Update garbage truck details",
            summary = "Garbage truck details will be updated based on the object passed",
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
    public void updateGarbageTruck(@PathVariable("garbagetruck_id") Long id,
                                   @RequestBody GarbageTruck garbagetruck) {
        garbagetruckService.updateGarbageTruck(id, garbagetruck);
    }
}
