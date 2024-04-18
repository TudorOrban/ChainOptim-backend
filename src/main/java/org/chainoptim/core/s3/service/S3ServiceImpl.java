package org.chainoptim.core.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 s3client;
    private final String bucketName = "chainoptim-productionhistory";

    @Autowired
    public S3ServiceImpl(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    public void uploadFile(String key, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, key, file));
    }
}
