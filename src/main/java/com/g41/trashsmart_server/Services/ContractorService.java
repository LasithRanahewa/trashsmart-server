package com.g41.trashsmart_server.Services;

import com.g41.trashsmart_server.DTO.ContractorDTO;
import com.g41.trashsmart_server.DTO.ContractorDTOMapper;
import com.g41.trashsmart_server.Enums.Role;
import com.g41.trashsmart_server.Models.Contractor;
import com.g41.trashsmart_server.Repositories.ContractorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractorService {
    private final ContractorRepository contractorRepository;
    private final ContractorDTOMapper contractorDTOMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ContractorService(ContractorRepository contractorRepository,
                             ContractorDTOMapper contractorDTOMapper) {
        this.contractorRepository = contractorRepository;
        this.contractorDTOMapper = contractorDTOMapper;
    }

    // Retrieve all active contractors
    public List<ContractorDTO> getContractors() {
        List<Contractor> contractors = contractorRepository.findAllContractors(false);
        return contractors.stream()
                .map(contractorDTOMapper)
                .collect(Collectors.toList());
    }

    // Retrieve all logically deleted contractors
    public List<ContractorDTO> getDeletedContractors() {
        List<Contractor> contractors = contractorRepository.findAllContractors(true);
        return contractors.stream()
                .map(contractorDTOMapper)
                .collect(Collectors.toList());
    }

    // Retrieve all registered contractors
    public List<ContractorDTO> getAllContractors() {
        List<Contractor> contractors = contractorRepository.findAllContractorsUnFiltered();
        return contractors.stream()
                .map(contractorDTOMapper)
                .collect(Collectors.toList());
    }

    // Retrieve all the contractor details (Admin Privilege)
    public List<Contractor> getContractorsAdmin() {
        return contractorRepository.findAll();
    }

    // Retrieve a specific contractor given the id
    public ContractorDTO getSpecificContractor(Long id) {
        Optional<Contractor> contractorOptional = contractorRepository.findById(id);
        if(contractorOptional.isEmpty()) {
            throw new IllegalStateException("Contractor with id " + id + " does not exists");
        }
        return contractorDTOMapper.apply(contractorOptional.get());
    }

    // Create a new contractor
    public void addNewContractor(Contractor contractor) {
        contractor.setPassword(passwordEncoder.encode(contractor.getPassword()));
        contractor.setRole(Role.CONTRACTOR);

        Optional<Contractor> contractorOptional = contractorRepository.findContractorByEmail(
                contractor.getEmail()
        );
        if(contractorOptional.isPresent()) {
            throw new IllegalStateException("Email Taken");
        }
        contractorRepository.save(contractor);
    }

    // Logically delete a contractor from the system
    public void deleteContractor(Long contractorId) {
        Optional<Contractor> contractorOptional = contractorRepository.findById(contractorId);
        if(contractorOptional.isEmpty()) {
            throw new IllegalStateException("Contractor with id " + contractorId + " does not exists");
        }
        Contractor contractorToDelete = contractorOptional.get();
        contractorToDelete.setDeleted(true);
        contractorRepository.save(contractorToDelete);
    }

    // Permanently delete a contractor from the system
    public void deletePermanentContractor(Long contractorId) {
        boolean exists = contractorRepository.existsById(contractorId);
        if(!exists) {
            throw new IllegalStateException("Contractor with id " + contractorId + " does not exists");
        }
        contractorRepository.deleteById(contractorId);
    }

    // Update contractor details
    public void updateContractor(Long id, Contractor contractor) {
        Contractor contractorToUpdate = contractorRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Contractor with id " + id + " does not exists")
        );
        if (contractor.getFirstName() != null && !contractor.getFirstName().isEmpty() &&
                !contractorToUpdate.getFirstName().equals(contractor.getFirstName())) {
            contractorToUpdate.setFirstName(contractor.getFirstName());
        }
        if (contractor.getLastName() != null && !contractor.getLastName().isEmpty() &&
                !contractorToUpdate.getLastName().equals(contractor.getLastName())) {
            contractorToUpdate.setLastName(contractor.getLastName());
        }
        if (contractor.getEmail() != null && !contractor.getEmail().isEmpty() &&
                !contractorToUpdate.getEmail().equals(contractor.getEmail())) {
            Optional<Contractor> contractorOptional = contractorRepository.findContractorByEmail(
                    contractor.getEmail()
            );
            if (contractorOptional.isPresent()) {
                throw new IllegalStateException("Email Taken");
            }
            contractorToUpdate.setEmail(contractor.getEmail());
        }
        if (contractor.getRole() != null && !contractorToUpdate.getRole().equals(contractor.getRole())) {
            contractorToUpdate.setRole(contractor.getRole());
        }
        if (contractor.getDob() != null && !contractorToUpdate.getDob().equals(contractor.getDob())) {
            contractorToUpdate.setDob(contractor.getDob());
        }
        if (contractor.getNic() != null && !contractorToUpdate.getNic().equals(contractor.getNic())) {
            contractorToUpdate.setNic(contractor.getNic());
        }
        contractorRepository.save(contractorToUpdate);
    }
}