package com.gamelist.game_service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "game")
public record ApplicationProperties(String userServiceUrl) {}
