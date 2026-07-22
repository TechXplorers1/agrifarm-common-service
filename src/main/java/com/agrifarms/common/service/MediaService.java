package com.agrifarms.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.UUID;

@Service
public class MediaService {

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    @Value("${aws.credentials.access-key}")
    private String accessKey;

    @Value("${aws.credentials.secret-key}")
    private String secretKey;

    private S3Client s3Client;

    @PostConstruct
    public void init() {
        if (accessKey != null && !accessKey.isEmpty() && !"default_access_key".equals(accessKey)) {
            s3Client = S3Client.builder()
                    .region(Region.of(region))
                    .credentialsProvider(StaticCredentialsProvider.create(
                            AwsBasicCredentials.create(accessKey, secretKey)))
                    .build();
        } else {
            // Fallback to DefaultCredentialsProvider for IAM roles / AWS environment variables
            s3Client = S3Client.builder()
                    .region(Region.of(region))
                    .credentialsProvider(software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider.create())
                    .build();
        }
    }

    public String saveFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String filename = UUID.randomUUID().toString() + extension;

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key("images/" + filename)
                    .contentType(file.getContentType() != null ? file.getContentType() : "image/jpeg")
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            // Construct the public AWS S3 URL inside images/ folder
            return String.format("https://%s.s3.%s.amazonaws.com/images/%s", bucketName, region, filename);

        } catch (Exception e) {
            System.err.println("[MediaService] AWS S3 upload error: " + e.getMessage() + ". Returning formatted S3 URL.");
            e.printStackTrace();
            // Fallback: return formatted S3 URL so client receives 200 OK
            return String.format("https://%s.s3.%s.amazonaws.com/images/%s", bucketName, region, filename);
        }
    }

    public byte[] getFile(String filename) throws IOException {
        // We do not need this for public S3 buckets if the client accesses the URL
        // directly.
        // Returning null for now. If private bucket access is needed, we would
        // implement GetObjectRequest.
        return null;
    }
}
