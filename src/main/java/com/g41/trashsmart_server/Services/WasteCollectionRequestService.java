package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.Enums.WasteCollectionRequestStatus;
import com.g41.trashsmart_server.Models.CommercialBin;
import com.g41.trashsmart_server.Models.Organization;
import com.g41.trashsmart_server.Models.WasteCollectionRequest;
import com.g41.trashsmart_server.Repositories.CommercialBinRepository;
import com.g41.trashsmart_server.Repositories.OrganizationRepository;
import com.g41.trashsmart_server.Repositories.WasteCollectionRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class WasteCollectionRequestService {
    private final WasteCollectionRequestRepository wasteCollectionRequestRepository;
    private final OrganizationRepository organizationRepository;
    private final CommercialBinRepository commercialBinRepository;

    public WasteCollectionRequestService(WasteCollectionRequestRepository wasteCollectionRequestRepository,
                                         OrganizationRepository organizationRepository,
                                         CommercialBinRepository commercialBinRepository) {
        this.wasteCollectionRequestRepository = wasteCollectionRequestRepository;
        this.organizationRepository = organizationRepository;
        this.commercialBinRepository = commercialBinRepository;
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

    // Create a new waste collection request, no location given
    public void createRequestNoLocation(WasteCollectionRequest wasteCollectionRequest, Long organizationId) {
        Optional<Organization> organizationOptional = organizationRepository.findById(organizationId);
        if (organizationOptional.isEmpty()) {
            throw new IllegalStateException("Organization with id " + organizationId + " does not exist");
        }
        Organization organization = organizationOptional.get();
        wasteCollectionRequest.setOrganization(organization);
        wasteCollectionRequest.setLatitude(organization.getLatitude());
        wasteCollectionRequest.setLongitude(organization.getLongitude());
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

    // Create a new Waste Collection Request using a commercial bin
    public void createWCRUsingBin(Long bin_id) {
        Optional<CommercialBin> commercialBinOptional = commercialBinRepository.findById(bin_id);
        if (commercialBinOptional.isEmpty()) {
            throw new IllegalStateException("Bin with id " + bin_id + " does not exist");
        }
        CommercialBin commercialBin = commercialBinOptional.get();
        WasteCollectionRequest wasteCollectionRequest = new WasteCollectionRequest(
                commercialBin.getFillLevel() * 50,
                commercialBin.getWasteType(),
                commercialBin.getLatitude(),
                commercialBin.getLongitude()
        );
        wasteCollectionRequestRepository.save(wasteCollectionRequest);

        Organization organization = commercialBin.getOrganization();
        organization.setTotalWaste((int)(organization.getTotalWaste() + wasteCollectionRequest.getAccumulatedVolume()));

        this.organizationRepository.save(organization);
    }
}
