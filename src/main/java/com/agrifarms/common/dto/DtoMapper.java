package com.agrifarms.common.dto;

import com.agrifarms.common.entity.Booking;
import com.agrifarms.common.entity.Equipment;
import com.agrifarms.common.entity.ServiceOffering;
import com.agrifarms.common.entity.TransportVehicle;
import com.agrifarms.common.entity.User;
import com.agrifarms.common.entity.WorkerGroup;
import com.agrifarms.common.entity.WorkerGroupRole;
import com.agrifarms.common.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoMapper {

    @Autowired
    private UserService userService;

    // User
    public UserDTO toUserDTO(User entity) {
        if (entity == null) {
            return null;
        }
        return new UserDTO(
                entity.getUserId(),
                entity.getPhoneNumber(),
                entity.getEmail(),
                entity.getFullName(),
                entity.getRole(),
                entity.getDistrict(),
                entity.getVillage(),
                entity.getHouseNo(),
                entity.getStreet(),
                entity.getState(),
                entity.getCountry(),
                entity.getPincode(),
                entity.getProfileImageUrl(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getFcmToken(),
                entity.getStatus(),
                entity.isNotificationOrderUpdates(),
                entity.isNotificationBookingUpdates(),
                entity.isNotificationPaymentUpdates(),
                entity.isNotificationCommunityActivity(),
                entity.isNotificationPromotionalOffers()
        );
    }

    public User toUserEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        User entity = new User();
        entity.setUserId(dto.getUserId());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        entity.setFullName(dto.getFullName());
        entity.setRole(dto.getRole());
        entity.setDistrict(dto.getDistrict());
        entity.setVillage(dto.getVillage());
        entity.setHouseNo(dto.getHouseNo());
        entity.setStreet(dto.getStreet());
        entity.setState(dto.getState());
        entity.setCountry(dto.getCountry());
        entity.setPincode(dto.getPincode());
        entity.setProfileImageUrl(dto.getProfileImageUrl());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setFcmToken(dto.getFcmToken());
        entity.setPassword(dto.getPassword());
        entity.setNotificationOrderUpdates(dto.isNotificationOrderUpdates());
        entity.setNotificationBookingUpdates(dto.isNotificationBookingUpdates());
        entity.setNotificationPaymentUpdates(dto.isNotificationPaymentUpdates());
        entity.setNotificationCommunityActivity(dto.isNotificationCommunityActivity());
        entity.setNotificationPromotionalOffers(dto.isNotificationPromotionalOffers());
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        return entity;
    }

    private String getOwnerNameSafely(String ownerId) {
        return userService.getOwnerNameWithCache(ownerId);
    }

    // Equipment
    public EquipmentDTO toEquipmentDTO(Equipment entity) {
        if (entity == null) {
            return null;
        }

        String ownerName = getOwnerNameSafely(entity.getOwnerId());
        String ownerProfileImageUrl = userService.getOwnerProfileImageWithCache(entity.getOwnerId());

        EquipmentDTO dto = new EquipmentDTO(
                entity.getEquipmentId(),
                entity.getOwnerId(),
                ownerName,
                entity.getCategory(),
                entity.getBrandModel(),
                entity.getConditionStatus(),
                entity.getPricePerHour(),
                entity.getOperatorAvailable(),
                entity.getOperatorPrice(),
                entity.getLocation(),
                entity.getHouseNo(),
                entity.getStreet(),
                entity.getVillage(),
                entity.getDistrict(),
                entity.getState(),
                entity.getCountry(),
                entity.getPincode(),
                entity.getIsAvailable(),
                entity.getRating(),
                entity.getApprovalStatus(),
                entity.getImageUrl(),
                entity.getLatitude(),
                entity.getLongitude(),
                ownerProfileImageUrl
        );
        dto.setOwnerBusinessName(entity.getOwnerBusinessName());
        dto.setBrand(entity.getBrand());
        dto.setModel(entity.getModel());
        dto.setDescription(entity.getDescription());
        dto.setVehicleNumber(entity.getVehicleNumber());
        return dto;
    }

    public Equipment toEquipmentEntity(EquipmentDTO dto) {
        if (dto == null) {
            return null;
        }
        Equipment entity = new Equipment();
        entity.setEquipmentId(dto.getEquipmentId());
        entity.setOwnerId(dto.getOwnerId());
        entity.setCategory(dto.getCategory());
        entity.setConditionStatus(dto.getConditionStatus());
        entity.setPricePerHour(dto.getPricePerHour());
        entity.setOperatorAvailable(dto.getOperatorAvailable());
        entity.setOperatorPrice(dto.getOperatorPrice());
        entity.setLocation(dto.getLocation());
        entity.setIsAvailable(dto.getIsAvailable());
        entity.setRating(dto.getRating());
        entity.setApprovalStatus(dto.getApprovalStatus());
        entity.setImageUrl(dto.getImageUrl());
        entity.setHouseNo(dto.getHouseNo());
        entity.setStreet(dto.getStreet());
        entity.setVillage(dto.getVillage());
        entity.setDistrict(dto.getDistrict());
        entity.setState(dto.getState());
        entity.setCountry(dto.getCountry());
        entity.setPincode(dto.getPincode());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setOwnerBusinessName(dto.getOwnerBusinessName());
        entity.setBrand(dto.getBrand());
        entity.setModel(dto.getModel());
        entity.setDescription(dto.getDescription());
        entity.setVehicleNumber(dto.getVehicleNumber());
        if (dto.getBrand() != null && dto.getModel() != null) {
            entity.setBrandModel(dto.getBrand() + " " + dto.getModel());
        } else if (dto.getBrandModel() != null) {
            entity.setBrandModel(dto.getBrandModel());
        }
        if (entity.getBrandModel() != null) {
            entity.setName(entity.getBrandModel());
        } else {
            entity.setName(entity.getCategory() != null ? entity.getCategory() : "Equipment");
        }
        return entity;
    }

    // TransportVehicle
    public TransportVehicleDTO toTransportVehicleDTO(TransportVehicle entity) {
        if (entity == null) {
            return null;
        }

        String ownerName = getOwnerNameSafely(entity.getOwnerId());
        String ownerProfileImageUrl = userService.getOwnerProfileImageWithCache(entity.getOwnerId());

        TransportVehicleDTO dto = new TransportVehicleDTO(
                entity.getVehicleId(),
                entity.getOwnerId(),
                ownerName,
                entity.getVehicleType(),
                entity.getVehicleNumber(),
                entity.getLoadCapacity(),
                entity.getPricePerKmOrTrip(),
                entity.getDriverIncluded(),
                entity.getServiceArea(),
                entity.getLocation(),
                entity.getHouseNo(),
                entity.getStreet(),
                entity.getVillage(),
                entity.getDistrict(),
                entity.getState(),
                entity.getCountry(),
                entity.getPincode(),
                entity.getIsAvailable(),
                entity.getRating(),
                entity.getApprovalStatus(),
                entity.getImageUrl(),
                entity.getLatitude(),
                entity.getLongitude(),
                ownerProfileImageUrl,
                entity.getOperatorPrice()
        );
        dto.setOwnerBusinessName(entity.getOwnerBusinessName());
        dto.setBrand(entity.getBrand());
        dto.setModel(entity.getModel());
        dto.setYearOfManufacture(entity.getYearOfManufacture());
        dto.setPricePerKm(entity.getPricePerKm());
        dto.setPricePerHour(entity.getPricePerHour());
        dto.setVehicleCondition(entity.getVehicleCondition());
        return dto;
    }

    public TransportVehicle toTransportVehicleEntity(TransportVehicleDTO dto) {
        if (dto == null) {
            return null;
        }
        TransportVehicle entity = new TransportVehicle();
        entity.setVehicleId(dto.getVehicleId());
        entity.setOwnerId(dto.getOwnerId());
        entity.setVehicleType(dto.getVehicleType());
        entity.setVehicleNumber(dto.getVehicleNumber());
        entity.setLoadCapacity(dto.getLoadCapacity());
        entity.setPricePerKmOrTrip(dto.getPricePerKmOrTrip());
        entity.setDriverIncluded(dto.getDriverIncluded());
        entity.setServiceArea(dto.getServiceArea());
        entity.setLocation(dto.getLocation());
        entity.setIsAvailable(dto.getIsAvailable());
        entity.setRating(dto.getRating());
        entity.setApprovalStatus(dto.getApprovalStatus());
        entity.setImageUrl(dto.getImageUrl());
        entity.setHouseNo(dto.getHouseNo());
        entity.setStreet(dto.getStreet());
        entity.setVillage(dto.getVillage());
        entity.setDistrict(dto.getDistrict());
        entity.setState(dto.getState());
        entity.setCountry(dto.getCountry());
        entity.setPincode(dto.getPincode());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setOperatorPrice(dto.getOperatorPrice());
        entity.setOwnerBusinessName(dto.getOwnerBusinessName());
        entity.setBrand(dto.getBrand());
        entity.setModel(dto.getModel());
        entity.setYearOfManufacture(dto.getYearOfManufacture());
        entity.setPricePerKm(dto.getPricePerKm());
        entity.setPricePerHour(dto.getPricePerHour());
        entity.setVehicleCondition(dto.getVehicleCondition());
        return entity;
    }

    // ServiceOffering
    public ServiceOfferingDTO toServiceOfferingDTO(ServiceOffering entity) {
        if (entity == null) {
            return null;
        }

        String ownerName = getOwnerNameSafely(entity.getOwnerId());
        String ownerProfileImageUrl = userService.getOwnerProfileImageWithCache(entity.getOwnerId());

        ServiceOfferingDTO dto = new ServiceOfferingDTO(
                entity.getServiceId(),
                entity.getOwnerId(),
                ownerName,
                entity.getServiceType(),
                entity.getBusinessName(),
                entity.getDescription(),
                entity.getEquipmentUsed(),
                entity.getPriceRate(),
                entity.getOperatorIncluded(),
                entity.getLocation(),
                entity.getHouseNo(),
                entity.getStreet(),
                entity.getVillage(),
                entity.getDistrict(),
                entity.getState(),
                entity.getCountry(),
                entity.getPincode(),
                entity.getIsAvailable(),
                entity.getRating(),
                entity.getApprovalStatus(),
                entity.getImageUrl(),
                entity.getLatitude(),
                entity.getLongitude(),
                ownerProfileImageUrl
        );
        dto.setOperatorPrice(entity.getOperatorPrice());
        return dto;
    }

    public ServiceOffering toServiceOfferingEntity(ServiceOfferingDTO dto) {
        if (dto == null) {
            return null;
        }
        ServiceOffering entity = new ServiceOffering();
        entity.setServiceId(dto.getServiceId());
        entity.setOwnerId(dto.getOwnerId());
        entity.setServiceType(dto.getServiceType());
        entity.setServiceName(dto.getServiceType() != null ? dto.getServiceType() : (dto.getBusinessName() != null ? dto.getBusinessName() : "Service"));
        entity.setBusinessName(dto.getBusinessName());
        entity.setDescription(dto.getDescription());
        entity.setEquipmentUsed(dto.getEquipmentUsed());
        entity.setPriceRate(dto.getPriceRate());
        entity.setOperatorIncluded(dto.getOperatorIncluded());
        entity.setOperatorPrice(dto.getOperatorPrice());
        entity.setLocation(dto.getLocation());
        entity.setIsAvailable(dto.getIsAvailable());
        entity.setRating(dto.getRating());
        entity.setApprovalStatus(dto.getApprovalStatus());
        entity.setImageUrl(dto.getImageUrl());
        entity.setHouseNo(dto.getHouseNo());
        entity.setStreet(dto.getStreet());
        entity.setVillage(dto.getVillage());
        entity.setDistrict(dto.getDistrict());
        entity.setState(dto.getState());
        entity.setCountry(dto.getCountry());
        entity.setPincode(dto.getPincode());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        return entity;
    }

    // Booking
    public BookingDTO toBookingDTO(Booking entity) {
        if (entity == null) {
            return null;
        }
        BookingDTO dto = new BookingDTO(
                entity.getBookingId(),
                entity.getFarmerId(),
                entity.getProviderId(),
                entity.getAssetId(),
                entity.getAssetType(),
                entity.getBookingDate(),
                entity.getScheduledStartTime(),
                entity.getScheduledEndTime(),
                entity.getStatus(),
                entity.getTotalAmount(),
                entity.getLocationLat(),
                entity.getLocationLng(),
                entity.getAddressText(),
                entity.getNotes(),
                false);
        dto.setCancelledBy(entity.getCancelledBy());
        dto.setCancellationReason(entity.getCancellationReason());
        return dto;
    }

    public Booking toBookingEntity(BookingDTO dto) {
        if (dto == null) {
            return null;
        }
        Booking entity = new Booking();
        entity.setBookingId(dto.getBookingId());
        entity.setFarmerId(dto.getFarmerId());
        entity.setProviderId(dto.getProviderId());
        entity.setAssetId(dto.getAssetId());
        entity.setAssetType(dto.getAssetType());
        entity.setBookingDate(dto.getBookingDate());
        entity.setScheduledStartTime(dto.getScheduledStartTime());
        entity.setScheduledEndTime(dto.getScheduledEndTime());
        entity.setStatus(dto.getStatus());
        entity.setTotalAmount(dto.getTotalAmount());
        entity.setLocationLat(dto.getLocationLat());
        entity.setLocationLng(dto.getLocationLng());
        entity.setAddressText(dto.getAddressText());
        entity.setNotes(dto.getNotes());
        entity.setCancelledBy(dto.getCancelledBy());
        entity.setCancellationReason(dto.getCancellationReason());
        return entity;
    }

    // WorkerGroup
    public WorkerGroupDTO toWorkerGroupDTO(WorkerGroup entity) {
        if (entity == null) {
            return null;
        }
        String ownerProfileImageUrl = userService.getOwnerProfileImageWithCache(entity.getOwnerId());
        WorkerGroupDTO dto = new WorkerGroupDTO(
                entity.getGroupId(),
                entity.getOwnerId(),
                getOwnerNameSafely(entity.getOwnerId()),
                entity.getGroupName(),
                entity.getMaleCount(),
                entity.getFemaleCount(),
                entity.getPricePerMale(),
                entity.getPricePerFemale(),
                entity.getPricePerMaleHourly(),
                entity.getPricePerFemaleHourly(),
                entity.getSkills(),
                entity.getLocation(),
                entity.getHouseNo(),
                entity.getStreet(),
                entity.getVillage(),
                entity.getDistrict(),
                entity.getState(),
                entity.getCountry(),
                entity.getPincode(),
                entity.getServiceRangeKm(),
                entity.getIsAvailable(),
                entity.getRating(),
                entity.getApprovalStatus(),
                entity.getImageUrl(),
                ownerProfileImageUrl,
                null,
                entity.getLatitude(),
                entity.getLongitude()
        );
        if (entity.getRoles() != null) {
            dto.setRoles(entity.getRoles().stream()
                    .map(this::toWorkerGroupRoleDTO)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public WorkerGroup toWorkerGroupEntity(WorkerGroupDTO dto) {
        if (dto == null) {
            return null;
        }
        WorkerGroup entity = new WorkerGroup();
        entity.setGroupId(dto.getGroupId());
        entity.setOwnerId(dto.getOwnerId());
        entity.setGroupName(dto.getGroupName());
        entity.setMaleCount(dto.getMaleCount());
        entity.setFemaleCount(dto.getFemaleCount());
        entity.setPricePerMale(dto.getPricePerMale());
        entity.setPricePerFemale(dto.getPricePerFemale());
        entity.setPricePerMaleHourly(dto.getPricePerMaleHourly());
        entity.setPricePerFemaleHourly(dto.getPricePerFemaleHourly());
        entity.setSkills(dto.getSkills());
        entity.setLocation(dto.getLocation());
        entity.setServiceRangeKm(dto.getServiceRangeKm());
        entity.setIsAvailable(dto.getIsAvailable());
        entity.setRating(dto.getRating());
        entity.setApprovalStatus(dto.getApprovalStatus());
        entity.setImageUrl(dto.getImageUrl());
        entity.setHouseNo(dto.getHouseNo());
        entity.setStreet(dto.getStreet());
        entity.setVillage(dto.getVillage());
        entity.setDistrict(dto.getDistrict());
        entity.setState(dto.getState());
        entity.setCountry(dto.getCountry());
        entity.setPincode(dto.getPincode());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());

        if (dto.getRoles() != null) {
            List<WorkerGroupRole> roles = dto.getRoles().stream()
                    .map(this::toWorkerGroupRoleEntity)
                    .collect(Collectors.toList());
            roles.forEach(role -> role.setWorkerGroup(entity));
            entity.setRoles(roles);
        }
        return entity;
    }

    public WorkerGroupRoleDTO toWorkerGroupRoleDTO(WorkerGroupRole entity) {
        if (entity == null) {
            return null;
        }
        return new WorkerGroupRoleDTO(
                entity.getRoleId(),
                entity.getGender(),
                entity.getCount(),
                entity.getTaskName());
    }

    public WorkerGroupRole toWorkerGroupRoleEntity(WorkerGroupRoleDTO dto) {
        if (dto == null) {
            return null;
        }
        WorkerGroupRole entity = new WorkerGroupRole();
        entity.setRoleId(dto.getRoleId());
        entity.setGender(dto.getGender());
        entity.setCount(dto.getCount());
        entity.setTaskName(dto.getTaskName());
        return entity;
    }
    public com.agrifarms.common.dto.ReviewDTO toReviewDTO(com.agrifarms.common.entity.Review entity) {
        if (entity == null) {
            return null;
        }
        return new com.agrifarms.common.dto.ReviewDTO(
                entity.getId(),
                entity.getBookingId(),
                entity.getAssetId(),
                entity.getReviewerId(),
                entity.getRating(),
                entity.getComment(),
                entity.getCreatedAt()
        );
    }

    public com.agrifarms.common.entity.Review toReviewEntity(com.agrifarms.common.dto.ReviewDTO dto) {
        if (dto == null) {
            return null;
        }
        com.agrifarms.common.entity.Review entity = new com.agrifarms.common.entity.Review();
        entity.setId(dto.getId());
        entity.setBookingId(dto.getBookingId());
        entity.setAssetId(dto.getAssetId());
        entity.setReviewerId(dto.getReviewerId());
        entity.setRating(dto.getRating());
        entity.setComment(dto.getComment());
        entity.setCreatedAt(dto.getCreatedAt());
        return entity;
    }
}
