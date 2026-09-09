package com.agrifarms.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class MediaService {

    private static final Logger log = LoggerFactory.getLogger(MediaService.class);

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    private S3Client s3Client;
    private final Path localStorageDir = Paths.get("uploads", "images");

    @PostConstruct
    public void init() {
        log.info("Initializing MediaService with S3 region='{}' bucket='{}'", region, bucketName);
        try {
            s3Client = S3Client.builder()
                    .region(Region.of(region))
                    .build();
        } catch (Exception e) {
            log.warn("S3Client initialization failed (AWS credentials might be absent locally): {}", e.getMessage());
        }

        // Ensure local upload directory exists as local fallback
        try {
            Files.createDirectories(localStorageDir);
            log.info("Local media upload directory initialized at: {}", localStorageDir.toAbsolutePath());
        } catch (Exception e) {
            log.error("Failed to create local media upload directory: {}", e.getMessage(), e);
        }
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getRegion() {
        return region;
    }

    public String saveFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Cannot upload an empty file.");
        }

        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String filename = UUID.randomUUID().toString() + extension;

        // 1. Try S3 upload if S3 client is initialized
        if (s3Client != null) {
            try {
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key("images/" + filename)
                        .contentType(file.getContentType() != null ? file.getContentType() : "image/jpeg")
                        .build();

                log.info("Uploading file to S3: bucket='{}' key='{}' size={}", bucketName, putObjectRequest.key(),
                        file.getSize());

                var response = s3Client.putObject(putObjectRequest,
                        RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

                log.info("S3 upload successful: status={}", response.sdkHttpResponse().statusCode());
                String publicUrl = String.format("https://%s.s3.%s.amazonaws.com/images/%s", bucketName, region,
                        filename);

                // Also save a local backup copy to ensure local download endpoint works
                // seamlessly
                saveToLocalStorage(file, filename);

                return publicUrl;
            } catch (Exception e) {
                log.warn("[S3 Fallback] S3 upload failed/unreachable ({}), saving file locally on disk instead.",
                        e.getMessage());
            }
        }

        // 2. Fallback to Local Disk Storage
        saveToLocalStorage(file, filename);
        String localUrl = "/api/media/download/" + filename;
        log.info("File saved to local storage successfully. Local URL={}", localUrl);
        return localUrl;
    }

    private void saveToLocalStorage(MultipartFile file, String filename) throws IOException {
        Files.createDirectories(localStorageDir);
        Path targetPath = localStorageDir.resolve(filename);
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        log.info("File written to local disk: {}", targetPath.toAbsolutePath());
    }

    public byte[] getFile(String filename) throws IOException {
        // Sanitize filename to prevent directory traversal
        String cleanFilename = Paths.get(filename).getFileName().toString();
        Path localFilePath = localStorageDir.resolve(cleanFilename);

        // 1. Check if file exists in local storage
        if (Files.exists(localFilePath)) {
            log.info("Serving file from local disk: {}", localFilePath.toAbsolutePath());
            return Files.readAllBytes(localFilePath);
        }

        // 2. Fallback to fetch from AWS S3
        if (s3Client != null) {
            try {
                GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key("images/" + cleanFilename)
                        .build();

                log.info("Fetching file from S3: bucket='{}' key='{}'", bucketName, getObjectRequest.key());
                byte[] bytes = s3Client.getObjectAsBytes(getObjectRequest).asByteArray();

                // Save locally for future fast caching
                try {
                    Files.createDirectories(localStorageDir);
                    Files.write(localFilePath, bytes);
                } catch (Exception ex) {
                    log.warn("Failed to cache S3 file locally: {}", ex.getMessage());
                }

                return bytes;
            } catch (Exception e) {
                log.warn("S3 getObject failed for filename={}: {}", cleanFilename, e.getMessage());
            }
        }

        log.warn("File not found on local disk or S3 for filename={}", cleanFilename);
        return null;
    }
}
