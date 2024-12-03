package com.g41.trashsmart_server.Controllers;

import com.g41.trashsmart_server.Services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping(path = "total_users")
    public long getTotalUsers() {
        return statisticsService.getTotalUsers();
    }

    // Get total dispatches
    @GetMapping(path = "total_collections")
    public long getTotalCollections() {
        return statisticsService.getTotalDispatches();
    }
}
