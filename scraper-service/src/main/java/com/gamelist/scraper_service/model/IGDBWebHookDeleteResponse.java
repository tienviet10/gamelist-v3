package com.gamelist.scraper_service.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.time.*;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class IGDBWebHookDeleteResponse {

    private String id;
}
