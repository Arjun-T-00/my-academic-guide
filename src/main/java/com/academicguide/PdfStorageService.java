package com.academicguide;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class PdfStorageService {

    private final Path uploadDirectory =
            Paths.get("uploads").toAbsolutePath().normalize();

    // Save uploaded PDF
    public String savePdf(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IOException("Please select a PDF file.");
        }

        String originalName = file.getOriginalFilename();

        if (originalName == null ||
                !originalName.toLowerCase().endsWith(".pdf")) {
            throw new IOException("Only PDF files are allowed.");
        }

        // Verify PDF file signature
        byte[] signature = new byte[5];

        try (InputStream input = file.getInputStream()) {
            if (input.read(signature) != 5 ||
                    !new String(signature).equals("%PDF-")) {
                throw new IOException("Invalid PDF file.");
            }
        }

        // Create uploads folder if it doesn't exist
        Files.createDirectories(uploadDirectory);

        // Generate a unique file name
        String fileName = UUID.randomUUID() + ".pdf";

        // Save PDF
        Path destination = uploadDirectory.resolve(fileName);

        Files.copy(file.getInputStream(), destination);

        return fileName;
    }
}