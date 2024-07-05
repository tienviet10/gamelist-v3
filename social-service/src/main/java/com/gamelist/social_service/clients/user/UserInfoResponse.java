package com.gamelist.social_service.clients.user;

import com.gamelist.social_service.clients.GeneralRestClientResponse;
import org.springframework.http.HttpStatus;

public class UserInfoResponse extends GeneralRestClientResponse<UserDTO> {
    public UserInfoResponse(
            String timeStamp,
            int statusCode,
            HttpStatus status,
            String message,
            String developerMessage,
            String path,
            String requestMethod,
            UserDTO data) {
        super(timeStamp, statusCode, status, message, developerMessage, path, requestMethod, data);
    }
}
