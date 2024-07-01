package com.gamelist.social_service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "social")
public record ApplicationProperties(String gameServiceUrl, String goServiceUrl, String userServiceUrl) {}
