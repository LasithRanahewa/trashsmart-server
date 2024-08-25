package com.g41.trashsmart_server.DTO;

import com.g41.trashsmart_server.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private LocalDate dob;
    private String nic;
}
