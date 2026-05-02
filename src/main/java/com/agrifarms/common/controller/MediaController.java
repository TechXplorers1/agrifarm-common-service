package com.agrifarms.common.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/media")
@CrossOrigin(origins = "*")
public class MediaController {

    private final String uploadDir = "uploads/";

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);
            Files.write(path, file.getBytes());

            Map<String, String> response = new HashMap<>();
            // Return a relative path or a full URL
            // The frontend seems to expect a JSON with a 'url' key
            response.put("url", fileName);
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<byte[]> viewFile(@PathVariable String fileName) {
        try {
            Path path = Paths.get(uploadDir + fileName);
            byte[] image = Files.readAllBytes(path);
            return ResponseEntity.ok(image);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
