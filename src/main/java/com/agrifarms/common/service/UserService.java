package com.agrifarms.common.service;

import com.agrifarms.common.dto.UserStatsDTO;
import com.agrifarms.common.entity.User;
import com.agrifarms.common.repository.*;
import lombok.RequiredArgsConstructor;
import com.agrifarms.common.repository.UserRepository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final EquipmentRepository equipmentRepository;
    private final TransportVehicleRepository transportVehicleRepository;
    private final ServiceOfferingRepository serviceOfferingRepository;
    private final WorkerGroupRepository workerGroupRepository;
    private final NotificationService notificationService;

    public UserStatsDTO getUserStats(String userId) {
        // Orders: Only PENDING or CONFIRMED bookings for this provider
        java.util.List<String> activeStatuses = java.util.Arrays.asList("PENDING", "CONFIRMED");
        long orders = bookingRepository.countByProviderIdAndStatusIn(userId, activeStatuses);

        // Rentals: Count of Equipment + Transport Vehicles owned by user
        long rentals = equipmentRepository.countByOwnerId(userId)
                + transportVehicleRepository.countByOwnerId(userId);

        // Services: Count of Services + Worker Groups owned by user
        long services = serviceOfferingRepository.countByOwnerId(userId)
                + workerGroupRepository.countByOwnerId(userId);

        return new UserStatsDTO(orders, rentals, services);
    }

    @Cacheable(value = "users", key = "#userId")
    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> getUserByKeycloakId(String keycloakId) {
        return userRepository.findByKeycloakId(keycloakId);
    }

    @Cacheable(value = "ownerNames", key = "#ownerId")
    public String getOwnerNameWithCache(String ownerId) {
        if (ownerId == null || ownerId.trim().isEmpty()) {
            return "Unknown Owner";
        }
        return userRepository.findById(ownerId)
                .map(user -> user.getFullName() != null && !user.getFullName().trim().isEmpty() ? user.getFullName()
                        : "Unknown Owner")
                .orElse("Unknown Owner");
    }

    @Cacheable(value = "profileImages", key = "#ownerId")
    public String getOwnerProfileImageWithCache(String ownerId) {
        if (ownerId == null || ownerId.trim().isEmpty()) {
            return null;
        }
        return userRepository.findById(ownerId)
                .map(User::getProfileImageUrl)
                .orElse(null);
    }

    private static final java.util.Set<String> ALLOWED_ROLES = java.util.Set.of("ADMIN", "FARMER", "OWNER");

    public User createUser(User user) {
        if (user.getPhoneNumber() != null) {
            String cleanedPhone = user.getPhoneNumber().replaceAll("\\D", "");
            if (cleanedPhone.length() != 10) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number must be exactly 10 digits");
            }
            user.setPhoneNumber(cleanedPhone);
            if (userRepository.existsByPhoneNumber(cleanedPhone)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already registered");
            }
        }
        if (user.getEmail() != null && userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        // Enforce role whitelist — only ADMIN, FARMER, OWNER allowed
        if (user.getRole() != null) {
            String upper = user.getRole().trim().toUpperCase();
            if (!ALLOWED_ROLES.contains(upper)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Invalid role '" + user.getRole() + "'. Allowed roles: ADMIN, FARMER, OWNER");
            }
            user.setRole(upper); // normalise to uppercase
        }

        User savedUser = userRepository.save(user);
        notificationService.notifyAdmin("New user registered",
                savedUser.getFullName() + " joined as a " + savedUser.getRole(), "success", savedUser.getUserId());
        return savedUser;
    }

    public java.util.List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Caching(evict = {
            @CacheEvict(value = "users", key = "#userId"),
            @CacheEvict(value = "ownerNames", key = "#userId"),
            @CacheEvict(value = "profileImages", key = "#userId")
    })
    public User updateUser(String userId, User updatedData) {
        return userRepository.findById(userId).map(existingUser -> {
            if (updatedData.getFullName() != null) {
                existingUser.setFullName(updatedData.getFullName());
            }
            if (updatedData.getPhoneNumber() != null) {
                String cleanedPhone = updatedData.getPhoneNumber().replaceAll("\\D", "");
                if (cleanedPhone.trim().isEmpty()) {
                    existingUser.setPhoneNumber(null);
                } else {
                    if (cleanedPhone.length() != 10) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "Phone number must be exactly 10 digits");
                    }
                    Optional<User> userWithPhone = userRepository.findByPhoneNumber(cleanedPhone);
                    if (userWithPhone.isPresent() && !userWithPhone.get().getUserId().equals(userId)) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already registered");
                    }
                    existingUser.setPhoneNumber(cleanedPhone);
                }
            }
            if (updatedData.getEmail() != null) {
                existingUser.setEmail(updatedData.getEmail());
            }
            if (updatedData.getVillage() != null) {
                existingUser.setVillage(updatedData.getVillage());
            }
            if (updatedData.getMandal() != null) {
                existingUser.setMandal(updatedData.getMandal());
            }
            if (updatedData.getDistrict() != null) {
                existingUser.setDistrict(updatedData.getDistrict());
            }
            if (updatedData.getHouseNo() != null) {
                existingUser.setHouseNo(updatedData.getHouseNo());
            }
            if (updatedData.getStreet() != null) {
                existingUser.setStreet(updatedData.getStreet());
            }
            if (updatedData.getState() != null) {
                existingUser.setState(updatedData.getState());
            }
            if (updatedData.getCountry() != null) {
                existingUser.setCountry(updatedData.getCountry());
            }
            if (updatedData.getPincode() != null) {
                existingUser.setPincode(updatedData.getPincode());
            }
            if (updatedData.getLatitude() != null) {
                existingUser.setLatitude(updatedData.getLatitude());
            }
            if (updatedData.getLongitude() != null) {
                existingUser.setLongitude(updatedData.getLongitude());
            }
            if (updatedData.getProfileImageUrl() != null) {
                existingUser.setProfileImageUrl(updatedData.getProfileImageUrl());
            }
            if (updatedData.getFcmToken() != null) {
                existingUser.setFcmToken(updatedData.getFcmToken());
            }
            existingUser.setNotificationOrderUpdates(updatedData.isNotificationOrderUpdates());
            existingUser.setNotificationBookingUpdates(updatedData.isNotificationBookingUpdates());
            existingUser.setNotificationPaymentUpdates(updatedData.isNotificationPaymentUpdates());
            existingUser.setNotificationCommunityActivity(updatedData.isNotificationCommunityActivity());
            existingUser.setNotificationPromotionalOffers(updatedData.isNotificationPromotionalOffers());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId));
    }

    public void updateFcmToken(String userId, String fcmToken) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setFcmToken(fcmToken);
            userRepository.save(user);
        });
    }

    @Caching(evict = {
            @CacheEvict(value = "users", key = "#userId")
    })
    public User updateUserStatus(String userId, String status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if ("Banned".equalsIgnoreCase(user.getStatus())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot modify a banned user");
        }

        user.setStatus(status);
        user = userRepository.save(user);

        if ("Suspended".equalsIgnoreCase(status) || "Banned".equalsIgnoreCase(status)) {
            // Deactivate all services
            equipmentRepository.findByOwnerId(userId).forEach(e -> {
                e.setIsAvailable(false);
                equipmentRepository.save(e);
            });
            serviceOfferingRepository.findByOwnerId(userId).forEach(s -> {
                s.setIsAvailable(false);
                serviceOfferingRepository.save(s);
            });
            transportVehicleRepository.findByOwnerId(userId).forEach(v -> {
                v.setIsAvailable(false);
                transportVehicleRepository.save(v);
            });
            workerGroupRepository.findByOwnerId(userId).forEach(w -> {
                w.setIsAvailable(false);
                workerGroupRepository.save(w);
            });
        }
        return user;
    }
}
