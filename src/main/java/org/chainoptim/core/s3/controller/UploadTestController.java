package org.chainoptim.core.s3.controller;

import org.chainoptim.core.s3.model.S3FileType;
import org.chainoptim.core.s3.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/upload-to-s3-test")
public class UploadTestController {

    private S3Service s3Service;

    @Autowired
    public UploadTestController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file) {
        File fileObj = convertMultiPartToFile(file);
        String key = "test/" + file.getOriginalFilename();
        s3Service.uploadFile("chainoptim-productionhistory", key, fileObj, S3FileType.TEXT);
        fileObj.delete();
        return ResponseEntity.ok("File uploaded successfully");
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String key) {
        File file = s3Service.downloadFile("chainoptim-productionhistory", key, "C:/Users/tudor/Desktop/awsdownload");
        if (file != null) {
            Path path = Paths.get(file.getAbsolutePath());
            ByteArrayResource resource;
            try {
                resource = new ByteArrayResource(Files.readAllBytes(path));
            } catch (IOException e) {
                return ResponseEntity.internalServerError().body(null);
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .body(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam String key) {
        s3Service.deleteFile("chainoptim-productionhistory", key);
        return ResponseEntity.ok("File deleted successfully");
    }

    private File convertMultiPartToFile(MultipartFile file) {
        if (file == null || file.getOriginalFilename() == null) {
            return null;
        }
        File convertedFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertedFile;
    }
}
