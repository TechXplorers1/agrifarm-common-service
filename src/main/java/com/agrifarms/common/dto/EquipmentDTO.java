package com.agrifarms.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDTO {
    private String equipmentId;
    private String ownerId;
    private String ownerName;
    private String category;
    private String brandModel;
    private String conditionStatus;
    private BigDecimal pricePerHour;
    private Boolean operatorAvailable;
    private String location;
    private Boolean isAvailable;
    private BigDecimal rating;
    private String approvalStatus;
    private String imageUrl;
}
