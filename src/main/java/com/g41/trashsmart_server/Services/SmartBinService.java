package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.Configuration.SMTPGmailSenderService;
import com.g41.trashsmart_server.Configuration.SecurityConfig;
import com.g41.trashsmart_server.DTO.OrganizationDTO;
import com.g41.trashsmart_server.DTO.SmartBinDTO;
import com.g41.trashsmart_server.DTO.SmartBinDTOMapper;
import com.g41.trashsmart_server.Models.Organization;
import com.g41.trashsmart_server.Models.SmartBin;
import com.g41.trashsmart_server.Repositories.SmartBinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SmartBinService {
    private final SmartBinRepository smartBinRepository;
    private final SmartBinDTOMapper smartBinDTOMapper;

    @Autowired
    public SmartBinService(SmartBinRepository smartBinRepository, SmartBinDTOMapper smartBinDTOMapper) {
        this.smartBinRepository = smartBinRepository;
        this.smartBinDTOMapper = smartBinDTOMapper;
    }
    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private SMTPGmailSenderService smtpGmailSenderService;

    // Retrieve all active smart bins
   
    public List<SmartBinDTO> getSmartBins() {
        List<SmartBin> smartBins = smartBinRepository.findAllSmartBins(false);
        return smartBins.stream()
                .map(smartBinDTOMapper)
                .collect(Collectors.toList());
    }
    // Add a new smart bin
    public void addSmartBin(SmartBin smartBin) {
        String apiKey = generateUniqueApiKey();
        smartBin.setBinAPI(apiKey);
        smartBinRepository.save(smartBin);
    }

    private String generateUniqueApiKey() {
        String apiKey;
        do {
            apiKey = generateApiKey();
        } while (smartBinRepository.existsByApiKey(apiKey,false)); // Check uniqueness
        return apiKey;
    }

    private String generateApiKey() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    // Update bin location, status, fill level
   @Transactional
   public void updateSmartBin(Long id, String apiKey, SmartBin updateDetails) {
    SmartBin existingBin = smartBinRepository.findSmartBinById(id, false)
            .orElseThrow(() -> new IllegalArgumentException("SmartBin not found"));

    if (!existingBin.getBinAPI().equals(apiKey)) {
        throw new IllegalArgumentException("Invalid API Key");
    }

    // Apply updates from updateDetails to existingBin
    updateSmartBinDetails(existingBin, updateDetails);

    smartBinRepository.save(existingBin);
}
private void updateSmartBinDetails(SmartBin existingBin, SmartBin updateDetails) {
    existingBin.setLongitude(updateDetails.getLongitude());
    existingBin.setLatitude(updateDetails.getLatitude());
    existingBin.setFillLevel(updateDetails.getFillLevel());
    existingBin.setBinStatus(updateDetails.getBinStatus());
    // Add other fields that need to be updated
}

// Delete a smart bin
@Transactional
public void deleteSmartBin(Long id, String apiKey) {
    SmartBin existingBin = smartBinRepository.findSmartBinById(id, false)
            .orElseThrow(() -> new IllegalArgumentException("SmartBin not found"));

    if (!existingBin.getBinAPI().equals(apiKey)) {
        throw new IllegalArgumentException("Invalid API Key");
    }

    existingBin.setDeleted(true);
    smartBinRepository.save(existingBin);
}
// Get smart bin by id
public SmartBinDTO getSmartBinById(Long id) {
    Optional<SmartBin> smartBin = smartBinRepository.findSmartBinById(id,false);
    if (smartBin.isEmpty()) {
        throw new IllegalArgumentException("SmartBin not found");
    }
    return smartBinDTOMapper.apply(smartBin.get());
}
}
