package com.example.javas3.uploadserver.service;

import java.util.List;

public interface UploadService {

    /**
     * @see UploadServiceImpl#uploadPhoto
     */
    List<String> uploadPhoto(List<byte[]> photos);
}
