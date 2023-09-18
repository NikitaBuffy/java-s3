package com.example.javas3.uploadserver.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadService {

    List<String> uploadPhoto(List<MultipartFile> photos);
}
