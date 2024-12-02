package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.Configuration.SMTPGmailSenderService;
import com.g41.trashsmart_server.Configuration.SecurityConfig;
import com.g41.trashsmart_server.DTO.OrganizationDTO;
import com.g41.trashsmart_server.DTO.OrganizationDTOMapper;
import com.g41.trashsmart_server.Models.Organization;
import com.g41.trashsmart_server.Repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final OrganizationDTOMapper organizationDTOMapper;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository,
                               OrganizationDTOMapper organizationDTOMapper) {
        this.organizationRepository = organizationRepository;
        this.organizationDTOMapper = organizationDTOMapper;
    }

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private SMTPGmailSenderService smtpGmailSenderService;

    // Retrieve all active organizations
    public List<OrganizationDTO> getOrganizations() {
        List<Organization> organizations = organizationRepository.findAllOrganizations(false);
        return organizations.stream()
                .map(organizationDTOMapper)
                .collect(Collectors.toList());
    }

    // Retrieve all logically deleted organizations
    public List<OrganizationDTO> getDeletedOrganizations() {
        List<Organization> organizations = organizationRepository.findAllOrganizations(true);
        return organizations.stream()
                .map(organizationDTOMapper)
                .collect(Collectors.toList());
    }

    // Retrieve all registered organizations
    public List<OrganizationDTO> getAllOrganizations() {
        List<Organization> organizations = organizationRepository.findAllOrganizationsUnFiltered();
        return organizations.stream()
                .map(organizationDTOMapper)
                .collect(Collectors.toList());
    }

    // Retrieve all the organization details (Admin Privilege)
    public List<Organization> getOrganizationsAdmin() {
        return organizationRepository.findAll();
    }

    // Retrieve a specific organization given the id (logically active)
    public OrganizationDTO getSpecificOrganization(Long id) {
        Optional<Organization> organizationOptional = organizationRepository.findOrganizationById(id, false);
        if(organizationOptional.isEmpty()) {
            throw new IllegalStateException("Organization with id " + id + " does not exist");
        }
        return organizationDTOMapper.apply(organizationOptional.get());
    }

    // Create a new organization
    public void addNewOrganization(Organization organization) {
        Optional<Organization> organizationOptional = organizationRepository.findOrganizationByEmail(
                organization.getEmail()
        );
        if (organizationOptional.isPresent()) {
            throw new IllegalStateException("Email Taken");
        }
        if (organization.getPassword() == null || organization.getPassword().isEmpty()) {
            organization.setPassword(securityConfig.generateRandomPassword(10));

            String subject = "TrashSmart Account Created";
            String body = "Hello, " + organization.getFirstName() + "!\n" +
                    "Your TrashSmart account has been created successfully.\n" +
                    "Your login credentials are as follows:\n" +
                    "Email: " + organization.getEmail() + "\n" +
                    "Password: " + organization.getPassword() + "\n" +
                    "Please change your password after logging in.\n" +
                    "Thank you for choosing TrashSmart!";
            smtpGmailSenderService.sendEmail(organization.getEmail(), subject, body);
        }
        organizationRepository.save(organization);
    }

    // Logically delete a organization from the system
    public void deleteOrganization(Long userId) {
        Optional<Organization> organizationOptional = organizationRepository.findById(userId);
        if(organizationOptional.isEmpty()) {
            throw new IllegalStateException("Organization with id " + userId + " does not exist");
        }
        Organization organizationToDelete = organizationOptional.get();
        organizationToDelete.setDeleted(true);
        organizationRepository.save(organizationToDelete);
    }

    // Permanently delete a organization from the system
    public void deletePermanentOrganization(Long userId) {
        boolean exists = organizationRepository.existsById(userId);
        if(!exists) {
            throw new IllegalStateException("Organization with id " + userId + " does not exist");
        }
        organizationRepository.deleteById(userId);
    }

    // Update organization details
    public void updateOrganization(Long id, Organization organization) {
        Organization organizationToUpdate = organizationRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Organization with id " + id + " does not exist")
        );
        if (organization.getFirstName() != null && !organization.getFirstName().isEmpty() &&
                !organizationToUpdate.getFirstName().equals(organization.getFirstName())) {
            organizationToUpdate.setFirstName(organization.getFirstName());
        }
        if (organization.getLastName() != null && !organization.getLastName().isEmpty() &&
                !organizationToUpdate.getLastName().equals(organization.getLastName())) {
            organizationToUpdate.setLastName(organization.getLastName());
        }
        if (organization.getEmail() != null && !organization.getEmail().isEmpty() &&
                !organizationToUpdate.getEmail().equals(organization.getEmail())) {
            Optional<Organization> organizationOptional = organizationRepository.findOrganizationByEmail(
                    organization.getEmail()
            );
            if(organizationOptional.isPresent()) {
                throw new IllegalStateException("Email Taken");
            }
            organizationToUpdate.setEmail(organization.getEmail());
        }
        if (organization.getPassword() != null && !organization.getPassword().isEmpty() &&
                !organizationToUpdate.getPassword().equals(organization.getPassword())) {
            organizationToUpdate.setPassword(organization.getPassword());
        }
        if (organization.getAddress() != null && !organization.getAddress().isEmpty() &&
                !organizationToUpdate.getAddress().equals(organization.getAddress())) {
            organizationToUpdate.setAddress(organization.getAddress());
        }
        if (organization.getContactNo() != null && !organization.getContactNo().isEmpty() &&
                !organizationToUpdate.getContactNo().equals(organization.getContactNo())) {
            organizationToUpdate.setContactNo(organization.getContactNo());
        }
        if (organization.getRole() != null && !organizationToUpdate.getRole().equals(organization.getRole())) {
            organizationToUpdate.setRole(organization.getRole());
        }
        if (organization.getProfileURL() != null && !organization.getProfileURL().isEmpty() &&
                !organizationToUpdate.getProfileURL().equals(organization.getProfileURL())) {
            organizationToUpdate.setProfileURL(organization.getProfileURL());
        }
        if (organization.getWeeklyWaste() != null &&
                !organizationToUpdate.getWeeklyWaste().equals(organization.getWeeklyWaste())) {
            organizationToUpdate.setWeeklyWaste(organization.getWeeklyWaste());
        }
        if (organization.getTotalWaste() != null &&
                !organizationToUpdate.getTotalWaste().equals(organization.getTotalWaste())) {
            organizationToUpdate.setTotalWaste(organization.getTotalWaste());
        }
        if (organization.getRecyclableWaste() != null &&
                !organizationToUpdate.getRecyclableWaste().equals(organization.getRecyclableWaste())) {
            organizationToUpdate.setRecyclableWaste(organization.getRecyclableWaste());
        }
        if (organization.getScale() != null && !organizationToUpdate.getScale().equals(organization.getScale())) {
            organizationToUpdate.setScale(organization.getScale());
        }
        if (organization.getOrgType() != null && !organizationToUpdate.getOrgType().equals(organization.getOrgType())) {
            organizationToUpdate.setOrgType(organization.getOrgType());
        }
        if (organization.getContractStartDate() != null &&
                !organizationToUpdate.getContractStartDate().equals(organization.getContractStartDate())) {
            organizationToUpdate.setContractStartDate(organization.getContractStartDate());
        }
        if (organization.getContractEndDate() != null &&
                !organizationToUpdate.getContractEndDate().equals(organization.getContractEndDate())) {
            organizationToUpdate.setContractEndDate(organization.getContractEndDate());
        }
        if (organization.getLatitude() != null &&
                !organizationToUpdate.getLatitude().equals(organization.getLatitude())) {
            organizationToUpdate.setLatitude(organization.getLatitude());
        }
        if (organization.getLongitude() != null &&
                !organizationToUpdate.getLongitude().equals(organization.getLongitude())) {
            organizationToUpdate.setLongitude(organization.getLongitude());
        }
        if (organization.getCommercialBins() != null && !organization.getCommercialBins().isEmpty() &&
                !organizationToUpdate.getCommercialBins().equals(organization.getCommercialBins())) {
            organizationToUpdate.setCommercialBins(organization.getCommercialBins());
        }
        if (organization.getWasteCollectionRequests() != null && !organization.getWasteCollectionRequests().isEmpty() &&
                !organizationToUpdate.getWasteCollectionRequests().equals(organization.getWasteCollectionRequests())) {
            organizationToUpdate.setWasteCollectionRequests(organization.getWasteCollectionRequests());
        }
        organizationRepository.save(organizationToUpdate);
    }
}
