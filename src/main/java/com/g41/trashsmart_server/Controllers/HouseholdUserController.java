package com.g41.trashsmart_server.Controllers;

import com.g41.trashsmart_server.DTO.HouseholdUserDTO;
import com.g41.trashsmart_server.Models.HouseholdUser;
import com.g41.trashsmart_server.Services.HouseholdUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/household_user")
public class HouseholdUserController {
    private final HouseholdUserService householdUserService;

    @Autowired
    public HouseholdUserController(HouseholdUserService householdUserService) {
        this.householdUserService = householdUserService;
    }

    // Retrieve all active household users
    @GetMapping
    @Operation(
            description = "Retrieve all active household users",
            summary = "All the active(deleted = false) household users will be retrieved",
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
    public List<HouseholdUserDTO> getHouseholdUsers() {
        return householdUserService.getHouseholdUsers();
    }

    // Retrieve all logically deleted household users
    @GetMapping(path = "getDeletedAll")
    @Operation(
            description = "Retrieve all logically deleted household users",
            summary = "All the logically deleted(deleted = true) household users will be retrieved",
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
    public List<HouseholdUserDTO> getDeletedHouseholdUsers() {
        return householdUserService.getDeletedHouseholdUsers();
    }

    // Retrieve all the household users
    @GetMapping(path = "getAll")
    @Operation(
            description = "Retrieve all the household users",
            summary = "All the active and logically deleted household users will be retrieved",
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
    public List<HouseholdUserDTO> getAllHouseholdUsers() {
        return householdUserService.getAllHouseholdUsers();
    }

    // Retrieve all the user details (Admin Privilege)
    @GetMapping(path = "getAllAdmin")
    @Operation(
            description = "Retrieve all the household users",
            summary = "All household users will be retrieved with all the details",
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
    public List<HouseholdUser> getHouseholdUsersAdmin() {
        return householdUserService.getHouseholdUsersAdmin();
    }

    // Retrieve a specific household user given the id
    @GetMapping(path = "{household_user_id}")
    @Operation(
            description = "Retrieve all the household users",
            summary = "All household users will be retrieved with all the details",
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
    public HouseholdUserDTO getSpecificHouseholdUser(@PathVariable("household_user_id") Long id) {
        return householdUserService.getSpecificHouseholdUser(id);
    }

    @PostMapping
    public void registerNewHouseholdUser(@RequestBody HouseholdUser householdUser) {
        householdUserService.addNewHouseholdUser(householdUser);
    }

    @DeleteMapping(path = "{household_user_id}")
    public void deleteHouseholdUser(@PathVariable("household_user_id") Long id) {
        householdUserService.deleteHouseholdUser(id);
    }

    @PutMapping
    public void updateHouseholdUser(@RequestBody HouseholdUser householdUser) {
        householdUserService.updateHouseholdUser(householdUser);
    }
}