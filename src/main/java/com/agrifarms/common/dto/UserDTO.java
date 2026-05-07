package com.agrifarms.common.dto;

import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserDTO {

    private String userId;
    private String phoneNumber;
    private String email;
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

    private String fcmToken;

    public UserDTO() {
    }

    public UserDTO(String userId, String phoneNumber, String email, String fullName, String role, String district, String village, String houseNo, String street, String state, String country, String pincode, String profileImageUrl, BigDecimal latitude, BigDecimal longitude, String fcmToken) {
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.district = district;
        this.village = village;
        this.houseNo = houseNo;
        this.street = street;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
        this.profileImageUrl = profileImageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.fcmToken = fcmToken;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getVillage() { return village; }
    public void setVillage(String village) { this.village = village; }

    public String getHouseNo() { return houseNo; }
    public void setHouseNo(String houseNo) { this.houseNo = houseNo; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public String getProfileImageUrl() { return profileImageUrl; }
    public void setProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl; }

    public BigDecimal getLatitude() { return latitude; }
    public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }

    public BigDecimal getLongitude() { return longitude; }
    public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }

    public String getFcmToken() { return fcmToken; }
    public void setFcmToken(String fcmToken) { this.fcmToken = fcmToken; }
}
