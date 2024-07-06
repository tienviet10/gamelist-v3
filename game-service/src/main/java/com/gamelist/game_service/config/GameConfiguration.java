package com.gamelist.game_service.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "settings")
public class GameConfiguration {

    private String access_token;
    private String clientId;

}
