package com.agrifarms.common.dto;

<<<<<<< HEAD
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
=======
>>>>>>> 6a3fc0c1aeaf20611009613722e2f788ea1da2fb


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
<<<<<<< HEAD
    private BigDecimal latitude;
    private BigDecimal longitude;
=======
    private String fcmToken;

    public UserDTO() {}

    public UserDTO(String userId, String phoneNumber, String fullName, String role, String district, String village, String profileImageUrl, String fcmToken) {
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.role = role;
        this.district = district;
        this.village = village;
        this.profileImageUrl = profileImageUrl;
        this.fcmToken = fcmToken;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getVillage() { return village; }
    public void setVillage(String village) { this.village = village; }

    public String getProfileImageUrl() { return profileImageUrl; }
    public void setProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl; }

    public String getFcmToken() { return fcmToken; }
    public void setFcmToken(String fcmToken) { this.fcmToken = fcmToken; }
>>>>>>> 6a3fc0c1aeaf20611009613722e2f788ea1da2fb
}
