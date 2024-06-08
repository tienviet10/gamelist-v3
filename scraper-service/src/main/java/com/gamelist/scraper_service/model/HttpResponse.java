package com.gamelist.scraper_service.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.*;
import org.springframework.http.*;

import java.util.*;

@Getter
@Setter
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HttpResponse {
    protected String timeStamp;
    protected int statusCode;
    protected HttpStatus status;
    protected String message;
    protected String developerMessage;
    protected String path;
    protected String requestMethod;
    protected Map<?, ?> data;
}
