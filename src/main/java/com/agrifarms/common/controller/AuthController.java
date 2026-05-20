package com.agrifarms.common.controller;

import com.agrifarms.common.entity.User;
import com.agrifarms.common.service.OtpService;
import com.agrifarms.common.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final OtpService otpService;
    private final UserService userService;

    public AuthController(OtpService otpService, UserService userService) {
        this.otpService = otpService;
        this.userService = userService;
    }

    /**
     * Register a new user profile directly in the database.
     * Starts with 'PENDING' status and sends verification OTP via SMTP.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        String role = request.get("role");

        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty() || role == null || role.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email, password, and role are all required"));
        }

        email = email.trim();
        password = password.trim();
        role = role.trim();

        if (userService.getUserByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email address already registered"));
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password); // Storing the password in the table as requested
        newUser.setRole(role);
        newUser.setFullName("New User");
        newUser.setStatus("PENDING");

        try {
            userService.createUser(newUser);
            // Trigger SMTP OTP instantly on signup
            otpService.generateAndSendOtp(email);
            return ResponseEntity.ok(Map.of("message", "Registration successful. OTP verification code sent."));
        } catch (Exception e) {
            System.err.println("[Registration Error] Failed: " + e.getMessage());
            return ResponseEntity.status(500).body(Map.of("message", "Failed to register user: " + e.getMessage()));
        }
    }

    /**
     * Log in a user by validating their email and password against the database 'users' table.
     * Intercepts unverified accounts by resending a code and returning 'PENDING'.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Both Email and Password are required"));
        }

        email = email.trim();
        password = password.trim();

        Optional<User> userOpt = userService.getUserByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid email or password"));
        }

        User user = userOpt.get();
        if (!password.equals(user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid email or password"));
        }

        // If PENDING, send OTP and notify client to trigger the verification modal
        if ("PENDING".equalsIgnoreCase(user.getStatus())) {
            try {
                otpService.generateAndSendOtp(email);
                return ResponseEntity.ok(Map.of(
                    "status", "PENDING",
                    "email", email,
                    "message", "Verification pending. A new OTP has been sent."
                ));
            } catch (Exception e) {
                return ResponseEntity.status(500).body(Map.of("message", "Failed to send OTP: " + e.getMessage()));
            }
        }

        // Return user details directly upon successful active login
        return ResponseEntity.ok(Map.of(
            "status", "Active",
            "userId", user.getUserId(),
            "email", user.getEmail(),
            "fullName", user.getFullName() != null ? user.getFullName() : "New User",
            "phoneNumber", user.getPhoneNumber() != null ? user.getPhoneNumber() : "",
            "role", user.getRole() != null ? user.getRole() : ""
        ));
    }

    /**
     * Send OTP endpoint. Takes JSON body: { "email": "user@example.com" }
     */
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email address is required"));
        }

        try {
            otpService.generateAndSendOtp(email.trim());
            return ResponseEntity.ok(Map.of("message", "OTP verification code sent successfully"));
        } catch (Exception e) {
            System.err.println("[SMTP Error] Failed to send OTP: " + e.getMessage());
            return ResponseEntity.status(500).body(Map.of("message", "SMTP delivery failure: " + e.getMessage()));
        }
    }

    /**
     * Verify OTP endpoint. Takes JSON body: { "email": "user@example.com", "otp": "123456" }
     */
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");

        if (email == null || email.trim().isEmpty() || otp == null || otp.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Both Email and OTP code are required"));
        }

        boolean isSuccess = otpService.verifyOtp(email.trim(), otp.trim());
        if (isSuccess) {
            return ResponseEntity.ok(Map.of("message", "OTP verified and user status activated successfully"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid, expired, or already used OTP code entered"));
        }
    }
}
