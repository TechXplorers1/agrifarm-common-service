package com.agrifarms.common.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @UuidGenerator
    private String userId;

    @Column(name = "phone_number", unique = true, nullable = true)
    private String phoneNumber;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "password")
    private String password;

    private String role; // Farmer, Provider, etc.
    private String district;
    private String mandal;
    private String village;

    @Column(name = "house_no")
    private String houseNo;

    @Column(name = "street")
    private String street;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "pincode")
    private String pincode;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "fcm_token")
    private String fcmToken;

    @Column(name = "status")
    private String status = "Active";

    @Column(name = "keycloak_id", unique = true)
    private String keycloakId;

    public User() {
    }

    public User(String userId, String phoneNumber, String email, String fullName, String role, String district, String mandal, String village, String profileImageUrl, String fcmToken) {
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.district = district;
        this.mandal = mandal;
        this.village = village;
        this.profileImageUrl = profileImageUrl;
        this.fcmToken = fcmToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getMandal() {
        return mandal;
    }

    public void setMandal(String mandal) {
        this.mandal = mandal;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeycloakId() {
        return keycloakId;
    }

    public void setKeycloakId(String keycloakId) {
        this.keycloakId = keycloakId;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    private BigDecimal latitude;
    private BigDecimal longitude;

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Column(name = "notification_order_updates")
    private Boolean notificationOrderUpdates = true;

    @Column(name = "notification_booking_updates")
    private Boolean notificationBookingUpdates = true;

    @Column(name = "notification_payment_updates")
    private Boolean notificationPaymentUpdates = true;

    @Column(name = "notification_community_activity")
    private Boolean notificationCommunityActivity = false;

    @Column(name = "notification_promotional_offers")
    private Boolean notificationPromotionalOffers = false;

    public boolean isNotificationOrderUpdates() {
        return notificationOrderUpdates != null ? notificationOrderUpdates : true;
    }

    public void setNotificationOrderUpdates(Boolean notificationOrderUpdates) {
        this.notificationOrderUpdates = notificationOrderUpdates;
    }

    public boolean isNotificationBookingUpdates() {
        return notificationBookingUpdates != null ? notificationBookingUpdates : true;
    }

    public void setNotificationBookingUpdates(Boolean notificationBookingUpdates) {
        this.notificationBookingUpdates = notificationBookingUpdates;
    }

    public boolean isNotificationPaymentUpdates() {
        return notificationPaymentUpdates != null ? notificationPaymentUpdates : true;
    }

    public void setNotificationPaymentUpdates(Boolean notificationPaymentUpdates) {
        this.notificationPaymentUpdates = notificationPaymentUpdates;
    }

    public boolean isNotificationCommunityActivity() {
        return notificationCommunityActivity != null ? notificationCommunityActivity : false;
    }

    public void setNotificationCommunityActivity(Boolean notificationCommunityActivity) {
        this.notificationCommunityActivity = notificationCommunityActivity;
    }

    public boolean isNotificationPromotionalOffers() {
        return notificationPromotionalOffers != null ? notificationPromotionalOffers : false;
    }

    public void setNotificationPromotionalOffers(Boolean notificationPromotionalOffers) {
        this.notificationPromotionalOffers = notificationPromotionalOffers;
    }
}
