package com.agrifarms.common.service;

import com.agrifarms.common.entity.User;
import com.agrifarms.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
            if (updatedData.getProfileImageUrl() != null) {
                existingUser.setProfileImageUrl(updatedData.getProfileImageUrl());
            }
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId));
    }
}
