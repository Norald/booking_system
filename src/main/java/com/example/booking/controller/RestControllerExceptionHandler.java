package com.example.booking.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

@RestControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<?> handleAll(Exception ex) {
        final Optional<ResponseStatus> annotation = Optional.ofNullable(AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class));
        final HttpStatus status = annotation.map(ResponseStatus::value).orElse(HttpStatus.INTERNAL_SERVER_ERROR);
        return
                ResponseEntity
                        .status(status)
                        .body(ApiErrorResponse.builder().status(status.value()).errorMessage(ex.getMessage()).build());
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ApiErrorResponse {
        private Object errors;
        private String errorMessage;
        private int status;
        @Builder.Default
        private long timestamp = System.currentTimeMillis();
    }
}
