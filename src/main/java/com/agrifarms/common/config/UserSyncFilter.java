package com.agrifarms.common.config;

import com.agrifarms.common.entity.User;
import com.agrifarms.common.repository.UserRepository;
import com.agrifarms.common.service.NotificationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

// Removed @Component as Keycloak UserSyncFilter is replaced by JwtAuthenticationFilter
public class UserSyncFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public UserSyncFilter(UserRepository userRepository, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            Jwt jwt = jwtAuthenticationToken.getToken();
            String keycloakId = jwt.getSubject(); // Keycloak sub (UUID)
            String email = jwt.getClaimAsString("email");
            String fullName = jwt.getClaimAsString("name");
            String phoneNumber = jwt.getClaimAsString("phone_number");

            if (fullName == null || fullName.trim().isEmpty()) {
                fullName = jwt.getClaimAsString("preferred_username");
            }
            if (fullName == null || fullName.trim().isEmpty()) {
                fullName = "Keycloak User";
            }

            // Extract role - check realm_access roles claim
            String role = "Farmer"; // Default fallback
            Map<String, Object> realmAccess = jwt.getClaim("realm_access");
            if (realmAccess != null && realmAccess.containsKey("roles")) {
                java.util.List<String> roles = (java.util.List<String>) realmAccess.get("roles");
                if (roles.contains("Owner") || roles.contains("owner") || roles.contains("PROVIDER") || roles.contains("provider")) {
                    role = "Owner";
                }
            }

            // Sync user details in the local transactional database
            Optional<User> userOpt = userRepository.findByKeycloakId(keycloakId);

            if (userOpt.isEmpty()) {
                User user = null;

                // 1. Try to merge with legacy profile by email
                if (email != null && !email.trim().isEmpty()) {
                    user = userRepository.findByEmail(email).orElse(null);
                }

                // 2. Try to merge with legacy profile by phone
                if (user == null && phoneNumber != null && !phoneNumber.trim().isEmpty()) {
                    String cleanedPhone = phoneNumber.replaceAll("\\D", "");
                    if (cleanedPhone.length() >= 10) {
                        cleanedPhone = cleanedPhone.substring(cleanedPhone.length() - 10);
                        user = userRepository.findByPhoneNumber(cleanedPhone).orElse(null);
                    }
                }

                if (user != null) {
                    // Legacy user found! Link Keycloak account
                    user.setKeycloakId(keycloakId);
                    if (user.getEmail() == null && email != null) {
                        user.setEmail(email);
                    }
                    userRepository.save(user);
                } else {
                    // Create a brand new user record
                    User newUser = new User();
                    newUser.setKeycloakId(keycloakId);
                    newUser.setEmail(email);
                    newUser.setFullName(fullName);
                    newUser.setRole(role);
                    newUser.setStatus("Active");

                    if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
                        String cleanedPhone = phoneNumber.replaceAll("\\D", "");
                        if (cleanedPhone.length() >= 10) {
                            cleanedPhone = cleanedPhone.substring(cleanedPhone.length() - 10);
                            newUser.setPhoneNumber(cleanedPhone);
                        }
                    }

                    userRepository.save(newUser);
                    notificationService.notifyAdmin(
                        "New Keycloak user registered", 
                        newUser.getFullName() + " joined as a " + newUser.getRole(), 
                        "success", 
                        newUser.getUserId()
                    );
                }
            } else {
                // User already exists, quietly make sure email or role are up to date
                User existingUser = userOpt.get();
                boolean modified = false;
                if (email != null && !email.equals(existingUser.getEmail())) {
                    existingUser.setEmail(email);
                    modified = true;
                }
                if (!role.equalsIgnoreCase(existingUser.getRole()) && !existingUser.getRole().equalsIgnoreCase("Owner")) {
                    // If user is designated as owner in keycloak, update role locally
                    existingUser.setRole(role);
                    modified = true;
                }
                if (modified) {
                    userRepository.save(existingUser);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
