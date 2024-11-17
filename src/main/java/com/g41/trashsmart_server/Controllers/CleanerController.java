package com.g41.trashsmart_server.Controllers;

import com.g41.trashsmart_server.Configuration.JwtUtils;
import com.g41.trashsmart_server.DTO.AuthenticationResponse;
import com.g41.trashsmart_server.DTO.CleanerDTO;
import com.g41.trashsmart_server.Enums.Role;
import com.g41.trashsmart_server.Models.Cleaner;
import com.g41.trashsmart_server.Services.CleanerService;
import com.g41.trashsmart_server.Services.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/cleaner")
public class CleanerController {

    private final CleanerService cleanerService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public CleanerController(CleanerService cleanerService) {
        this.cleanerService = cleanerService;
    }

    // Retrieve all active cleaners
    @Operation(
            description = "Retrieve all active cleaners",
            summary = "All the active(deleted = false) cleaners will be retrieved",
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
    @GetMapping
    public List<CleanerDTO> getCleaners() {
        return cleanerService.getCleaners();
    }


    // Retrieve all logically deleted cleaners
    @Operation(
            description = "Retrieve all logically deleted cleaners",
            summary = "All the logically deleted(deleted = true) cleaners will be retrieved",
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
    @GetMapping(path = "/deleted")
    public List<CleanerDTO> getDeletedCleaners() {
        return cleanerService.getDeletedCleaners();
    }


    // Retrieve all the cleaners
    @Operation(
            description = "Retrieve all the cleaners",
            summary = "All the active and logically deleted cleaners will be retrieved",
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
    @GetMapping(path = "/getAll")
    public List<CleanerDTO> getAllCleaners() {
        return cleanerService.getAllCleaners();
    }


    // Retrieve all the user details (Admin Privilege)
    @Operation(
            description = "Retrieve all the cleaners",
            summary = "All cleaners will be retrieved with all the details",
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
    @GetMapping(path = "getAllAdmin")
    public List<Cleaner> getCleanersAdmin() {
        return cleanerService.getCleanersAdmin();
    }


    // Retrieve a specific cleaner given the id
    @Operation(
            description = "Retrieve a specific cleaner given the id",
            summary = "A specific cleaner with the given id in the path will be retrieved",
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
    @GetMapping(path = "{cleaner_id}")
    public CleanerDTO getSpecificCleaner(@PathVariable Long cleaner_id) {
        return cleanerService.getSpecificCleaner(cleaner_id);
    }


    // Create a new cleaner
    @Operation(
            description = "Create a new driver",
            summary = "Create a new driver when the user details are sent in the body of the POST request",
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
    @PostMapping
    public void registerNewCleaner(@RequestBody Cleaner cleaner) {
        cleanerService.addNewCleaner(cleaner);
    }


    //Self register a cleaner
    @Operation(
            description = "Self register a new cleaner",
            summary = "Self register a new cleaner when the user details are sent in the body of the POST request",
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
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> selfRegisterNewCleaner(@RequestBody Cleaner cleaner) {
        cleanerService.addNewCleaner(cleaner);

        //Authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(cleaner.getEmail(), cleaner.getPassword())
        );

        //Load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(cleaner.getEmail());
        Long userId = ((Cleaner) userDetails).getId();

        //Generate JWT token
        String jwt = jwtUtils.generateToken(userDetails.getUsername(), Role.CLEANER.name(), userId);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }


    //Logically Delete a cleaner
    @Operation(
            description = "Logically delete a cleaner from the system",
            summary = "Cleaner will be logically deleted from the system",
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
    @PutMapping (path = "/delete/{cleaner_id}")
    public void deleteCleaner(@PathVariable Long cleaner_id) {
        cleanerService.deleteCleaner(cleaner_id);
    }


    //Permanently delete a cleaner
    @Operation(
            description = "Permanently delete a cleaner from the system",
            summary = "Cleaner will be permanently deleted from the system",
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
    @DeleteMapping(path = "/{cleaner_id}")
    public void deletePermanentCleaner(@PathVariable Long cleaner_id) {
        cleanerService.deletePermanentCleaner(cleaner_id);
    }


    //Update a cleaner
    @Operation(
            description = "Update cleaner details",
            summary = "Cleaner details will be updated based on the object passed",
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
    @PutMapping(path = "{cleaner_id}")
    public void updateCleaner(@PathVariable Long cleaner_id, @RequestBody Cleaner cleaner) {
        cleanerService.updateCleaner(cleaner_id, cleaner);
    }
}
