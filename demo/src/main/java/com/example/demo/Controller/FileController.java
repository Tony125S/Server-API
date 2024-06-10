package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class FileController {

    @Value("${upload.dir}")
    private String uploadDir;

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("yamlFile") MultipartFile yamlFile,
                                   @RequestParam("csvFile") MultipartFile csvFile) {
        if (yamlFile.isEmpty() && csvFile.isEmpty()) {
            return "Both files are empty";
        }

        StringBuilder responseMessage = new StringBuilder();

        // Ensure the upload directory exists
        File uploadDirectory = new File(uploadDir);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }

        // Save YAML file
        if (!yamlFile.isEmpty()) {
            try {
                File yamlDest = new File(uploadDir, yamlFile.getOriginalFilename());
                yamlFile.transferTo(yamlDest);
                responseMessage.append("YAML file uploaded successfully: ").append(yamlDest.getAbsolutePath()).append("\n");
            } catch (IOException e) {
                e.printStackTrace();
                responseMessage.append("Failed to upload YAML file: ").append(e.getMessage()).append("\n");
            }
        } else {
            responseMessage.append("YAML file is empty\n");
        }

        // Save CSV file
        if (!csvFile.isEmpty()) {
            try {
                File csvDest = new File(uploadDir, csvFile.getOriginalFilename());
                csvFile.transferTo(csvDest);
                responseMessage.append("CSV file uploaded successfully: ").append(csvDest.getAbsolutePath()).append("\n");
            } catch (IOException e) {
                e.printStackTrace();
                responseMessage.append("Failed to upload CSV file: ").append(e.getMessage()).append("\n");
            }
        } else {
            responseMessage.append("CSV file is empty\n");
        }

        return responseMessage.toString();
    }
}
