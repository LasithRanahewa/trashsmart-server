package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.Enums.DispatchStatus;
import com.g41.trashsmart_server.Enums.Status;
import com.g41.trashsmart_server.Enums.TruckStatus;
import com.g41.trashsmart_server.Enums.WasteType;
import com.g41.trashsmart_server.Models.Driver;
import com.g41.trashsmart_server.Models.GarbageTruck;
import com.g41.trashsmart_server.Models.HouseholdDispatch;
import com.g41.trashsmart_server.Models.OrganizationDispatch;
import com.g41.trashsmart_server.Repositories.DriverRepository;
import com.g41.trashsmart_server.Repositories.GarbageTruckRepository;
import com.g41.trashsmart_server.Repositories.HouseholdDispatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HouseholdDispatchService {

    @Autowired
    private HouseholdDispatchRepository householdDispatchRepository;

    @Autowired
    private GarbageTruckRepository garbageTruckRepository;

    @Autowired
    private DriverRepository driverRepository;

    public HouseholdDispatch createHouseholdDispatch(HouseholdDispatch dispatch) {
        Optional<Driver> activeDriver = driverRepository.findByStatus(Status.ACTIVE).stream().findFirst();
        Optional<GarbageTruck> idleGarbageTruck = garbageTruckRepository.findByTruckStatus(TruckStatus.IDLE).stream().findFirst();

        DispatchStatus status = (activeDriver.isPresent() && idleGarbageTruck.isPresent()) ? DispatchStatus.NEW : DispatchStatus.PENDING;
        Driver driver = activeDriver.orElse(null);
        GarbageTruck garbageTruck = idleGarbageTruck.orElse(null);

        dispatch.setDispatchStatus(status);
        dispatch.setDriver(driver);
        dispatch.setGarbageTruck(garbageTruck);
        householdDispatchRepository.save(dispatch);

        return dispatch;
    }

//    public HouseholdDispatch createHouseholdDispatch(LocalDateTime dateTime, GarbageTruck garbageTruck, WasteType wasteType, String route) {
//        Optional<Driver> activeDriver = driverRepository.findByStatus(Status.ACTIVE).stream().findFirst();
//        DispatchStatus status = activeDriver.isPresent() ? DispatchStatus.NEW : DispatchStatus.PENDING;
//        Driver driver = activeDriver.orElse(null);
//
//        HouseholdDispatch dispatch = new HouseholdDispatch(dateTime, garbageTruck, driver, wasteType, route);
//        dispatch.setDispatchStatus(status);
//        householdDispatchRepository.save(dispatch);
//
//        return dispatch;
//    }

    public HouseholdDispatch updateHouseholdDispatch(HouseholdDispatch dispatch) {
        return householdDispatchRepository.save(dispatch);
    }

    public HouseholdDispatch getHouseholdDispatch(Long id) {
        return householdDispatchRepository.findById(id).orElse(null);
    }

    public HouseholdDispatch updateHouseholdDispatchStatus(Long id, DispatchStatus status) {
        HouseholdDispatch dispatch = householdDispatchRepository.findById(id).orElse(null);
        if (dispatch != null) {
            dispatch.setDispatchStatus(status);
            householdDispatchRepository.save(dispatch);
        }
        return dispatch;
    }

    public void deleteHouseholdDispatch(Long id) {
        householdDispatchRepository.deleteById(id);
    }

    @Scheduled(cron = "0 0 0 * * MON") // Schedule to run every Monday at midnight
    public void scheduleWeeklyDispatches() {
        LocalDateTime nextWeek = LocalDateTime.now().plusWeeks(1);
        householdDispatchRepository.findAll().forEach(dispatch -> {
            HouseholdDispatch newDispatch = new HouseholdDispatch(nextWeek, dispatch.getGarbageTruck(), dispatch.getDriver(), dispatch.getWasteType(), dispatch.getRoute());
            householdDispatchRepository.save(newDispatch);
        });
    }

    @Scheduled(fixedRate = 60000)
    public void updateDispatchStatus() {
        LocalDateTime now = LocalDateTime.now();
        householdDispatchRepository.findAll().forEach(dispatch -> {
            if (dispatch.getDateTime().isBefore(now) && dispatch.getDispatchStatus() == DispatchStatus.NEW) {
                dispatch.setDispatchStatus(DispatchStatus.DISPATCHED);
                householdDispatchRepository.save(dispatch);
            }
        });
    }


    public List<HouseholdDispatch> getAllHouseholdDispatches() {
        return householdDispatchRepository.findAll();
    }

    // get dispatches based on driver id and status
    public List<HouseholdDispatch> getHouseholdDispatchByDriverStatus(Long driver_id, DispatchStatus dispatchStatus) {
        return householdDispatchRepository.getHouseholdDispatchByDriverStatus(driver_id, dispatchStatus);
    }
}
