package com.example.javas3.uploadserver.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ObjectStorageConfig {

    @Value("${BUCKET_NAME}")
    private String bucketName;

    @Value("${ACCESS_KEY_ID}")
    private String accessKeyId;

    @Value("${SECRET_KEY}")
    private String secretAccessKey;
}

