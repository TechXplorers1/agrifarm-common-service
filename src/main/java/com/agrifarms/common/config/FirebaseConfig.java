package com.agrifarms.common.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                InputStream serviceAccount = getServiceAccountInputStream();

                if (serviceAccount == null) {
                    System.err.println(" WARNING: Firebase Service Account JSON key not found!");
                    System.err.println(" Action Required: Save your downloaded Firebase private key JSON file to:");
                    System.err.println(
                            "   agrifarm-common-service/src/main/resources/agrifarms-firebase-service-account.json");
                    return;
                }

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
                System.out.println("✅ Firebase Admin SDK initialized successfully.");
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to initialize Firebase Admin SDK: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private InputStream getServiceAccountInputStream() {
        // 1. Check classpath for agrifarms-firebase-service-account.json
        try {
            ClassPathResource resource = new ClassPathResource("agrifarms-firebase-service-account.json");
            if (resource.exists()) {
                System.out.println(
                        "🔒 Loading Firebase credentials from classpath: agrifarms-firebase-service-account.json");
                return resource.getInputStream();
            }
        } catch (IOException ignored) {
        }

        // 2. Check classpath for serviceAccountKey.json
        try {
            ClassPathResource resource = new ClassPathResource("serviceAccountKey.json");
            if (resource.exists()) {
                System.out.println("🔒 Loading Firebase credentials from classpath: serviceAccountKey.json");
                return resource.getInputStream();
            }
        } catch (IOException ignored) {
        }

        // 3. Check environment variable FIREBASE_CONFIG_PATH
        String envPath = System.getenv("FIREBASE_CONFIG_PATH");
        if (envPath != null && !envPath.isEmpty()) {
            File f = new File(envPath);
            if (f.exists()) {
                try {
                    System.out.println("🔒 Loading Firebase credentials from ENV path: " + envPath);
                    return new FileInputStream(f);
                } catch (IOException ignored) {
                }
            }
        }

        return null;
    }
}
