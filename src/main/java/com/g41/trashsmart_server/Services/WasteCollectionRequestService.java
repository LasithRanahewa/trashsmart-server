package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.Enums.WasteCollectionRequestStatus;
import com.g41.trashsmart_server.Models.Organization;
import com.g41.trashsmart_server.Models.WasteCollectionRequest;
import com.g41.trashsmart_server.Repositories.OrganizationRepository;
import com.g41.trashsmart_server.Repositories.WasteCollectionRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WasteCollectionRequestService {
    private final WasteCollectionRequestRepository wasteCollectionRequestRepository;
    private final OrganizationRepository organizationRepository;

    public WasteCollectionRequestService(WasteCollectionRequestRepository wasteCollectionRequestRepository,
                                         OrganizationRepository organizationRepository) {
        this.wasteCollectionRequestRepository = wasteCollectionRequestRepository;
        this.organizationRepository = organizationRepository;
    }

    // Retrieve all waste collection requests
    public List<WasteCollectionRequest> getAllRequests() {
        return wasteCollectionRequestRepository.findAll();
    }

    // Retrieve all the waste collection requests opened by a given organization
    public List<WasteCollectionRequest> getAllRequestsByOrganization(Long organizationId, Integer status) {
        WasteCollectionRequestStatus wasteCollectionRequestStatus = switch (status) {
            case 1 -> WasteCollectionRequestStatus.NEW;
            case 2 -> WasteCollectionRequestStatus.COLLECTING;
            case 3 -> WasteCollectionRequestStatus.COLLECTED;
            case 4 -> WasteCollectionRequestStatus.MISSED;
            default -> throw new IllegalStateException("Wrong Waste Collection Request Status");
        };
        return wasteCollectionRequestRepository.findByOrganizationId(organizationId, wasteCollectionRequestStatus);
    }

    // Retrieve all the waste collection requests opened by a given organization
    public List<WasteCollectionRequest> getAllRequestsByOrganizationWOStatus(Long id) {
        return wasteCollectionRequestRepository.findByOrganizationIdWOStatus(id);
    }

    // Retrieve specific by id
    public WasteCollectionRequest getRequest(Long id) {
        Optional<WasteCollectionRequest> wasteCollectionRequestOptional = wasteCollectionRequestRepository.findById(id);
        if (wasteCollectionRequestOptional.isEmpty()) {
           throw new IllegalStateException("Waste Collection Request with id " + id + " does not exist");
        }
        return wasteCollectionRequestOptional.get();
    }

    // Create a new waste collection request
    public void createRequest(WasteCollectionRequest wasteCollectionRequest, Long organizationId) {
        Optional<Organization> organizationOptional = organizationRepository.findById(organizationId);
        if (organizationOptional.isEmpty()) {
            throw new IllegalStateException("Organization with id " + organizationId + " does not exist");
        }
        Organization organization = organizationOptional.get();
        wasteCollectionRequest.setOrganization(organization);
        wasteCollectionRequestRepository.save(wasteCollectionRequest);
    }

    // Delete a waste collection request
    public void deleteRequest(Long id) {
        Optional<WasteCollectionRequest> wasteCollectionRequestOptional = wasteCollectionRequestRepository.findById(id);
        if (wasteCollectionRequestOptional.isEmpty()) {
            throw new IllegalStateException("Waste Collection Request with id " + id + " does not exist");
        }
        wasteCollectionRequestRepository.deleteById(id);
    }

    // Update a waste collection request
    public void updateRequest(Long id, WasteCollectionRequest wasteCollectionRequest) {
        WasteCollectionRequest wasteCollectionRequestToUpdate = wasteCollectionRequestRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Waste Collection Request with id " + id + " does not exist")
        );
        if (wasteCollectionRequest.getAccumulatedVolume() != null &&
                !wasteCollectionRequestToUpdate.getAccumulatedVolume().equals(wasteCollectionRequest.getAccumulatedVolume())) {
            wasteCollectionRequestToUpdate.setAccumulatedVolume(wasteCollectionRequest.getAccumulatedVolume());
        }
        if (wasteCollectionRequest.getWasteType() != null &&
                !wasteCollectionRequestToUpdate.getWasteType().equals(wasteCollectionRequest.getWasteType())) {
            wasteCollectionRequestToUpdate.setWasteType(wasteCollectionRequest.getWasteType());
        }
        if (wasteCollectionRequest.getLongitude() != null &&
                !wasteCollectionRequestToUpdate.getLongitude().equals(wasteCollectionRequest.getLongitude())) {
            wasteCollectionRequestToUpdate.setLongitude(wasteCollectionRequest.getLongitude());
        }
        if (wasteCollectionRequest.getLatitude() != null &&
                !wasteCollectionRequestToUpdate.getLatitude().equals(wasteCollectionRequest.getLatitude())) {
            wasteCollectionRequestToUpdate.setLatitude(wasteCollectionRequest.getLatitude());
        }
        if (wasteCollectionRequest.getWasteCollectionRequestStatus() != null &&
                !wasteCollectionRequestToUpdate.getWasteCollectionRequestStatus()
                        .equals(wasteCollectionRequest.getWasteCollectionRequestStatus())) {
            wasteCollectionRequestToUpdate.setWasteCollectionRequestStatus(
                    wasteCollectionRequest.getWasteCollectionRequestStatus()
            );
        }
        wasteCollectionRequestRepository.save(wasteCollectionRequestToUpdate);
    }
}
