package com.example.javas3.uploadserver.service;

import java.util.List;

public interface UploadService {

    List<String> uploadPhoto(List<byte[]> photos);
}
