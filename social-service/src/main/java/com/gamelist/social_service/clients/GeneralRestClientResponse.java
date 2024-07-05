package com.gamelist.social_service.clients;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class GeneralRestClientResponse<T> {
    private String timeStamp;
    private int statusCode;
    private HttpStatus status;
    private String message;
    private String developerMessage;
    private String path;
    private String requestMethod;
    private T data;
}
