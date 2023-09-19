package com.example.javas3.uploadserver.controller;

import com.example.javas3.uploadserver.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UploadController {

    private final UploadService uploadService;


    /**
     * Загрузка фотографий на облачное хранилище S3
     * @param photos Список фотографий в байтах
     * @return Список ссылок на загруженные файлы
     */
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public List<String> upload(@RequestBody List<byte[]> photos) {
        return uploadService.uploadPhoto(photos);
    }
}
