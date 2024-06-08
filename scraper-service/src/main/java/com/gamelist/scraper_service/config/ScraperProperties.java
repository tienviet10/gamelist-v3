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
    private String igdbClientKey;
}
