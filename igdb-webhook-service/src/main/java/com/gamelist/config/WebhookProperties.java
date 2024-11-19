package com.gamelist.config;

import lombok.*;
import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;

@Configuration
@ConfigurationProperties(prefix = "webhook.properties")
@Getter
@Setter
public class WebhookProperties {
    private String secretKey;
}
