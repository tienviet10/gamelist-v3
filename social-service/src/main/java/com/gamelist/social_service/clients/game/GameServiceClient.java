package com.gamelist.social_service.clients.game;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class GameServiceClient {
    private static final Logger log = LoggerFactory.getLogger(GameServiceClient.class);
    private final RestClient restClient;

    @CircuitBreaker(name = "game-service")
    @Retry(name = "game-service", fallbackMethod = "getGameByCodeFallback")
    public Optional<HttpResponseModel> getGameById(Long gameId) {
        log.info("getGameById called in GameServiceClient with gameId: {}", gameId);
        var gameByCode = Objects.requireNonNull(restClient
                .get()
                .uri("/games/{gameId}", gameId)
                .retrieve()
                .toEntity(HttpResponseModel.class)
                .getBody());
        return Optional.of(gameByCode);
    }

    Optional<HttpResponseModel> getGameByCodeFallback(Throwable t) {
        log.error("getGameByCodeFallback called with gameId: ", t);
        return Optional.empty();
    }
}
