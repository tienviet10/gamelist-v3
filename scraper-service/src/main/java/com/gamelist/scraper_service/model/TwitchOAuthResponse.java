package com.gamelist.scraper_service.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class TwitchOAuthResponse {

    private String access_token;
    private int expires_in;
    private String token_type;
}
