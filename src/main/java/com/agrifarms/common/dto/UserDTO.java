package com.agrifarms.common.dto;

import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String userId;
    private String phoneNumber;
    private String fullName;
    private String role;
    private String district;
    private String village;
    private String houseNo;
    private String street;
    private String state;
    private String country;
    private String pincode;
    private String profileImageUrl;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
