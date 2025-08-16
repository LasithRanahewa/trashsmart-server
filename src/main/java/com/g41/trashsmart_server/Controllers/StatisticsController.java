package com.g41.trashsmart_server.Controllers;

import com.g41.trashsmart_server.Models.Organization;
import com.g41.trashsmart_server.Services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    // Get total waste last week
    @GetMapping("last_week_total_waste")
    public Integer getLastWeekTotalWasteVolume() {
        return statisticsService.getLastWeekTotalWasteVolume().intValue();
    }

    // Number of WCRs last week
    @GetMapping("last_week_request_count")
    public Long getLastWeekWasteRequestCount() {
        return statisticsService.getLastWeekWasteRequestCount();
    }

    // Endpoint for total accumulated waste
    @GetMapping("total_accumulated_waste")
    public Integer getTotalAccumulatedWaste() {
        return statisticsService.getTotalAccumulatedWaste().intValue();
    }

    // Endpoint for total accumulated recyclable waste
    @GetMapping("total_accumulated_recyclable_waste")
    public Integer getTotalAccumulatedRecyclableWaste() {
        return statisticsService.getTotalAccumulatedRecyclableWaste().intValue();
    }

    // Get organization count
    @GetMapping(path = "total_organizations")
    public long getTotalOrganizations() {
        return statisticsService.getTotalOrganizations();
    }

    // Get new organization registration count
    @GetMapping(path = "total_new_organizations")
    public long getTotalNewOrganizations() {
        return statisticsService.getTotalNewOrganizations();
    }

    // Get active organization count
    @GetMapping(path = "total_active_organizations")
    public long getActiveOrganizations() {
        return statisticsService.getActiveOrganizations();
    }

    // Get organizations weekly collection request count
    @GetMapping(path = "org_weekly_wcr_count")
    public long getLastWeekOrgWasteRequestCount() {
        return statisticsService.getLastWeekOrgWasteRequestCount();
    }

    // Get total bin count
    @GetMapping(path = "total_bins")
    public long getTotalBins() {
        return statisticsService.getTotalBins();
    }

    // Get currently full bin count
    @GetMapping(path = "total_full_bins")
    public long getFullBins() {
        return statisticsService.getFullBins();
    }

    // Get new commercial bin purchase count
    @GetMapping(path = "total_commercial_bin_purchase_count")
    public long getCommercialBinPurchaseCount() {
        return statisticsService.getCommercialBinPurchaseCount();
    }

    // Get new communal bin establishment count
    @GetMapping(path = "total_communal_bin_est_count")
    public long getCommunalBinEstCount() {
        return statisticsService.getCommunalBinEstCount();
    }

    // Get top 10 organizations
    @GetMapping(path = "top_ten_organizations")
    public List<Organization> getTopTenOrganizations() {
        return statisticsService.getTopTenOrganizations();
    }

    // Get new recycling plant registration count
    @GetMapping(path = "total_new_recycling_plants")
    public long getTotalNewRecyclingPlants() {
        return 2;
//        return statisticsService.getTotalNewRecyclingPlants();
    }

    // Get active organization count
//    @GetMapping(path = "total_active_recycling_plants")
//    public long getActiveRecyclingPlants() {
//        return statisticsService.getActiveRecyclingPlants();
//    }

    //live

}
