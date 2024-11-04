package com.g41.trashsmart_server.Controllers;

import com.g41.trashsmart_server.Services.OrganizationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/organization_dispatch")
public class OrganizationDispatchController {
    private final OrganizationService organizationService;

    public OrganizationDispatchController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }


}
