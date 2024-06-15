package com.gamelist.scraper_service.components;

import com.gamelist.scraper_service.config.*;
import org.slf4j.*;
import org.springframework.boot.context.event.*;
import org.springframework.context.*;
import org.springframework.http.*;
import org.springframework.http.client.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import java.net.*;

@Component
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(ApplicationReadyListener.class);
    private ScraperProperties scraperProperties;
    private final RestClient customClient = RestClient.builder()
            .requestFactory(new HttpComponentsClientHttpRequestFactory())
            .build();

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        log.info("Application ready");
        log.info("Properties: {}", scraperProperties);

        RestClient.ResponseSpec spec = customClient.post().uri(uriBuilder -> {
            URI uri = uriBuilder.path("https://id.twitch.tv/oauth2/token")
                    .queryParam("client_id", "")
                    .queryParam("client_secret", "")
                    .queryParam("grant_type", "client_credentials")
                    .build();

            log.info("URI: {}", uri);
            return uri;
        }).accept(MediaType.APPLICATION_JSON)
                .retrieve();

        log.info("Spec: {}", spec.toString());


    }
}