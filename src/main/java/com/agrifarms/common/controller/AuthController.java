package com.agrifarms.common.controller;

import com.agrifarms.common.entity.User;
import com.agrifarms.common.repository.UserRepository;
import com.agrifarms.common.service.KeycloakService;
import com.agrifarms.common.service.OtpService;
import com.agrifarms.common.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final OtpService otpService;
    private final UserService userService;
    private final KeycloakService keycloakService;
    private final UserRepository userRepository;

    public AuthController(OtpService otpService, UserService userService, KeycloakService keycloakService, UserRepository userRepository) {
        this.otpService = otpService;
        this.userService = userService;
        this.keycloakService = keycloakService;
        this.userRepository = userRepository;
    }

    private static class FirebaseTokenPayload {
        private final String uid;
        private final String phoneNumber;
        private final String email;
        private final String name;

        public FirebaseTokenPayload(String uid, String phoneNumber, String email, String name) {
            this.uid = uid;
            this.phoneNumber = phoneNumber;
            this.email = email;
            this.name = name;
        }

        public String getUid() { return uid; }
        public String getPhoneNumber() { return phoneNumber; }
        public String getEmail() { return email; }
        public String getName() { return name; }
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
            return ResponseEntity.status(401).body(Map.of("message", "Please sign up before logging in."));
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

    /**
     * Firebase phone login authentication endpoint.
     * Verifies Firebase ID Token, registers/syncs the user in Keycloak,
     * registers/syncs the user in local SQL database, and returns Keycloak OIDC JWT.
     */
    @PostMapping("/firebase-login")
    public ResponseEntity<?> firebaseLogin(@RequestBody Map<String, String> request) {
        String idToken = request.get("idToken");
        String role = request.get("role"); // 'Farmer' or 'Owner'
        String fullName = request.get("fullName"); // optional, for registration

        if (idToken == null || idToken.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Firebase ID Token is required"));
        }

        try {
            // 1. Verify Firebase ID Token
            FirebaseTokenPayload payload = verifyFirebaseToken(idToken);
            String uid = payload.getUid();
            String phoneNumber = payload.getPhoneNumber();
            String email = payload.getEmail();
            String name = (fullName != null && !fullName.trim().isEmpty()) ? fullName : payload.getName();

            if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("message", "Verified phone number not found in Firebase token"));
            }

            // Clean phone number to match the 10-digit DB format
            String cleanedPhone = phoneNumber.replaceAll("\\D", "");
            if (cleanedPhone.length() >= 10) {
                cleanedPhone = cleanedPhone.substring(cleanedPhone.length() - 10);
            }

            // Default role if not provided
            if (role == null || role.trim().isEmpty()) {
                role = "Farmer";
            }

            // 2. Hash Firebase UID to use as Keycloak password
            String keycloakPassword = generateKeycloakPassword(uid);

            // 3. Sync with Keycloak
            String keycloakUserId = keycloakService.findUserIdByUsername(cleanedPhone);
            if (keycloakUserId == null) {
                // User doesn't exist in Keycloak. Register them!
                keycloakUserId = keycloakService.createUser(cleanedPhone, keycloakPassword, cleanedPhone, name, role);
                if (keycloakUserId == null) {
                    return ResponseEntity.status(500).body(Map.of("message", "Failed to register user in Keycloak"));
                }
            }

            // 4. Sync with Local DB
            Optional<User> localUserOpt = userRepository.findByPhoneNumber(cleanedPhone);
            User localUser;
            if (localUserOpt.isEmpty()) {
                localUser = new User();
                localUser.setPhoneNumber(cleanedPhone);
                localUser.setKeycloakId(keycloakUserId);
                localUser.setEmail(email);
                localUser.setFullName(name != null && !name.trim().isEmpty() ? name : "Farmer " + cleanedPhone);
                localUser.setRole(role);
                localUser.setStatus("Active");
                localUser = userRepository.save(localUser);
            } else {
                localUser = localUserOpt.get();
                boolean modified = false;
                if (localUser.getKeycloakId() == null) {
                    localUser.setKeycloakId(keycloakUserId);
                    modified = true;
                }
                if (email != null && !email.equals(localUser.getEmail())) {
                    localUser.setEmail(email);
                    modified = true;
                }
                if (name != null && !name.trim().isEmpty() && !name.equals(localUser.getFullName())) {
                    localUser.setFullName(name);
                    modified = true;
                }
                if (modified) {
                    localUser = userRepository.save(localUser);
                }
            }

            // 5. Authenticate against Keycloak via Direct Access Grant
            Map<String, Object> tokens = keycloakService.authenticateUser(cleanedPhone, keycloakPassword);
            if (tokens == null) {
                return ResponseEntity.status(401).body(Map.of("message", "Keycloak Direct Grant authentication failed"));
            }

            // 6. Return response containing token credentials & user profile
            Map<String, Object> response = new HashMap<>();
            response.put("access_token", tokens.get("access_token"));
            response.put("refresh_token", tokens.get("refresh_token"));
            response.put("expires_in", tokens.get("expires_in"));
            response.put("userId", localUser.getUserId());
            response.put("fullName", localUser.getFullName());
            response.put("phoneNumber", localUser.getPhoneNumber());
            response.put("email", localUser.getEmail() != null ? localUser.getEmail() : "");
            response.put("role", localUser.getRole());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("[Firebase Login Error] Failed: " + e.getMessage());
            return ResponseEntity.status(401).body(Map.of("message", "Authentication failed: " + e.getMessage()));
        }
    }

    // ── Allowed roles ─────────────────────────────────────────────────────────
    private static final java.util.Set<String> ALLOWED_ROLES =
        java.util.Set.of("ADMIN", "FARMER", "OWNER");

    private String validateRole(String role) {
        if (role == null || role.trim().isEmpty()) return "FARMER";
        String upper = role.trim().toUpperCase();
        return ALLOWED_ROLES.contains(upper) ? upper : "FARMER";
    }

    /**
     * DEV MODE: Static OTP login — no Firebase required.
     * OTP validation is handled on the Flutter side (123456).
     * This endpoint simply creates a new user on sign-up or returns
     * the existing user on login using only phoneNumber + role + fullName.
     *
     * Roles accepted: ADMIN, FARMER, OWNER (any other is rejected → 400).
     */
    @PostMapping("/static-login")
    public ResponseEntity<?> staticLogin(@RequestBody Map<String, Object> request) {
        String phoneNumber = (String) request.get("phoneNumber");
        String role        = (String) request.get("role");
        String fullName    = (String) request.get("fullName");
        Boolean isLogin    = (Boolean) request.get("isLogin");

        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "phoneNumber is required"));
        }

        // Normalise phone to 10 digits
        String cleanedPhone = phoneNumber.replaceAll("\\D", "");
        if (cleanedPhone.length() > 10) {
            cleanedPhone = cleanedPhone.substring(cleanedPhone.length() - 10);
        }
        if (cleanedPhone.length() != 10) {
            return ResponseEntity.badRequest().body(Map.of("message", "Phone number must be exactly 10 digits"));
        }

        // Validate & normalise role — only ADMIN / FARMER / OWNER stored
        String normalizedRole = validateRole(role);
        // Reject if the submitted role is explicitly outside allowed set
        if (role != null && !role.trim().isEmpty()
                && !ALLOWED_ROLES.contains(role.trim().toUpperCase())) {
            return ResponseEntity.badRequest().body(
                Map.of("message", "Invalid role '" + role + "'. Allowed roles: ADMIN, FARMER, OWNER"));
        }

        Optional<User> existingOpt = userRepository.findByPhoneNumber(cleanedPhone);

        User user;
        if (existingOpt.isPresent()) {
            // Existing user — return their stored profile
            user = existingOpt.get();
        } else {
            // New user — create them
            if (Boolean.TRUE.equals(isLogin)) {
                return ResponseEntity.status(404).body(
                    Map.of("message", "No account found for this phone number. Please sign up first."));
            }
            user = new User();
            user.setPhoneNumber(cleanedPhone);
            user.setRole(normalizedRole);
            user.setFullName((fullName != null && !fullName.trim().isEmpty()) ? fullName.trim() : "User " + cleanedPhone);
            user.setStatus("Active");
            user = userRepository.save(user);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("userId",      user.getUserId());
        response.put("fullName",    user.getFullName() != null ? user.getFullName() : "");
        response.put("phoneNumber", user.getPhoneNumber());
        response.put("role",        user.getRole() != null ? user.getRole() : normalizedRole);
        response.put("status",      user.getStatus() != null ? user.getStatus() : "Active");
        response.put("email",       user.getEmail() != null ? user.getEmail() : "");
        return ResponseEntity.ok(response);
    }

    private FirebaseTokenPayload verifyFirebaseToken(String idToken) throws Exception {
        // Try Firebase Admin SDK first if initialized
        if (!com.google.firebase.FirebaseApp.getApps().isEmpty()) {
            try {
                com.google.firebase.auth.FirebaseToken decodedToken =
                    com.google.firebase.auth.FirebaseAuth.getInstance().verifyIdToken(idToken);
                return new FirebaseTokenPayload(
                    decodedToken.getUid(),
                    (String) decodedToken.getClaims().get("phone_number"),
                    decodedToken.getEmail(),
                    decodedToken.getName()
                );
            } catch (Exception e) {
                System.err.println("Firebase Admin SDK verification failed, using fallback Nimbus verifier: " + e.getMessage());
            }
        }

        // Fallback: decode & verify signature using standard Nimbus JWT libraries
        try {
            NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri("https://www.googleapis.com/service_accounts/v1/jwk/securetoken@system.gserviceaccount.com").build();
            Jwt jwt = jwtDecoder.decode(idToken);

            String projectId = "agrifarms-174f9"; // from google-services.json
            String expectedIssuer = "https://securetoken.google.com/" + projectId;

            if (!expectedIssuer.equals(jwt.getIssuer().toString())) {
                throw new Exception("Invalid token issuer: " + jwt.getIssuer());
            }
            if (!jwt.getAudience().contains(projectId)) {
                throw new Exception("Invalid token audience (project mismatch)");
            }

            String uid = jwt.getSubject();
            String phone = jwt.getClaimAsString("phone_number");
            String email = jwt.getClaimAsString("email");
            String name = jwt.getClaimAsString("name");

            return new FirebaseTokenPayload(uid, phone, email, name);
        } catch (Exception e) {
            throw new Exception("Firebase ID Token verification failed: " + e.getMessage(), e);
        }
    }

    private String generateKeycloakPassword(String firebaseUid) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            String salted = firebaseUid + "agrifarms_secret_salt_2026!";
            byte[] hash = digest.digest(salted.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            return firebaseUid; // fallback
        }
    }
}


