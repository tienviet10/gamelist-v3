package com.gamelist.game_service.clients.user;

import java.util.Map;
import org.springframework.http.HttpStatus;

public record HttpResponseModel(
        String timeStamp,
        int statusCode,
        HttpStatus status,
        String message,
        String developerMessage,
        String path,
        String requestMethod,
        Map<String, Object> data) {}
