package com.agrifarms.common.dto;

import lombok.AllArgsConstructor;
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
    private String profileImageUrl;
}
