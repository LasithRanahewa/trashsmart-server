package com.g41.trashsmart_server.Controllers;

import com.g41.trashsmart_server.DTO.RecyclingPlantDTO;
import com.g41.trashsmart_server.DTO.RecyclingPlantDTOMapper;
import com.g41.trashsmart_server.Models.RecyclingPlant;
import com.g41.trashsmart_server.Services.RecyclingPlantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/recyclingPlant")
public class RecyclingPlantController {
    private final RecyclingPlantService recyclingPlantService;

    @Autowired
    public RecyclingPlantController(RecyclingPlantService recyclingPlantService) {
        this.recyclingPlantService = recyclingPlantService;
    }

    @Operation(
            description = "A new recycling plant will be added to the system by the contractor.",
            summary = "Adding a new recycling plant.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unautherized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<RecyclingPlant> addRecyclingPlant(@RequestBody RecyclingPlant recyclingPlant) {
        RecyclingPlant addedPlant = recyclingPlantService.addRecyclingPlant(recyclingPlant);
        return ResponseEntity.ok(addedPlant);
    }


    @Operation(
            description = "Retrieve all recycling plants which are added to the system.",
            summary = "Get all recycling plants.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unautherized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @GetMapping
    public List<RecyclingPlantDTO> getAllRecyclingPlants() {
        return recyclingPlantService.getAllRecyclingPlants();
    }


    @Operation(
            description = "Retrieve all active/logically-deleted recycling plants which are added to the system.",
            summary = "Get all active recycling plants.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unautherized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @GetMapping(path = "/active")
    public List<RecyclingPlantDTO> getActiveRecyclingPlants() {
        return recyclingPlantService.getActiveRecyclingPlants();
    }

    @Operation(
            description = "Retrieve all logically deleted recycling plants which are added to the system.",
            summary = "Get all logically deleted recycling plants.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unautherized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @GetMapping(path = "/deleted")
    public List<RecyclingPlantDTO> getDeletedRecyclingPlants() {
        return recyclingPlantService.getDeletedRecyclingPlants();
    }


    @Operation(
            description = "Retrieve a specific recycling plant form the system by the ID.",
            summary = "Get a specific recycling plant.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unautherized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @GetMapping(path = "/{plantId}")
    public RecyclingPlantDTO getSpecificRecyclingPlant(@PathVariable Long plantId) {
        return recyclingPlantService.getSpecificRecyclingPlant(plantId);
    }


    @Operation(
            description = "Delete a specific recycling plant form the system by the ID.",
            summary = "Delete a specific recycling plant.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unautherized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @DeleteMapping(path = "/{plantId}")
    public ResponseEntity<Void> deleteRecyclingPlant(@PathVariable Long plantId) {
        recyclingPlantService.deleteRecyclingPlant(plantId);
        return ResponseEntity.ok().build();
    }


    @Operation(
            description = "Update a specific recycling plant from the system by the ID.",
            summary = "Update a specific recycling plant.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Conflict",
                            responseCode = "409"
                    )
            }
    )
    @PutMapping(path = "/{plantId}")
    public ResponseEntity<RecyclingPlantDTO> updateRecyclingPlant(@PathVariable Long plantId, @RequestBody RecyclingPlantDTO updateDTO) {
        RecyclingPlantDTO recyclingPlantDTO = recyclingPlantService.updateRecyclingPlant(plantId, updateDTO);
        return ResponseEntity.ok(recyclingPlantDTO);
    }
}
