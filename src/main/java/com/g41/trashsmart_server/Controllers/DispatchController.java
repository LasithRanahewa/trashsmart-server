package com.g41.trashsmart_server.Controllers;

import com.g41.trashsmart_server.Enums.DispatchStatus;
import com.g41.trashsmart_server.Models.Dispatch;
import com.g41.trashsmart_server.Models.OrganizationDispatch;
import com.g41.trashsmart_server.Services.DispatchService;
import com.g41.trashsmart_server.Services.HouseholdDispatchService;
import com.g41.trashsmart_server.Services.OrganizationDispatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/dispatch")
public class DispatchController {
    private final DispatchService dispatchService;

    @Autowired
    public DispatchController(DispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    // get dispatches based on driver id and status
    @GetMapping(path = "driver/{driver_id}/{dispatch_status}")
    @Operation(
            description = "Get dispatches based on driver id and status",
            summary = "Return all the dispatches of a driver given their id and dispatch status",
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
    public List<Dispatch> getDispatchByDriverStatus(@PathVariable("driver_id") Long driver_id,
                                                    @PathVariable("dispatch_status") DispatchStatus dispatchStatus) {
        return dispatchService.getDispatchByDriverStatus(driver_id, dispatchStatus);
    }
}
