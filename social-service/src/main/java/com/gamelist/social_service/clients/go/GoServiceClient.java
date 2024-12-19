package com.gamelist.social_service.clients.go;

import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

// TODO: This is for a TEST.
@Component
public class GoServiceClient {
    private static final Logger log = LoggerFactory.getLogger(GoServiceClient.class);

    @Qualifier("restGoClient") private final RestClient restGOClient;

    public GoServiceClient(@Qualifier("restGoClient") RestClient restGOClient) {
        this.restGOClient = restGOClient;
    }

    public Optional<String> getGo() {
        var gameByCode = Objects.requireNonNull(restGOClient
                .get()
                .uri("/ping")
                .retrieve()
                .toEntity(String.class)
                .getBody());
        return Optional.of(gameByCode);
    }

    Optional<String> getGameByCodeFallback(Throwable t) {
        log.error("getGameByCodeFallback called with gameId: ", t);
        return Optional.empty();
    }
}
