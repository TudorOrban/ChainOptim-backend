package org.chainoptim.core.s3.controller;

import org.chainoptim.core.s3.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
        s3Service.uploadFile(key, fileObj);
        fileObj.delete();
        return ResponseEntity.ok("File uploaded successfully");
    }

    private File convertMultiPartToFile(MultipartFile file) {
        if (file == null || file.getOriginalFilename() == null) {
            return null;
        }
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertedFile;
    }
}
