package com.g41.trashsmart_server.DTO;
import com.g41.trashsmart_server.Enums.BinSize;
import com.g41.trashsmart_server.Enums.BinStatus;
import com.g41.trashsmart_server.Enums.WasteType;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmartBinDTO {

    private Long id;
    private Double longitude;
    private Double latitude;
    private LocalDate lastMaintenanceDate;
    private Double fillLevel;
    private BinStatus binStatus;
    private WasteType wasteType;
    private LocalDate lastCollectionDate;
    private BinSize binSize;
    private Boolean deleted;
    private String binAPI;
}
