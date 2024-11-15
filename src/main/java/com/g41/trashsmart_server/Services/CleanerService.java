package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.Configuration.SMTPGmailSenderService;
import com.g41.trashsmart_server.Configuration.SecurityConfig;
import com.g41.trashsmart_server.DTO.CleanerDTO;
import com.g41.trashsmart_server.DTO.CleanerDTOMapper;
import com.g41.trashsmart_server.Enums.Role;
import com.g41.trashsmart_server.Enums.Status;
import com.g41.trashsmart_server.Models.Cleaner;
import com.g41.trashsmart_server.Repositories.CleanerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CleanerService {

    private final CleanerRepository cleanerRepository;

    @Autowired
    private CleanerDTOMapper cleanerDTOMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private SMTPGmailSenderService smtpGmailSenderService;

    @Autowired
    public CleanerService(CleanerRepository cleanerRepository) {
        this.cleanerRepository = cleanerRepository;
    }


    //Get active cleaners
    public List<CleanerDTO> getCleaners() {
        List<Cleaner> cleaners = cleanerRepository.findCleaners(false);
        return cleaners.stream()
                .map(cleanerDTOMapper)
                .collect(Collectors.toList());
    }

    //Get logically deleted cleaners
    public List<CleanerDTO> getDeletedCleaners() {
        List<Cleaner> cleaners = cleanerRepository.findCleaners(true);
        return cleaners.stream()
                .map(cleanerDTOMapper)
                .collect(Collectors.toList());
    }

    //Get all cleaners
    public List<CleanerDTO> getAllCleaners() {
        List<Cleaner> cleaners = cleanerRepository.findAllCleanersUnfiltered();
        return cleaners.stream()
                .map(cleanerDTOMapper)
                .collect(Collectors.toList());
    }

    //Get cleaners all details
    public List<Cleaner> getCleanersAdmin() {
        return cleanerRepository.findAll();
    }

    public CleanerDTO getSpecificCleaner(Long cleanerId) {
        Optional<Cleaner> cleanerOptional = cleanerRepository.findById(cleanerId);
        if (cleanerOptional.isEmpty()) {
            throw new IllegalStateException("Cleaner with id " + cleanerId + " does not exist");
        }
        return cleanerDTOMapper.apply(cleanerOptional.get());
    }

    //Add new cleaner
    public void addNewCleaner(Cleaner cleaner) {
        Optional<Cleaner> existingByEmail = cleanerRepository.findByEmail(cleaner.getEmail());
        Optional<Cleaner> existingByContactNo = cleanerRepository.findByContactNo(cleaner.getContactNo());

        if (existingByEmail.isPresent()) {
            throw new IllegalStateException("Cleaner with the email " + cleaner.getEmail() + " already exist.");
        }
        if (existingByContactNo.isPresent()) {
            throw new IllegalStateException("Cleaner with the contact No. " + cleaner.getContactNo() + " already exist.");
        }

//        if (cleaner.getPassword() == null || cleaner.getPassword().isEmpty()) {
//            cleaner.setPassword(securityConfig.generateRandomPassword(10));
//
//            String subject = "TrashSmart Account Created";
//            String body = "Hello, " + cleaner.getFirstName() + "!\n" +
//                    "Your TrashSmart account has been created successfully.\n" +
//                    "Your login credentials are as follows:\n" +
//                    "Email: " + cleaner.getEmail() + "\n" +
//                    "Password: " + cleaner.getPassword() + "\n" +
//                    "Please change your password after logging in.\n" +
//                    "Thank you for choosing TrashSmart!";
//            smtpGmailSenderService.sendEmail(cleaner.getEmail(), subject, body);
//        } else {
//            cleaner.setPassword(passwordEncoder.encode(cleaner.getPassword()));
//        }

        cleaner.setRole(Role.CLEANER);
        cleaner.setStatus(Status.ACTIVE);

        cleanerRepository.save(cleaner);
    }

    //Logically delete a cleaner
    public void deleteCleaner(Long cleanerId) {
        Optional<Cleaner> cleanerOptional = cleanerRepository.findById(cleanerId);

        if (cleanerOptional.isEmpty()) {
            throw new IllegalStateException("Cleaner with id " + cleanerId + " does not exist");
        }

        Cleaner cleanerToDelete = cleanerOptional.get();
        cleanerToDelete.setDeleted(true);
        cleanerToDelete.setStatus(Status.UNAVAILABLE);
        cleanerRepository.save(cleanerToDelete);
    }

    //Permanently delete a cleaner
    public void deletePermanentCleaner(Long cleanerId) {
        Optional<Cleaner> cleanerOptional = cleanerRepository.findById(cleanerId);

        if (cleanerOptional.isEmpty()) {
            throw new IllegalStateException("Cleaner with id " + cleanerId + " does not exist");
        }

        cleanerRepository.delete(cleanerOptional.get());
    }

    //Update a cleaner
    public void updateCleaner(Long cleanerId, Cleaner cleaner) {
        Optional<Cleaner> cleanerOptional = cleanerRepository.findById(cleanerId);

        if (cleanerOptional.isEmpty()) {
            throw new IllegalStateException("Cleaner with id " + cleanerId + " does not exist");
        }

        Cleaner cleanerToUpdate = cleanerOptional.get();

        if (cleaner.getFirstName() != null && !cleanerToUpdate.getFirstName().equals(cleaner.getFirstName())) {
            cleanerToUpdate.setFirstName(cleaner.getFirstName());
        }
        if (cleaner.getLastName() != null && !cleanerToUpdate.getLastName().equals(cleaner.getLastName())) {
            cleanerToUpdate.setLastName(cleaner.getLastName());
        }
        if (cleaner.getEmail() != null && !cleanerToUpdate.getEmail().equals(cleaner.getEmail())) {
            Optional<Cleaner> existingByEmail = cleanerRepository.findByEmail(cleaner.getEmail());
            if (existingByEmail.isPresent()) {
                throw new IllegalStateException("Recycling Plant with email " + cleaner.getEmail() + " already exists.");
            }
            cleanerToUpdate.setEmail(cleaner.getEmail());
        }
        if (cleaner.getAddress() != null && !cleanerToUpdate.getAddress().equals(cleaner.getAddress())) {
            cleanerToUpdate.setAddress(cleaner.getAddress());
        }
        if (cleaner.getContactNo() != null && !cleanerToUpdate.getContactNo().equals(cleaner.getContactNo())) {
            cleanerToUpdate.setContactNo(cleaner.getContactNo());
        }
        if (cleaner.getProfileURL() != null && !cleanerToUpdate.getProfileURL().equals(cleaner.getProfileURL())) {
            cleanerToUpdate.setProfileURL(cleaner.getProfileURL());
        }
        if (cleaner.getDob() != null && !cleanerToUpdate.getDob().equals(cleaner.getDob())) {
            cleanerToUpdate.setDob(cleaner.getDob());
        }
        if (cleaner.getNic() != null && !cleanerToUpdate.getNic().equals(cleaner.getNic())) {
            cleanerToUpdate.setNic(cleaner.getNic());
        }
        if (cleaner.getStatus() != null && !cleanerToUpdate.getStatus().equals(cleaner.getStatus())) {
            cleanerToUpdate.setStatus(cleaner.getStatus());
        }
        if (cleaner.getPassword() != null && !cleanerToUpdate.getPassword().equals(cleaner.getPassword())) {
            cleanerToUpdate.setPassword(passwordEncoder.encode(cleaner.getPassword()));
        }
        if (cleaner.getCommunalBins() != null && !cleanerToUpdate.getCommunalBins().equals(cleaner.getCommunalBins())) {
            cleanerToUpdate.setCommunalBins(cleaner.getCommunalBins());
        }

        cleanerRepository.save(cleanerToUpdate);
    }
}
