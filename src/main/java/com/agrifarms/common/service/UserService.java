package com.agrifarms.common.service;

import com.agrifarms.common.entity.User;
import com.agrifarms.common.repository.UserRepository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable(value = "users", key = "#userId")
    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    @Cacheable(value = "ownerNames", key = "#ownerId")
    public String getOwnerNameWithCache(String ownerId) {
        if (ownerId == null || ownerId.trim().isEmpty()) {
            return "Unknown Owner";
        }
        return userRepository.findById(ownerId)
                .map(user -> user.getFullName() != null && !user.getFullName().trim().isEmpty() ? user.getFullName() : "Unknown Owner")
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

    public User createUser(User user) {
        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            // You can create a custom PhoneAlreadyExistsException for better API responses
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already registered");
        }
        return userRepository.save(user);
    }

    public java.util.List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
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
            if (updatedData.getVillage() != null) {
                existingUser.setVillage(updatedData.getVillage());
            }
            if (updatedData.getDistrict() != null) {
                existingUser.setDistrict(updatedData.getDistrict());
            }
            if (updatedData.getProfileImageUrl() != null) {
                existingUser.setProfileImageUrl(updatedData.getProfileImageUrl());
            }
            if (updatedData.getFcmToken() != null) {
                existingUser.setFcmToken(updatedData.getFcmToken());
            }
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId));
    }

    public void updateFcmToken(String userId, String fcmToken) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setFcmToken(fcmToken);
            userRepository.save(user);
        });
    }
}
