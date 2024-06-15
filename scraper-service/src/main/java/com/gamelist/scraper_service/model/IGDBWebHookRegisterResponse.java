package com.gamelist.scraper_service.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.time.*;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class IGDBWebHookRegisterResponse {

    private String id;
    private String url;
    private int category;
    private int sub_category;
    private boolean active;
    private String api_key;
    private String secret;
    private LocalDate created_at;
    private LocalDate updated_at;
}
