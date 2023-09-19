package com.example.javas3.uploadclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UploadClient {

    private final RestTemplate rest;
    private final String serverUrl;

    public UploadClient(@Autowired RestTemplate restTemplate, @Value("${upload-server.url}") String serverUrl) {
        this.rest = restTemplate;
        this.serverUrl = serverUrl;
    }

    public static final String UPLOAD_API_PREFIX = "/upload";

    public ResponseEntity<List<String>> upload(List<MultipartFile> photos) {
        List<byte[]> photoBytes = new ArrayList<>();
        for (MultipartFile photo : photos) {
            try {
                photoBytes.add(photo.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<byte[]>> requestEntity = new HttpEntity<>(photoBytes, headers);

        try {
            ResponseEntity<List<String>> response = rest.exchange(serverUrl + UPLOAD_API_PREFIX,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<>() {
                    });

            if (response.getStatusCode().is2xxSuccessful()) {
                return response;
            }

            ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

            if (response.hasBody()) {
                return responseBuilder.body(response.getBody());
            }

            return responseBuilder.build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }
}
