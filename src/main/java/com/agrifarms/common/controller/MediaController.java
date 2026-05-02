package com.agrifarms.common.controller;

<<<<<<< HEAD
=======
import com.agrifarms.common.service.MediaService;
import org.springframework.http.MediaType;
>>>>>>> 6a3fc0c1aeaf20611009613722e2f788ea1da2fb
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

<<<<<<< HEAD
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
=======
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
>>>>>>> 6a3fc0c1aeaf20611009613722e2f788ea1da2fb

@RestController
@RequestMapping("/api/media")
@CrossOrigin(origins = "*")
public class MediaController {

<<<<<<< HEAD
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
=======
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
>>>>>>> 6a3fc0c1aeaf20611009613722e2f788ea1da2fb
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

<<<<<<< HEAD
    @GetMapping("/view/{fileName}")
    public ResponseEntity<byte[]> viewFile(@PathVariable String fileName) {
        try {
            Path path = Paths.get(uploadDir + fileName);
            byte[] image = Files.readAllBytes(path);
            return ResponseEntity.ok(image);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
=======
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
>>>>>>> 6a3fc0c1aeaf20611009613722e2f788ea1da2fb
        }
    }
}
