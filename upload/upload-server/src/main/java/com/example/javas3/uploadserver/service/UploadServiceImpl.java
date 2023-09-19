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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@Slf4j
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    private final ObjectStorageConfig objectStorageConfig;

    /**
     * Загрузка фотографий на облачное хранилище S3
     * @param photos Список фотографий в байтах
     * @return Список ссылок на загруженные файлы
     * @throws SdkClientException Если не удается создать клиент S3
     * @throws AmazonS3Exception Если возникнет проблема при обращении к хранилищу S3
     * @throws RuntimeException Если во время исполнения одного из потоков произойдет ошибка
     */
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
            // Создание пула потоков
            ExecutorService executorService = Executors.newFixedThreadPool(photos.size());
            List<Future<String>> futures = new ArrayList<>();

            for (byte[] photoBytes : photos) {
                Future<String> future = executorService.submit(() -> {
                    String fileName = generateUniqueName();
                    ObjectMetadata metadata = new ObjectMetadata();
                    metadata.setContentLength(photoBytes.length);

                    // Загрузка файла в Yandex Object Storage
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(photoBytes);
                    s3Client.putObject(bucketName, fileName, inputStream, metadata);
                    log.info("Upload Service. Added file: " + fileName + " to bucket: " + bucketName);

                    // Получение ссылки на загруженный файл
                    String url = s3Client.getUrl(bucketName, fileName).toExternalForm();

                    return url;
                });

                futures.add(future);
            }

            // Ожидание завершения задач
            for (Future<String> future : futures) {
                try {
                    String url = future.get();
                    urls.add(url);
                } catch (InterruptedException | ExecutionException e) {
                    log.error("One of the thread ended with exception. Reason: {}", e.getMessage());
                    throw new RuntimeException(e);
                }
            }

            executorService.shutdown();
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
