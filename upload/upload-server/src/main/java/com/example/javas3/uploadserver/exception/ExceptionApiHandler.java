package com.example.javas3.uploadserver.exception;

import com.amazonaws.SdkBaseException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.example.javas3.uploadserver.exception.model.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;

@RestControllerAdvice
@Slf4j
public class ExceptionApiHandler {

    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler({AmazonS3Exception.class, SdkClientException.class})
    public ResponseEntity<ApiError> amazonS3Exception(SdkBaseException exception) {
        log.debug(exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError("INTERNAL_SERVER_ERROR", "Something has gone wrong with AWS SDK.",
                        exception.getMessage(), LocalDateTime.now().format(DATE_FORMAT)));
    }

    @ExceptionHandler({InterruptedException.class, ExecutionException.class})
    public ResponseEntity<ApiError> runtimeException(RuntimeException exception) {
        log.debug(exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError("INTERNAL_SERVER_ERROR", "One of the thread ended with exception.",
                        exception.getMessage(), LocalDateTime.now().format(DATE_FORMAT)));
    }
}
