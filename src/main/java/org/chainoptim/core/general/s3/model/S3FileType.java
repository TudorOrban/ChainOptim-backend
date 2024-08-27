package org.chainoptim.core.general.s3.model;

import lombok.Getter;

@Getter
public enum S3FileType {
    TEXT("text/plain"),
    JPEG("image/jpeg"),
    JSON("application/json"),
    PDF("application/pdf");

    private final String contentType;

    S3FileType(String contentType) {
        this.contentType = contentType;
    }
}
