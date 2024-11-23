package com.g41.trashsmart_server.DTO;

import com.g41.trashsmart_server.Enums.Role;
import com.g41.trashsmart_server.Enums.Status;
import com.g41.trashsmart_server.Models.CommunalBin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CleanerDTO {
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
    private LocalDate lastCollectionDate;
    private Integer totalActiveDays;
    private Integer numberOfHolidays;
    private boolean isDeleted;
    private List<CommunalBin> communalBins;
}
