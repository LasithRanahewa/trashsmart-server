package com.g41.trashsmart_server.Controllers;

import com.g41.trashsmart_server.Configuration.JwtUtils;
import com.g41.trashsmart_server.DTO.AuthenticationRequest;
import com.g41.trashsmart_server.DTO.AuthenticationResponse;
import com.g41.trashsmart_server.Enums.Role;
import com.g41.trashsmart_server.Models.HouseholdUser;
import com.g41.trashsmart_server.Models.Contractor;
import com.g41.trashsmart_server.Services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/login")
    public AuthenticationResponse authenticateUser(@RequestBody AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        //get the role of the user by checking the instance of the user
        Role role;
        if (userDetails instanceof HouseholdUser) {
            role = ((HouseholdUser) userDetails).getRole();
        } else if (userDetails instanceof Contractor) {
            role = ((Contractor) userDetails).getRole();
        } else {
            throw new IllegalStateException("Unknown user type");
        }

        String jwt = jwtUtils.generateToken(userDetails.getUsername(), role.name());
        return new AuthenticationResponse(jwt);
    }
}