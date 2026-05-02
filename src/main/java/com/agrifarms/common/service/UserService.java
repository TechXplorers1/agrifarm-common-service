package com.agrifarms.common.service;

import com.agrifarms.common.dto.UserStatsDTO;
import com.agrifarms.common.entity.User;
import com.agrifarms.common.repository.*;
import lombok.RequiredArgsConstructor;

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

    public UserStatsDTO getUserStats(String userId) {
        // Orders: Only PENDING or CONFIRMED bookings for this provider
        java.util.List<String> activeStatuses = java.util.Arrays.asList("PENDING", "CONFIRMED");
        long orders = bookingRepository.countByProviderIdAndStatusIn(userId, activeStatuses);
        
        // Rentals: Count of Equipment + Transport Vehicles owned by user
        long rentals = equipmentRepository.countByOwnerId(userId) +
                      transportVehicleRepository.countByOwnerId(userId);

        // Services: Count of Services + Worker Groups owned by user
        long services = serviceOfferingRepository.countByOwnerId(userId) +
                       workerGroupRepository.countByOwnerId(userId);

        return new UserStatsDTO(orders, rentals, services);
    }

    public User createUser(User user) {
        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            // You can create a custom PhoneAlreadyExistsException for better API responses
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already registered");
        }
        return userRepository.save(user);
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public User updateUser(String userId, User updatedData) {
        return userRepository.findById(userId).map(existingUser -> {
            if (updatedData.getFullName() != null) {
                existingUser.setFullName(updatedData.getFullName());
            }
            if (updatedData.getVillage() != null) {
                existingUser.setVillage(updatedData.getVillage());
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
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId));
    }
}
