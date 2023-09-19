package com.example.javas3.uploadserver.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.javas3.uploadserver.config.ObjectStorageConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    private final ObjectStorageConfig objectStorageConfig;

    @Override
    public List<String> uploadPhoto(List<byte[]> photos) {
        List<String> urls = new ArrayList<>();

        final String bucketName = objectStorageConfig.getBucketName();
        final String accessKeyId = objectStorageConfig.getAccessKeyId();
        final String secretAccessKey = objectStorageConfig.getSecretAccessKey();
        final AmazonS3 s3Client;

        try {
            // Создание клиента AmazonS3 с подключением к Object Storage
            s3Client = AmazonS3ClientBuilder.standard()
                    .withEndpointConfiguration(
                            new com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration(
                                    "https://storage.yandexcloud.net",
                                    "ru-central1"
                            )
                    )
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKeyId, secretAccessKey)))
                    .build();
        } catch (SdkClientException e) {
            log.error("Error creating client for Object Storage via AWS SDK. Reason: {}", e.getMessage());
            throw new SdkClientException(e.getMessage());
        }

        try {
            for (byte[] photoBytes : photos) {
                String fileName = generateUniqueName();
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(photoBytes.length);

                // Загружаем файл в Yandex Object Storage
                ByteArrayInputStream inputStream = new ByteArrayInputStream(photoBytes);
                s3Client.putObject(bucketName, fileName, inputStream, metadata);
                log.info("Upload Service. Added file: " + fileName + " to bucket: " + bucketName);

                // Получаем ссылку на загруженный файл
                String url = s3Client.getUrl(bucketName, fileName).toExternalForm();

                urls.add(url);
            }
        } catch (AmazonS3Exception e) {
            log.error("Error uploading photos to Object Storage. Reason: {}", e.getMessage());
            throw new AmazonS3Exception(e.getMessage());
        }
        return urls;
    }

    private String generateUniqueName() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
