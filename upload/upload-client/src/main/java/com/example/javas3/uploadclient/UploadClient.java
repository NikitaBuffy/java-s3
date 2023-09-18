package com.example.javas3.uploadclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UploadClient {

    private final RestTemplate rest;
    private final String serverUrl;

    public UploadClient(@Autowired RestTemplate restTemplate, @Value("${upload-server.url}") String serverUrl) {
        this.rest = restTemplate;
        this.serverUrl = serverUrl;
    }

    public static final String UPLOAD_API_PREFIX = "/upload";
}
