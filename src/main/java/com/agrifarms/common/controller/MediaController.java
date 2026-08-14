package com.agrifarms.common.controller;

import com.agrifarms.common.service.MediaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/media")
@CrossOrigin(origins = "*")
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // saveFile returns the full S3 URL
            String fileUrl = mediaService.saveFile(file);

            Map<String, String> response = new HashMap<>();

            // Extract filename from S3 URL
            String filename = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            String downloadUrl = "/api/media/download/" + filename;

            response.put("url", downloadUrl);
            response.put("filename", filename);
            response.put("s3Url", fileUrl);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("[MediaController] Error during file upload: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // @GetMapping("/download/{filename}")
    // public ResponseEntity<?> getFile(@PathVariable String filename) {
    //     try {
    //         // Build the direct S3 public URL and redirect to it
    //         // This avoids proxying bytes through the backend and doesn't require AWS credentials in dev
    //         String s3Url = String.format("https://%s.s3.%s.amazonaws.com/images/%s",
    //                 mediaService.getBucketName(), mediaService.getRegion(), filename);
    //         return ResponseEntity.status(302)
    //                 .header("Location", s3Url)
    //                 .build();
    //     } catch (Exception e) {
    //         System.err.println("[MediaController] Error building redirect URL: " + e.getMessage());
    //         return ResponseEntity.notFound().build();
    //     }
    // }
    @GetMapping("/download/{filename}")
    public ResponseEntity<?> getFile(@PathVariable String filename) {
        try {
            byte[] fileBytes = mediaService.getFile(filename);
            if (fileBytes == null || fileBytes.length == 0) {
                return ResponseEntity.notFound().build();
            }

            String contentType = "application/octet-stream";
            String lower = filename.toLowerCase();
            if (lower.endsWith(".png")) {
                contentType = "image/png";
            } else if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) {
                contentType = "image/jpeg";
            } else if (lower.endsWith(".webp")) {
                contentType = "image/webp";
            }
            return ResponseEntity.ok()
                    .header("Content-Type", contentType)
                    .header("Cache-Control", "public, max-age=86400")
                    .body(fileBytes);
        } catch (Exception e) {
            System.err.println("[MediaController] Error fetching file from S3: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
