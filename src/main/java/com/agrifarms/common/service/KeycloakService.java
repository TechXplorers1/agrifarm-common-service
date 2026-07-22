package com.agrifarms.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

// Keycloak disabled - Removed @Service
public class KeycloakService {

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.admin-realm:master}")
    private String adminRealm;

    @Value("${keycloak.admin-client-id:admin-cli}")
    private String adminClientId;

    @Value("${keycloak.admin-client-secret:}")
    private String adminClientSecret;

    @Value("${keycloak.admin-username:admin}")
    private String adminUsername;

    @Value("${keycloak.admin-password:}")
    private String adminPassword;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Obtains an Admin Access Token from Keycloak using either client credentials
     * or resource owner password credentials flow.
     */
    public String getAdminAccessToken() {
        String tokenUrl = String.format("%s/realms/%s/protocol/openid-connect/token", authServerUrl, adminRealm);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        if (adminClientSecret != null && !adminClientSecret.trim().isEmpty()) {
            map.add("grant_type", "client_credentials");
            map.add("client_id", adminClientId);
            map.add("client_secret", adminClientSecret);
        } else {
            map.add("grant_type", "password");
            map.add("client_id", adminClientId != null && !adminClientId.isEmpty() ? adminClientId : "admin-cli");
            map.add("username", adminUsername);
            map.add("password", adminPassword);
        }

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return (String) response.getBody().get("access_token");
            }
        } catch (Exception e) {
            System.err.println("[Keycloak] Failed to retrieve admin access token: " + e.getMessage());
        }
        return null;
    }

    /**
     * Searches for a user in Keycloak by username (phone number).
     * Returns Keycloak User ID (UUID) if found, null otherwise.
     */
    public String findUserIdByUsername(String username) {
        String adminToken = getAdminAccessToken();
        if (adminToken == null) {
            System.err.println("[Keycloak] Admin token is null. Cannot search user.");
            return null;
        }

        String searchUrl = String.format("%s/admin/realms/%s/users?username=%s", authServerUrl, realm, username);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminToken);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<List> response = restTemplate.exchange(searchUrl, HttpMethod.GET, request, List.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null && !response.getBody().isEmpty()) {
                Map user = (Map) response.getBody().get(0);
                return (String) user.get("id");
            }
        } catch (Exception e) {
            System.err.println("[Keycloak] Failed to search for user: " + e.getMessage());
        }
        return null;
    }

    /**
     * Creates a new user profile in Keycloak.
     * Returns the created Keycloak User ID (UUID) if successful, null otherwise.
     */
    public String createUser(String username, String password, String phoneNumber, String fullName, String role) {
        String adminToken = getAdminAccessToken();
        if (adminToken == null) {
            System.err.println("[Keycloak] Admin token is null. Cannot create user.");
            return null;
        }

        String createUrl = String.format("%s/admin/realms/%s/users", authServerUrl, realm);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Build User representation body
        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("enabled", true);
        user.put("emailVerified", false);

        if (fullName != null && !fullName.trim().isEmpty()) {
            String[] parts = fullName.trim().split("\\s+", 2);
            if (parts.length > 0) user.put("firstName", parts[0]);
            if (parts.length > 1) user.put("lastName", parts[1]);
        }

        // Credentials
        Map<String, Object> credential = new HashMap<>();
        credential.put("type", "password");
        credential.put("value", password);
        credential.put("temporary", false);
        user.put("credentials", Collections.singletonList(credential));

        // Attributes
        Map<String, List<String>> attributes = new HashMap<>();
        if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
            attributes.put("phone_number", Collections.singletonList(phoneNumber));
        }
        user.put("attributes", attributes);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(user, headers);

        try {
            ResponseEntity<Void> response = restTemplate.postForEntity(createUrl, request, Void.class);
            if (response.getStatusCode() == HttpStatus.CREATED) {
                // Keycloak returns the created user's URI in Location header:
                // .../admin/realms/agrifarms/users/{userId}
                String location = response.getHeaders().getFirst("Location");
                if (location != null) {
                    String userId = location.substring(location.lastIndexOf("/") + 1);
                    
                    // Assign requested realm role if applicable
                    if (role != null && !role.trim().isEmpty()) {
                        assignRole(userId, role);
                    }
                    return userId;
                }
            }
        } catch (HttpClientErrorException.Conflict conflict) {
            System.out.println("[Keycloak] User already exists in Keycloak (Conflict). Finding ID...");
            return findUserIdByUsername(username);
        } catch (Exception e) {
            System.err.println("[Keycloak] Failed to create user in Keycloak: " + e.getMessage());
        }
        return null;
    }

    /**
     * Assigns a realm role to a Keycloak user by userId.
     */
    public void assignRole(String userId, String roleName) {
        String adminToken = getAdminAccessToken();
        if (adminToken == null) return;

        // 1. Get role representation
        String getRoleUrl = String.format("%s/admin/realms/%s/roles/%s", authServerUrl, realm, roleName);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminToken);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Void> getRequest = new HttpEntity<>(headers);

        Map roleRep = null;
        try {
            ResponseEntity<Map> roleResponse = restTemplate.exchange(getRoleUrl, HttpMethod.GET, getRequest, Map.class);
            if (roleResponse.getStatusCode() == HttpStatus.OK) {
                roleRep = roleResponse.getBody();
            }
        } catch (Exception e) {
            System.err.println("[Keycloak] Role not found: " + roleName + ". Trying lowercase...");
            // Try lowercase fallback
            try {
                String getRoleUrlLower = String.format("%s/admin/realms/%s/roles/%s", authServerUrl, realm, roleName.toLowerCase());
                ResponseEntity<Map> roleResponseLower = restTemplate.exchange(getRoleUrlLower, HttpMethod.GET, getRequest, Map.class);
                if (roleResponseLower.getStatusCode() == HttpStatus.OK) {
                    roleRep = roleResponseLower.getBody();
                }
            } catch (Exception ex) {
                System.err.println("[Keycloak] Role check failed: " + ex.getMessage());
            }
        }

        if (roleRep == null) {
            System.err.println("[Keycloak] Cannot assign role: " + roleName + " is not defined in realm.");
            return;
        }

        // 2. Post role mapping
        String assignRoleUrl = String.format("%s/admin/realms/%s/users/%s/role-mappings/realm", authServerUrl, realm, userId);
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<Map> roleList = Collections.singletonList(roleRep);
        HttpEntity<List<Map>> assignRequest = new HttpEntity<>(roleList, headers);

        try {
            restTemplate.postForEntity(assignRoleUrl, assignRequest, Void.class);
            System.out.println("[Keycloak] Assigned role " + roleName + " to Keycloak user " + userId);
        } catch (Exception e) {
            System.err.println("[Keycloak] Failed to assign role to user: " + e.getMessage());
        }
    }

    /**
     * Authenticates the user with Keycloak via Direct Access Grant flow.
     * Returns token map containing access_token, refresh_token, expires_in, etc.
     */
    public Map<String, Object> authenticateUser(String username, String password) {
        String tokenUrl = String.format("%s/realms/%s/protocol/openid-connect/token", authServerUrl, realm);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("client_id", clientId);
        map.add("username", username);
        map.add("password", password);
        map.add("scope", "openid profile email offline_access");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> body = response.getBody();
                return body;
            }
        } catch (HttpClientErrorException e) {
            System.err.println("[Keycloak Direct Grant] Authentication failed: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            System.err.println("[Keycloak Direct Grant] Authentication request error: " + e.getMessage());
        }
        return null;
    }
}
