package com.g41.trashsmart_server.Controllers;

import com.g41.trashsmart_server.Configuration.JwtUtils;
import com.g41.trashsmart_server.DTO.AuthenticationResponse;
import com.g41.trashsmart_server.DTO.HouseholdUserDTO;
import com.g41.trashsmart_server.Enums.Role;
import com.g41.trashsmart_server.Models.HouseholdUser;
import com.g41.trashsmart_server.Repositories.ContractorRepository;
import com.g41.trashsmart_server.Repositories.HouseholdUserRepository;
import com.g41.trashsmart_server.Services.HouseholdUserService;
import com.g41.trashsmart_server.Services.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/household_user")
public class HouseholdUserController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final HouseholdUserService householdUserService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


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
            description = "Retrieve a specific household user given the id",
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
    public HouseholdUserDTO getSpecificHouseholdUser(@PathVariable("household_user_id") Long id) {
        return householdUserService.getSpecificHouseholdUser(id);
    }

    // Create a new household user
    @PostMapping(path = "")
    @Operation(
            description = "Create a new household user",
            summary = "Create a new household user when the user details are sent in the body of the POST request",
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
    public void registerNewHouseholdUser(@RequestBody HouseholdUser householdUser) {
        householdUserService.addNewHouseholdUser(householdUser);
    }

    // Self register a new household user
    @Operation(
            description = "Self register a new household user",
            summary = "Self register a new household user when the user details are sent in the body of the POST request",
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
    @PostMapping(path = "register")
    public ResponseEntity<AuthenticationResponse> SelfRegisterNewHouseholdUser(@RequestBody HouseholdUser householdUser) {
        householdUserService.addNewHouseholdUser(householdUser);
//      //add validations later
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(householdUser.getUsername(), householdUser.getPassword())
        );
        // Load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(householdUser.getUsername());
        // Generate JWT token
        String jwt = jwtUtils.generateToken(userDetails.getUsername(), Role.HOUSEHOLD_USER.name());
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    // Logically delete a household user from the system
    @DeleteMapping(path = "{household_user_id}")
    @Operation(
            description = "Logically delete a household user from the system",
            summary = "Household user will be logically deleted from the system",
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
    public void deleteHouseholdUser(@PathVariable("household_user_id") Long id) {
        householdUserService.deleteHouseholdUser(id);
    }

    // Permanently delete a household user from the system (Admin Privilege)
    @DeleteMapping(path = "delete/{household_user_id}")
    @Operation(
            description = "Permanently delete a household user from the system",
            summary = "Household user will be permanently deleted from the system",
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
    public void deletePermanentHouseholdUser(@PathVariable("household_user_id") Long id) {
        householdUserService.deletePermanentHouseholdUser(id);
    }

    // Update household user details
    @PutMapping(path = "{household_user_id}")
    @Operation(
            description = "Update household user details",
            summary = "Household user details will be updated based on the object passed",
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
    public void updateHouseholdUser(@PathVariable("household_user_id") Long id,
                                    @RequestBody HouseholdUser householdUser) {
        householdUserService.updateHouseholdUser(id, householdUser);
    }
}