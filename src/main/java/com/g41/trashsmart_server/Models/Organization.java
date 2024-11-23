package com.g41.trashsmart_server.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.g41.trashsmart_server.Enums.Role;
import com.g41.trashsmart_server.Enums.Scale;
import com.g41.trashsmart_server.Enums.OrgType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table
public class Organization extends BusinessUser implements UserDetails {
    private Integer weeklyWaste = 0;
    private Integer totalWaste = 0;
    private Integer recyclableWaste = 0;
    private Scale scale;
    private OrgType orgType;
    private LocalDate contractStartDate = LocalDate.now();
    private LocalDate contractEndDate;
    @OneToMany(mappedBy = "organization")
    @JsonManagedReference
    private List<CommercialBin> commercialBins;
    @OneToMany(mappedBy = "organization")
    @JsonManagedReference(value = "organization-back-ref")
    private List<WasteCollectionRequest> wasteCollectionRequests;

    public Organization() {
        this.setRole(Role.ORGANIZATION);
    }

    public Organization(String firstName, String lastName, String email, String password, String contactNo,
                        String address, String profileURL, Scale scale, OrgType orgType) {
        super(firstName, lastName, email, password, contactNo, address, profileURL, LocalDateTime.now());
        this.scale = scale;
        this.orgType = orgType;
        this.setRole(Role.ORGANIZATION);
    }

    // Getters and Setters
    public Integer getWeeklyWaste() {
        return weeklyWaste;
    }

    public void setWeeklyWaste(Integer weeklyWaste) {
        this.weeklyWaste = weeklyWaste;
    }

    public Integer getTotalWaste() {
        return totalWaste;
    }

    public void setTotalWaste(Integer totalWaste) {
        this.totalWaste = totalWaste;
    }

    public Integer getRecyclableWaste() {
        return recyclableWaste;
    }

    public void setRecyclableWaste(Integer recyclableWaste) {
        this.recyclableWaste = recyclableWaste;
    }

    public Scale getScale() {
        return scale;
    }

    public void setScale(Scale scale) {
        this.scale = scale;
    }

    public OrgType getOrgType() {
        return orgType;
    }

    public void setOrgType(OrgType orgType) {
        this.orgType = orgType;
    }

    public LocalDate getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(LocalDate contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public LocalDate getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(LocalDate contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public List<CommercialBin> getCommercialBins() {
        return commercialBins;
    }

    public void setCommercialBins(List<CommercialBin> commercialBins) {
        this.commercialBins = commercialBins;
    }

    public List<WasteCollectionRequest> getWasteCollectionRequests() {
        return wasteCollectionRequests;
    }

    public void setWasteCollectionRequests(List<WasteCollectionRequest> wasteCollectionRequests) {
        this.wasteCollectionRequests = wasteCollectionRequests;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
