package com.g41.trashsmart_server.DTO;

import com.g41.trashsmart_server.Enums.Role;
import com.g41.trashsmart_server.Enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO extends DriverDTOMapper {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNo;
    private String address;
    private Role role;
    private String profileURL;
    private LocalDate dob;
    private String nic;
    private Status status;
    private Integer totalCollections;
    private Integer currentStreak;
    private Integer longestStreak;
    private Integer totalActiveDays;
    private Integer numberOfHolidays;
    private boolean isDeleted;
}
