package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.Models.HouseholdUser;
import com.g41.trashsmart_server.Repositories.HouseholdUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseholdUserService {
    private final HouseholdUserRepository householdUserRepository;

    @Autowired
    public HouseholdUserService(HouseholdUserRepository householdUserRepository) {
        this.householdUserRepository = householdUserRepository;
    }

    public List<HouseholdUser> getHouseholdUsers() {

    }
}
