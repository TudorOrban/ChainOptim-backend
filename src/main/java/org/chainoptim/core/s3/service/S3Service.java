package org.chainoptim.core.s3.service;

import java.io.File;

public interface S3Service {

    void uploadFile(String key, File file);
}
