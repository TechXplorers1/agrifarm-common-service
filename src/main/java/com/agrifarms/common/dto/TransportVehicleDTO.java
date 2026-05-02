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
    private String ownerName;
    private String vehicleType;
    private String vehicleNumber;
    private String loadCapacity;
    private BigDecimal pricePerKmOrTrip;
    private Boolean driverIncluded;
    private String serviceArea;
    private String location;
    private String houseNo;
    private String street;
    private String village;
    private String district;
    private String state;
    private String country;
    private String pincode;
    private Boolean isAvailable;
    private BigDecimal rating;
    private String approvalStatus;
    private String imageUrl;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
