package com.example.javas3.uploadserver.controller;

import com.example.javas3.uploadserver.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UploadController {

    private final UploadService uploadService;

    @PostMapping("/upload")
    public List<String> upload(@RequestBody List<MultipartFile> photos) {
        return uploadService.uploadPhoto(photos);
    }
}
