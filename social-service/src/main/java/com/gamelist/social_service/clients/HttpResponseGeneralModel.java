package com.gamelist.social_service.clients;

import org.springframework.http.HttpStatus;

public record HttpResponseGeneralModel<T>(
        String timeStamp,
        int statusCode,
        HttpStatus status,
        String message,
        String developerMessage,
        String path,
        String requestMethod,
        T data) {}
