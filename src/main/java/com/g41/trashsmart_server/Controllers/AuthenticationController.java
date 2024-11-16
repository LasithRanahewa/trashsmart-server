package com.g41.trashsmart_server.Controllers;

import com.g41.trashsmart_server.Configuration.JwtUtils;
import com.g41.trashsmart_server.DTO.AuthenticationRequest;
import com.g41.trashsmart_server.DTO.AuthenticationResponse;
import com.g41.trashsmart_server.Enums.Role;
import com.g41.trashsmart_server.Models.*;
import com.g41.trashsmart_server.Repositories.ContractorRepository;
import com.g41.trashsmart_server.Repositories.DriverRepository;
import com.g41.trashsmart_server.Repositories.HouseholdUserRepository;
import com.g41.trashsmart_server.Repositories.OrganizationRepository;
import com.g41.trashsmart_server.Services.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private HouseholdUserRepository householdUserRepository;
    @Autowired
    private ContractorRepository contractorRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private OrganizationRepository organizationRepository;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody AuthenticationRequest request) {
        try {
            String email = request.getEmail();
            Optional<HouseholdUser> householdUser = householdUserRepository.findHouseholdUserByEmail(email);
            Optional<Contractor> contractor = contractorRepository.findContractorByEmail(email);
            Optional<Driver> driver = driverRepository.findDriverByEmail(email);
            Optional<Organization> organization = organizationRepository.findOrganizationByEmail(email);

            if (householdUser.isEmpty() && contractor.isEmpty() && driver.isEmpty() && organization.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            
            //get the role of the user by checking the instance of the user
            Role role;
            Long userId;
            if (userDetails instanceof HouseholdUser) {
                role = ((HouseholdUser) userDetails).getRole();
                userId = ((HouseholdUser) userDetails).getId();
            } else if (userDetails instanceof Contractor) {
                role = ((Contractor) userDetails).getRole();
                userId = ((Contractor) userDetails).getId();
            } else if (userDetails instanceof Driver) {
                role = ((Driver) userDetails).getRole();
                userId = ((Driver) userDetails).getId();
            } else if (userDetails instanceof Organization) {
                role = ((Organization) userDetails).getRole();
                userId = ((Organization) userDetails).getId();
            } else {
                throw new IllegalStateException("Unknown user type");
            }
            String jwt = jwtUtils.generateToken(userDetails.getUsername(), role.name(), userId);
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

   @PostMapping("/logout")
   public void logout(HttpServletRequest request, HttpServletResponse response) {
       String authHeader = request.getHeader("Authorization");
       if (authHeader != null && authHeader.startsWith("Bearer ")) {
           String token = authHeader.substring(7); // Remove "Bearer " prefix
           Authentication auth = SecurityContextHolder.getContext().getAuthentication();
           if (auth != null) {
               jwtUtils.invalidateToken(token);
               new SecurityContextLogoutHandler().logout(request, response, auth);
           }
           response.setStatus(HttpServletResponse.SC_OK);
       } else {
           response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
       }
   }
}