package com.agrifarms.common.repository;

import com.agrifarms.common.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    
    // Finds the latest active, unused OTP for an email that has not expired yet
    Optional<Otp> findTopByEmailAndOtpCodeAndIsUsedFalseAndExpiresAtAfterOrderByCreatedAtDesc(
            String email, String otpCode, LocalDateTime now);

    // Finds all active, unused OTPs for an email (useful to mark them all as used/invalidated when generating a new one)
    List<Otp> findAllByEmailAndIsUsedFalse(String email);
}
