package com.agrifarms.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class Msg91Service {

    @Value("${msg91.authkey}")
    private String authKey;

    @Value("${msg91.template-id}")
    private String templateId;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Sends OTP to the given phone number using MSG91.
     * Prepend 91 (India) if the phone number is 10 digits and doesn't have a country code.
     */
    public boolean sendOtp(String phoneNumber) {
        try {
            String formattedPhone = formatPhoneNumber(phoneNumber);
            String url = "https://control.msg91.com/api/v5/otp";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("authkey", authKey);

            Map<String, Object> body = new HashMap<>();
            body.put("template_id", templateId);
            body.put("mobile", formattedPhone);
            body.put("otp_length", 6);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> respBody = response.getBody();
                System.out.println("[MSG91] Response: " + respBody);
                String type = (String) respBody.get("type");
                if ("success".equalsIgnoreCase(type)) {
                    System.out.println("[MSG91] OTP successfully sent to " + formattedPhone);
                    return true;
                } else {
                    System.err.println("[MSG91] Failed to send OTP: " + respBody.get("message"));
                }
            }
        } catch (Exception e) {
            System.err.println("[MSG91] Error sending OTP: " + e.getMessage());
        }
        return false;
    }

    /**
     * Verifies the OTP with MSG91.
     */
    public boolean verifyOtp(String phoneNumber, String otpCode) {
        // Development Backdoor: Allow master test OTP code '123456'
        if ("123456".equals(otpCode)) {
            System.out.println("[MSG91] Development Backdoor: Verifying with master test OTP '123456' for phone " + phoneNumber);
            return true;
        }

        try {
            String formattedPhone = formatPhoneNumber(phoneNumber);
            
            // Using UriComponentsBuilder to build the verification URL
            String url = UriComponentsBuilder.fromHttpUrl("https://control.msg91.com/api/v5/otp/verify")
                    .queryParam("otp", otpCode)
                    .queryParam("mobile", formattedPhone)
                    .toUriString();

            HttpHeaders headers = new HttpHeaders();
            headers.set("authkey", authKey);

            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<Map> response = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, entity, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> respBody = response.getBody();
                String type = (String) respBody.get("type");
                if ("success".equalsIgnoreCase(type)) {
                    System.out.println("[MSG91] OTP successfully verified for " + formattedPhone);
                    return true;
                } else {
                    System.err.println("[MSG91] Verification failed: " + respBody.get("message"));
                }
            }
        } catch (Exception e) {
            System.err.println("[MSG91] Error verifying OTP: " + e.getMessage());
        }
        return false;
    }

    private String formatPhoneNumber(String phoneNumber) {
        // Remove all non-digits
        String cleaned = phoneNumber.replaceAll("\\D", "");
        // If 10 digits, assume India (+91)
        if (cleaned.length() == 10) {
            return "91" + cleaned;
        }
        return cleaned;
    }
}
