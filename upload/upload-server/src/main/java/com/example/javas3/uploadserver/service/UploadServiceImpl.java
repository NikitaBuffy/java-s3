package com.example.javas3.uploadserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    @Override
    public List<String> uploadPhoto(List<MultipartFile> photos) {
        List<String> photoUrls = new ArrayList<>();

//        ExecutorService executor = Executors.newFixedThreadPool(photos.size());
//
//        try {
//            for (MultipartFile photo : photos) {
//
//            }
//
//        } catch (InterruptedException | ExecutionException e) {
//            // Обработка ошибок
//        } finally {
//            executor.shutdown();
//        }

        return photoUrls;
    }
}
