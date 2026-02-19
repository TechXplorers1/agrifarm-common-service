package com.agrifarms.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportVehicleDTO {
    private String vehicleId;
    private String ownerId;
    private String vehicleType;
    private String vehicleNumber;
    private String loadCapacity;
    private BigDecimal pricePerKmOrTrip;
    private Boolean driverIncluded;
    private String serviceArea;
    private String location;
    private Boolean isAvailable;
    private BigDecimal rating;
    private String approvalStatus;
    private String imageUrl;
}
