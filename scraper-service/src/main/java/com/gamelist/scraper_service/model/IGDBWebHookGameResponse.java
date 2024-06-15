package com.gamelist.scraper_service.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class IGDBWebHookGameResponse {

    private String id;
}
