package com.g41.trashsmart_server.DTO;

import com.g41.trashsmart_server.Enums.Role;
import com.g41.trashsmart_server.Models.Suburb;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseholdUserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNo;
    private String address;
    private Role role;
    private String profileURL;
    private Suburb suburb;
}
