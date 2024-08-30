package com.g41.trashsmart_server.DTO;

import com.g41.trashsmart_server.Enums.TruckStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GarbageTruckDTO {
    private Long id;
    private String licencePlateNo;
    private Integer mileage;
    private Integer maxVolume;
    private TruckStatus truckStatus;
}
