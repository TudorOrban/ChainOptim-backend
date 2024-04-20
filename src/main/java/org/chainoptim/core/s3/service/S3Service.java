package org.chainoptim.core.s3.service;

import org.chainoptim.core.s3.model.S3FileType;

import java.io.File;

public interface S3Service {

    File downloadFile(String bucketName, String key, String destinationPath);
    byte[] fetchFileContent(String bucketName, String key);
    void uploadFile(String bucketName, String key, File file, S3FileType fileType);
    void updateFile(String bucketName, String key, File file);
    void deleteFile(String bucketName, String key);
}
