package com.gamelist;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.context.properties.*;

@SpringBootApplication
@ConfigurationPropertiesScan
public class IGDBWebhookApplication {

    public static void main(String[] args) {
        SpringApplication.run(IGDBWebhookApplication.class, args);
    }
}
