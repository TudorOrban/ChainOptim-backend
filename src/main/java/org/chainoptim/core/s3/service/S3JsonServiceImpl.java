package org.chainoptim.core.s3.service;

import org.chainoptim.core.s3.model.S3FileType;

import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;

@Service
public class S3JsonServiceImpl implements S3JsonService {

    private final S3Service s3Service;
    private final ObjectMapper objectMapper;

    @Autowired
    public S3JsonServiceImpl(S3Service s3Service, ObjectMapper objectMapper) {
        this.s3Service = s3Service;
        this.objectMapper = objectMapper;
    }

    public <T> T downloadObject(String bucketName, String key, Class<T> valueType) {
        byte[] jsonData = s3Service.fetchFileContent(bucketName, key);
        if (jsonData == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found in S3");
        }
        try {
            return objectMapper.readValue(jsonData, valueType);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deserializing JSON data", e);
        }
    }

    public void uploadObject(String bucketName, String key, Object data) {
        try {
            byte[] jsonData = objectMapper.writeValueAsBytes(data);
            InputStream inputStream = new ByteArrayInputStream(jsonData);
            File tempFile = createTempFile(inputStream, key);
            try {
                s3Service.uploadFile(bucketName, key, tempFile, S3FileType.JSON);
            } finally {
                tempFile.delete();
            }
        } catch (JsonProcessingException e) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error serializing data to JSON", e);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error handling temp file", e);
        }
    }

    public void deleteObject(String bucketName, String key) {
        s3Service.deleteFile(bucketName, key);
    }

    private File createTempFile(InputStream data, String key) throws IOException {
        File tempFile = File.createTempFile(key, ".tmp");
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(data, out);
        }
        return tempFile;
    }

}
