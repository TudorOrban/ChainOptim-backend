package org.chainoptim.core.s3.service;


public interface S3JsonService {

    <T> T downloadObject(String bucketName, String key, Class<T> valueType);
    void uploadObject(String bucketName, String key, Object data);
    void deleteObject(String bucketName, String key);
}
