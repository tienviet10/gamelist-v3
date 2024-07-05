package com.gamelist.social_service.clients.user;

import com.gamelist.social_service.clients.GeneralRestClientResponse;
import org.springframework.http.HttpStatus;

public class UserExistResponse extends GeneralRestClientResponse<Boolean> {
    public UserExistResponse(
            String timeStamp,
            int statusCode,
            HttpStatus status,
            String message,
            String developerMessage,
            String path,
            String requestMethod,
            Boolean data) {
        super(timeStamp, statusCode, status, message, developerMessage, path, requestMethod, data);
    }
}
