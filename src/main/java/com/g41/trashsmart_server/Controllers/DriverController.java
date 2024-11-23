package com.g41.trashsmart_server.Controllers;

import com.g41.trashsmart_server.Configuration.JwtUtils;
import com.g41.trashsmart_server.DTO.AuthenticationResponse;
import com.g41.trashsmart_server.DTO.DriverDTO;
import com.g41.trashsmart_server.DTO.DriverDTOMapper;
import com.g41.trashsmart_server.Enums.Role;
import com.g41.trashsmart_server.Models.Driver;
import com.g41.trashsmart_server.Services.DriverService;
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
@RequestMapping(path = "api/v1/driver")
public class DriverController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final DriverService driverService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    // Retrieve all active drivers
    @GetMapping
    @Operation(
            description = "Retrieve all active drivers",
            summary = "All the active(deleted = false) drivers will be retrieved",
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
    public List<DriverDTOMapper> getDrivers() {
        return driverService.getDrivers();
    }

    // Retrieve all logically deleted drivers
    @GetMapping(path = "getDeletedAll")
    @Operation(
            description = "Retrieve all logically deleted drivers",
            summary = "All the logically deleted(deleted = true) drivers will be retrieved",
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
    public List<DriverDTOMapper> getDeletedDrivers() {
        return driverService.getDeletedDrivers();
    }

    // Retrieve all the drivers
    @GetMapping(path = "getAll")
    @Operation(
            description = "Retrieve all the drivers",
            summary = "All the active and logically deleted drivers will be retrieved",
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
    public List<DriverDTOMapper> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    // Retrieve all the user details (Admin Privilege)
    @GetMapping(path = "getAllAdmin")
    @Operation(
            description = "Retrieve all the drivers",
            summary = "All drivers will be retrieved with all the details",
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
    public List<Driver> getDriversAdmin() {
        return driverService.getDriversAdmin();
    }

    // Retrieve a specific driver given the id
    @GetMapping(path = "{driver_id}")
    @Operation(
            description = "Retrieve a specific driver given the id",
            summary = "A specific driver with the given id in the path will be retrieved",
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

    // Create a new driver
    @PostMapping(path = "")
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
    public void registerNewDriver(@RequestBody Driver driver) {
        driverService.addNewDriver(driver);
    }

    // Self register a new driver
    @PostMapping(path = "register")
    @Operation(
            description = "Self register a new driver",
            summary = "Self register a new driver when the user details are sent in the body of the POST request",
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
    public ResponseEntity<AuthenticationResponse> SelfRegisterNewDriver(@RequestBody Driver driver) {
        driverService.addNewDriver(driver);
//      //add validations later
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(driver.getUsername(), driver.getPassword())
        );
        // Load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(driver.getUsername());
        // Get the user id
        Long userId = ((Driver) userDetails).getId();
        // Generate JWT token
        String jwt = jwtUtils.generateToken(userDetails.getUsername(), Role.DRIVER.name(), userId);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    // Logically delete a driver from the system
    @DeleteMapping(path = "{driver_id}")
    @Operation(
            description = "Logically delete a driver from the system",
            summary = "Driver will be logically deleted from the system",
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
    public void deleteDriver(@PathVariable("driver_id") Long id) {
        driverService.deleteDriver(id);
    }

    // Permanently delete a driver from the system (Admin Privilege)
    @DeleteMapping(path = "delete/{driver_id}")
    @Operation(
            description = "Permanently delete a driver from the system",
            summary = "Driver will be permanently deleted from the system",
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
    public void deletePermanentDriver(@PathVariable("driver_id") Long id) {
        driverService.deletePermanentDriver(id);
    }

    // Update driver details
    @PutMapping(path = "{driver_id}")
    @Operation(
            description = "Update driver details",
            summary = "Driver details will be updated based on the object passed",
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
    public void updateDriver(@PathVariable("driver_id") Long id,
                                    @RequestBody Driver driver) {
        driverService.updateDriver(id, driver);
    }
}