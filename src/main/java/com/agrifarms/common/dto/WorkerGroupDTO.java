package com.agrifarms.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerGroupDTO {
    private String groupId;
    private String ownerId;
    private String groupName;
    private Integer maleCount;
    private Integer femaleCount;
    private BigDecimal pricePerMale;
    private BigDecimal pricePerFemale;
    private String skills;
    private String location;
    private String houseNo;
    private String street;
    private String village;
    private String district;
    private String state;
    private String country;
    private String pincode;
    private Integer serviceRangeKm;
    private Boolean isAvailable;
    private BigDecimal rating;
    private String approvalStatus;
    private String imageUrl;
    private List<WorkerGroupRoleDTO> roles;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
