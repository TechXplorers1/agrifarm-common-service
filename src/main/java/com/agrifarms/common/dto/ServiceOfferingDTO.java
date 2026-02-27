package com.agrifarms.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceOfferingDTO {
    private String serviceId;
    private String ownerId;
    private String ownerName;
    private String serviceType;
    private String businessName;
    private String description;
    private String equipmentUsed;
    private BigDecimal priceRate;
    private Boolean operatorIncluded;
    private String location;
    private Boolean isAvailable;
    private BigDecimal rating;
    private String approvalStatus;
    private String imageUrl;
}
