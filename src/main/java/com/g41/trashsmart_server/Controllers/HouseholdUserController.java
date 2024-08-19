package com.g41.trashsmart_server.Controllers;

import com.g41.trashsmart_server.DTO.HouseholdUserDTO;
import com.g41.trashsmart_server.Models.HouseholdUser;
import com.g41.trashsmart_server.Services.HouseholdUserService;
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

    @GetMapping
    public List<HouseholdUserDTO> getHouseholdUsers() {
        return householdUserService.getHouseholdUsers();
    }

    @GetMapping(path = "{household_user_id}")
    public HouseholdUserDTO getSpecificHouseholdUser(@PathVariable("household_user_id") Long id) {
        return householdUserService.getSpecificHouseholdUser(id);
    }

    @PostMapping
    public void registerNewHouseholdUser(@RequestBody HouseholdUser householdUser) {
        householdUserService.addNewHouseholdUser(householdUser);
    }
}
