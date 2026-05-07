package com.agrifarms.common.dto;

import java.math.BigDecimal;
import java.util.List;

public class WorkerGroupDTO {

    private String groupId;
    private String ownerId;
    private String ownerName;
    private String groupName;
    private Integer maleCount;
    private Integer femaleCount;
    private BigDecimal pricePerMale;
    private BigDecimal pricePerFemale;
    private BigDecimal pricePerMaleHourly;
    private BigDecimal pricePerFemaleHourly;
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
    private String ownerProfileImageUrl;
    private List<WorkerGroupRoleDTO> roles;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public WorkerGroupDTO() {
    }

    public WorkerGroupDTO(String groupId, String ownerId, String ownerName, String groupName, Integer maleCount,
            Integer femaleCount, BigDecimal pricePerMale, BigDecimal pricePerFemale, BigDecimal pricePerMaleHourly, BigDecimal pricePerFemaleHourly, String skills,
            String location, String houseNo, String street, String village, String district, String state, String country, String pincode, Integer serviceRangeKm, Boolean isAvailable, BigDecimal rating,
            String approvalStatus, String imageUrl, String ownerProfileImageUrl, List<WorkerGroupRoleDTO> roles, BigDecimal latitude, BigDecimal longitude) {
        this.groupId = groupId;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.groupName = groupName;
        this.maleCount = maleCount;
        this.femaleCount = femaleCount;
        this.pricePerMale = pricePerMale;
        this.pricePerFemale = pricePerFemale;
        this.pricePerMaleHourly = pricePerMaleHourly;
        this.pricePerFemaleHourly = pricePerFemaleHourly;
        this.skills = skills;
        this.location = location;
        this.houseNo = houseNo;
        this.street = street;
        this.village = village;
        this.district = district;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
        this.serviceRangeKm = serviceRangeKm;
        this.isAvailable = isAvailable;
        this.rating = rating;
        this.approvalStatus = approvalStatus;
        this.imageUrl = imageUrl;
        this.ownerProfileImageUrl = ownerProfileImageUrl;
        this.roles = roles;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getGroupId() { return groupId; }
    public void setGroupId(String groupId) { this.groupId = groupId; }

    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }

    public Integer getMaleCount() { return maleCount; }
    public void setMaleCount(Integer maleCount) { this.maleCount = maleCount; }

    public Integer getFemaleCount() { return femaleCount; }
    public void setFemaleCount(Integer femaleCount) { this.femaleCount = femaleCount; }

    public BigDecimal getPricePerMale() { return pricePerMale; }
    public void setPricePerMale(BigDecimal pricePerMale) { this.pricePerMale = pricePerMale; }

    public BigDecimal getPricePerFemale() { return pricePerFemale; }
    public void setPricePerFemale(BigDecimal pricePerFemale) { this.pricePerFemale = pricePerFemale; }

    public BigDecimal getPricePerMaleHourly() { return pricePerMaleHourly; }
    public void setPricePerMaleHourly(BigDecimal pricePerMaleHourly) { this.pricePerMaleHourly = pricePerMaleHourly; }

    public BigDecimal getPricePerFemaleHourly() { return pricePerFemaleHourly; }
    public void setPricePerFemaleHourly(BigDecimal pricePerFemaleHourly) { this.pricePerFemaleHourly = pricePerFemaleHourly; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getHouseNo() { return houseNo; }
    public void setHouseNo(String houseNo) { this.houseNo = houseNo; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getVillage() { return village; }
    public void setVillage(String village) { this.village = village; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public Integer getServiceRangeKm() { return serviceRangeKm; }
    public void setServiceRangeKm(Integer serviceRangeKm) { this.serviceRangeKm = serviceRangeKm; }

    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }

    public BigDecimal getRating() { return rating; }
    public void setRating(BigDecimal rating) { this.rating = rating; }

    public String getApprovalStatus() { return approvalStatus; }
    public void setApprovalStatus(String approvalStatus) { this.approvalStatus = approvalStatus; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getOwnerProfileImageUrl() { return ownerProfileImageUrl; }
    public void setOwnerProfileImageUrl(String ownerProfileImageUrl) { this.ownerProfileImageUrl = ownerProfileImageUrl; }

    public List<WorkerGroupRoleDTO> getRoles() { return roles; }
    public void setRoles(List<WorkerGroupRoleDTO> roles) { this.roles = roles; }

    public BigDecimal getLatitude() { return latitude; }
    public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }

    public BigDecimal getLongitude() { return longitude; }
    public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }
}
