package com.agrifarms.common.controller;

import com.agrifarms.common.service.MediaService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
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

    @GetMapping("/download/{filename}")
    public ResponseEntity<byte[]> getFile(@PathVariable String filename) {
        try {
            byte[] data = mediaService.getFile(filename);
            if (data == null) {
                return ResponseEntity.notFound().build();
            }

            String extension = "";
            if (filename.contains(".")) {
                extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
            }

            MediaType mediaType = MediaType.IMAGE_JPEG;
            if ("png".equals(extension)) {
                mediaType = MediaType.IMAGE_PNG;
            } else if ("gif".equals(extension)) {
                mediaType = MediaType.IMAGE_GIF;
            } else if ("webp".equals(extension)) {
                mediaType = MediaType.parseMediaType("image/webp");
            } else if ("svg".equals(extension)) {
                mediaType = MediaType.parseMediaType("image/svg+xml");
            }

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(data);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
