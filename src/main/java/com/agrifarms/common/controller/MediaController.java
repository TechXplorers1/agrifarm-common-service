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
            String filename = mediaService.saveFile(file);
            // In a real environment, you'd use a proper base URL. 
            // For this local setup, we'll return a path that the Flutter app can use via the download endpoint.
            String fileUrl = "/api/media/download/" + filename;
            
            Map<String, String> response = new HashMap<>();
            response.put("url", fileUrl);
            response.put("filename", filename);
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
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
            
            String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
            MediaType mediaType = MediaType.IMAGE_JPEG;
            if (extension.equals("png")) {
                mediaType = MediaType.IMAGE_PNG;
            } else if (extension.equals("gif")) {
                mediaType = MediaType.IMAGE_GIF;
            }
            
            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(data);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
