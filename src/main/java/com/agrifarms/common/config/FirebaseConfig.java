package com.agrifarms.common.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                // Read from the classpath using ClassPathResource
                ClassPathResource resource = new ClassPathResource("agrifarms-firebase-service-account.json");
                
                // If it doesn't exist, just log it instead of crashing the whole app
                if (!resource.exists()) {
                    System.err.println("WARNING: agrifarms-firebase-service-account.json not found in resources. Firebase Admin SDK not initialized.");
                    return;
                }
                
                InputStream serviceAccount = resource.getInputStream();

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
                System.out.println("Firebase Admin SDK initialized successfully.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to initialize Firebase Admin SDK: " + e.getMessage());
        }
    }
}
