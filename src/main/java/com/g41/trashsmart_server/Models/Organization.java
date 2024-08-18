package com.g41.trashsmart_server.Models;

import com.g41.trashsmart_server.Enums.Role;
import com.g41.trashsmart_server.Enums.Scale;
import com.g41.trashsmart_server.Enums.OrgType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table
public class Organization extends BusinessUser {
    private Integer weeklyWaste = 0;
    private Integer totalWaste = 0;
    private Integer recyclableWaste = 0;
    private Scale scale;
    private OrgType orgType;
    private LocalDate contractStartDate = LocalDate.now();
    private LocalDate contractEndDate;

    public Organization() {
    }

    public Organization(String firstName, String lastName, String email, String password, String contactNo,
                        String address, Role role, String profileURL, LocalDateTime createdTimeStamp) {
        super(firstName, lastName, email, password, contactNo, address, role, profileURL, createdTimeStamp);
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

    public OrgType getType() {
        return orgType;
    }

    public void setType(OrgType orgType) {
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
}