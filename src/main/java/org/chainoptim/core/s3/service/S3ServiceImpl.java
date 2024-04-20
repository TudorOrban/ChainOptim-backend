package org.chainoptim.core.s3.service;

import org.chainoptim.core.s3.model.S3FileType;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 s3client;

    @Autowired
    public S3ServiceImpl(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    public File downloadFile(String bucketName, String key, String destinationPath) {
        S3Object object = s3client.getObject(new GetObjectRequest(bucketName, key));
        S3ObjectInputStream inputStream = object.getObjectContent();
        File file = new File(destinationPath);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            byte[] readBuf = new byte[1024];
            int readLen;
            while ((readLen = inputStream.read(readBuf)) > 0) {
                outputStream.write(readBuf, 0, readLen);
            }
        } catch (Exception e) {
            System.err.println("Error while downloading file from S3: " + e.getMessage());
        }

        return file;
    }

    public byte[] fetchFileContent(String bucketName, String key) {
        try (S3ObjectInputStream inputStream = s3client.getObject(new GetObjectRequest(bucketName, key)).getObjectContent()) {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            System.err.println("Error while fetching file content from S3: " + e.getMessage());
            return null;
        }
    }

    public void uploadFile(String bucketName, String key, File file, S3FileType fileType) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(fileType.getContentType());

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            PutObjectRequest request = new PutObjectRequest(bucketName, key, fileInputStream, metadata);
            s3client.putObject(request);
        } catch(FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
        }
    }

    public void updateFile(String bucketName, String key, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, key, file));
    }

    public void deleteFile(String bucketName, String key) {
        s3client.deleteObject(bucketName, key);
    }
}
