package com.g41.trashsmart_server.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.g41.trashsmart_server.Enums.RequestStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class MaintenanceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdTimeStamp = LocalDateTime.now();
    private RequestStatus requestStatus = RequestStatus.TO_DO;
    @ManyToOne
    @JoinColumn(name = "bin_id")
    @JsonBackReference
    private SmartBin smartBin;
    private String otherNotes;

    public MaintenanceRequest() {
    }

    public MaintenanceRequest(SmartBin smartBin) {
        this.smartBin = smartBin;
    }

    public MaintenanceRequest(SmartBin smartBin, String otherNotes) {
        this.smartBin = smartBin;
        this.otherNotes = otherNotes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(LocalDateTime createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public SmartBin getSmartBin() {
        return smartBin;
    }

    public void setSmartBin(SmartBin smartBin) {
        this.smartBin = smartBin;
    }

    public String getOtherNotes() {
        return otherNotes;
    }

    public void setOtherNotes(String otherNotes) {
        this.otherNotes = otherNotes;
    }
}
