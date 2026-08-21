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
import software.amazon.awssdk.services.s3.model.S3Exception;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.UUID;

@Service
public class MediaService {

    private static final Logger log = LoggerFactory.getLogger(MediaService.class);

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    private S3Client s3Client;

    @PostConstruct
    public void init() {
        log.info("Initializing S3 client for region='{}' bucket='{}'", region, bucketName);
        s3Client = S3Client.builder()
                .region(Region.of(region))
                .build();
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getRegion() {
        return region;
    }

    public String saveFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String filename = UUID.randomUUID().toString() + extension;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key("images/" + filename)
                .contentType(file.getContentType() != null ? file.getContentType() : "image/jpeg")
                .build();

        log.info("Uploading file to S3: bucket='{}' key='{}' size={} contentType={}",
                bucketName, putObjectRequest.key(), file.getSize(), putObjectRequest.contentType());

        try {
            var response = s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            log.info("S3 putObject completed: status={}, requestId={}",
                    response.sdkHttpResponse().statusCode(), response.responseMetadata());

            // Construct the public AWS S3 URL inside images/ folder
            String publicUrl = String.format("https://%s.s3.%s.amazonaws.com/images/%s", bucketName, region, filename);
            log.info("File uploaded successfully. publicUrl={}", publicUrl);
            return publicUrl;

        } catch (S3Exception se) {
            log.error("S3Exception during upload: statusCode={}, awsErrorCode={}, message={}",
                    se.statusCode(), se.awsErrorDetails() != null ? se.awsErrorDetails().errorCode() : "-", se.getMessage(), se);
            throw new IOException("Failed to upload file to S3: " + se.getMessage(), se);
        } catch (Exception e) {
            log.error("Unexpected exception during S3 upload: {}", e.getMessage(), e);
            throw new IOException("Failed to upload file to S3: " + e.getMessage(), e);
        }
    }

    public byte[] getFile(String filename) throws IOException {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key("images/" + filename)
                .build();

        log.info("Fetching file from S3: bucket='{}' key='{}'", bucketName, getObjectRequest.key());

        try {
            return s3Client.getObjectAsBytes(getObjectRequest).asByteArray();
        } catch (S3Exception se) {
            log.error("S3Exception during getObject: statusCode={}, awsErrorCode={}, message={}",
                    se.statusCode(), se.awsErrorDetails() != null ? se.awsErrorDetails().errorCode() : "-", se.getMessage(), se);
            throw new IOException("Failed to fetch file from S3: " + se.getMessage(), se);
        } catch (Exception e) {
            log.error("Unexpected exception during S3 getObject: {}", e.getMessage(), e);
            throw new IOException("Failed to fetch file from S3: " + e.getMessage(), e);
        }
    }
}
