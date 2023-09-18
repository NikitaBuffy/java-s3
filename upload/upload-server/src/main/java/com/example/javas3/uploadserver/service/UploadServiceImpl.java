package com.example.javas3.uploadserver.service;

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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    private final ObjectStorageConfig objectStorageConfig;

    @Override
    public List<String> uploadPhoto(List<MultipartFile> photos) {
        List<String> urls = new ArrayList<>();

        String bucketName = objectStorageConfig.getBucketName();
        String accessKeyId = objectStorageConfig.getAccessKeyId();
        String secretAccessKey = objectStorageConfig.getSecretAccessKey();

        // Создание клиента AmazonS3 с подключением к Object Storage
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(
                        new com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration(
                                "https://storage.yandexcloud.net",
                                "ru-central1"
                        )
                )
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKeyId, secretAccessKey)))
                .build();

        try {
            for (MultipartFile file : photos) {
                String fileName = file.getOriginalFilename();
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(file.getSize());

                // Загружаем файл в Yandex Object Storage
                s3Client.putObject(bucketName, fileName, file.getInputStream(), metadata);
                log.info("Upload Service. Added file: " + fileName + " to bucket: " + bucketName);

                // Получаем ссылку на загруженный файл
                String url = s3Client.getUrl(bucketName, fileName).toExternalForm();

                urls.add(url);
            }
        } catch (IOException | AmazonS3Exception e) {
            e.printStackTrace();
        }

        return urls;
    }
}
