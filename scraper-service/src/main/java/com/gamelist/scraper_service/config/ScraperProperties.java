package com.gamelist.scraper_service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "secrets")
@Getter
@Setter
public class ScraperProperties {

    private String twitchLoginClientId;
    private String twitchLoginClientSecret;
    private String grant_type;

    private String igdbClientKey;
}
