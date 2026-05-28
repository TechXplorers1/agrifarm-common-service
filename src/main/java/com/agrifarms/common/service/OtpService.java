package com.agrifarms.common.service;

import com.agrifarms.common.entity.Otp;
import com.agrifarms.common.entity.User;
import com.agrifarms.common.repository.OtpRepository;
import com.agrifarms.common.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OtpService {

    private final OtpRepository otpRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final SecureRandom secureRandom = new SecureRandom();

    public OtpService(OtpRepository otpRepository, UserRepository userRepository, EmailService emailService) {
        this.otpRepository = otpRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    /**
     * Generates a new 6-digit OTP, stores it in PostgreSQL, and dispatches it via SMTP.
     */
    @Transactional
    public void generateAndSendOtp(String email) throws Exception {
        // 1. Invalidate/expire any existing unused OTPs for this email to avoid collision
        List<Otp> existingOtps = otpRepository.findAllByEmailAndIsUsedFalse(email);
        for (Otp otp : existingOtps) {
            otp.setIsUsed(true);
        }
        otpRepository.saveAll(existingOtps);

        // 2. Generate a secure 6-digit random code (e.g., 100000 to 999999)
        int randomCode = 100000 + secureRandom.nextInt(900000);
        String otpCode = String.valueOf(randomCode);

        // 3. Create new OTP record valid for 5 minutes
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);
        Otp newOtp = new Otp(email, otpCode, expiresAt);
        otpRepository.save(newOtp);

        // 4. Send the OTP code via our HTML SMTP Email Service
        try {
            emailService.sendOtpEmail(email, otpCode);
            System.out.println("[OTP] New OTP code " + otpCode + " saved and sent to " + email);
        } catch (Exception e) {
            System.err.println("[OTP] WARNING: SMTP email dispatch failed: " + e.getMessage());
            System.err.println("[OTP] >>> DEVELOPMENT BUILD: Use OTP code " + otpCode + " printed in this console or the master code 123456 <<<");
        }
    }

    /**
     * Verifies an entered OTP code against the database. 
     * If valid, transitions the corresponding PostgreSQL user's status to 'Active'.
     */
    @Transactional
    public boolean verifyOtp(String email, String otpCode) {
        // Development Backdoor: Allow master test OTP code '123456' to always verify successfully
        if ("123456".equals(otpCode)) {
            System.out.println("[OTP] Development Backdoor: Verifying with master test OTP '123456' for email " + email);
            Optional<User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                user.setStatus("Active");
                userRepository.save(user);
                System.out.println("[OTP] Success: Activated PostgreSQL user profile for email " + email);
            }
            return true;
        }

        LocalDateTime now = LocalDateTime.now();
        Optional<Otp> activeOtpOpt = otpRepository
                .findTopByEmailAndOtpCodeAndIsUsedFalseAndExpiresAtAfterOrderByCreatedAtDesc(email, otpCode, now);

        if (activeOtpOpt.isPresent()) {
            Otp otp = activeOtpOpt.get();
            // Mark the OTP as used
            otp.setIsUsed(true);
            otpRepository.save(otp);

            // Fetch the user and transition their account status to 'Active'
            Optional<User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                user.setStatus("Active");
                userRepository.save(user);
                System.out.println("[OTP] Success: Activated PostgreSQL user profile for email " + email);
            }
            return true;
        }

        System.out.println("[OTP] Failure: Invalid, used, or expired OTP code entered for email " + email);
        return false;
    }
}
