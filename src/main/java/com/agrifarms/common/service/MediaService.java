package com.agrifarms.common.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class MediaService {

    private final String uploadDir = "agrifarm-uploads";

    public MediaService() {
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
    }

    public String saveFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        
        String filename = UUID.randomUUID().toString() + extension;
        Path path = Paths.get(uploadDir, filename);
        Files.copy(file.getInputStream(), path);
        
        return filename;
    }

    public byte[] getFile(String filename) throws IOException {
        Path path = Paths.get(uploadDir, filename);
        if (Files.exists(path)) {
            return Files.readAllBytes(path);
        }
        return null;
    }
}
