package com.gamelist.entity.webhook;

import java.time.*;
import lombok.*;

@Data
public class IGDBWebhookRegisterWebhookResponse {

    private final String id;
    private final String url;
    private final int category;
    private final int subCategory;
    private final boolean active;
    private final String apiKey;
    private final String apiSecret;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
