package com.agrifarms.common.repository;

import com.agrifarms.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByPhoneNumber(String phoneNumber);

    // Add this to check for duplicates efficiently
    boolean existsByPhoneNumber(String phoneNumber);
}