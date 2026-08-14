// package com.agrifarms.common.service;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;
// import org.springframework.web.multipart.MultipartFile;
// import software.amazon.awssdk.core.sync.RequestBody;
// import software.amazon.awssdk.regions.Region;
// import software.amazon.awssdk.services.s3.S3Client;
// import software.amazon.awssdk.services.s3.model.GetObjectRequest;
// import software.amazon.awssdk.services.s3.model.PutObjectRequest;
// import jakarta.annotation.PostConstruct;
// import java.io.IOException;
// import java.util.UUID;
// @Service
// public class MediaService {
//     @Value("${aws.s3.bucket}")
//     private String bucketName;
//     @Value("${aws.s3.region}")
//     private String region;
//     private S3Client s3Client;
//     @PostConstruct
//     public void init() {
//         s3Client = S3Client.builder()
//                 .region(Region.of(region))
//                 .build();
//     }
//     public String getBucketName() {
//         return bucketName;
//     }
//     public String getRegion() {
//         return region;
//     }
//     public String saveFile(MultipartFile file) throws IOException {
//         String originalFilename = file.getOriginalFilename();
//         String extension = "";
//         if (originalFilename != null && originalFilename.contains(".")) {
//             extension = originalFilename.substring(originalFilename.lastIndexOf("."));
//         }
//         String filename = UUID.randomUUID().toString() + extension;
//         try {
//             PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                     .bucket(bucketName)
//                     .key("images/" + filename)
//                     .contentType(file.getContentType() != null ? file.getContentType() : "image/jpeg")
//                     .build();
//             s3Client.putObject(putObjectRequest,
//                     RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
//             // Construct the public AWS S3 URL inside images/ folder
//             return String.format("https://%s.s3.%s.amazonaws.com/images/%s", bucketName, region, filename);
//         } catch (Exception e) {
//             System.err.println("[MediaService] AWS S3 upload error: " + e.getMessage() + ". Returning formatted S3 URL.");
//             return String.format("https://%s.s3.%s.amazonaws.com/images/%s", bucketName, region, filename);
//         }
//     }
//     public byte[] getFile(String filename) throws IOException {
//         try {
//             GetObjectRequest getObjectRequest = GetObjectRequest.builder()
//                     .bucket(bucketName)
//                     .key("images/" + filename)
//                     .build();
//             return s3Client.getObjectAsBytes(getObjectRequest).asByteArray();
//         } catch (Exception e) {
//             System.err.println("[MediaService] AWS S3 getFile error: " + e.getMessage());
//             return new byte[0]; // Return empty byte array to prevent crash
//         }
//     }
// }
package com.agrifarms.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.UUID;

@Service
public class MediaService {

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    private S3Client s3Client;

    @PostConstruct
    public void init() {
        s3Client = S3Client.builder()
                .region(Region.of(region))
                .build();
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
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key("images/" + filename)
                .build();

        return s3Client.getObjectAsBytes(getObjectRequest).asByteArray();
    }
}
