package com.g41.trashsmart_server.DTO;

import com.g41.trashsmart_server.Enums.OrgType;
import com.g41.trashsmart_server.Enums.Role;
import com.g41.trashsmart_server.Enums.Scale;
import com.g41.trashsmart_server.Models.CommercialBin;
import com.g41.trashsmart_server.Models.WasteCollectionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNo;
    private String address;
    private Role role;
    private String profileURL;
    private Integer weeklyWaste;
    private Integer totalWaste;
    private Integer recyclableWaste;
    private Scale scale;
    private OrgType orgType;
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;
    private List<CommercialBin> commercialBins;
    private List<WasteCollectionRequest> wasteCollectionRequests;
}
