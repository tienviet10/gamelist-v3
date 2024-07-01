package com.gamelist.game_service.clients.user;

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
public class UserServiceClient {
    private static final Logger log = LoggerFactory.getLogger(UserServiceClient.class);
    private final RestClient restClient;

    @CircuitBreaker(name = "user-service")
    @Retry(name = "user-service", fallbackMethod = "getUserByCodeFallback")
    public Optional<HttpResponseModel> getUserInfoById(String authorizationHeader) {
        var userById = Objects.requireNonNull(restClient
                .get()
                .uri("/api/v1/user/listorder")
                .header("Authorization", authorizationHeader)
                .retrieve()
                .toEntity(HttpResponseModel.class)
                .getBody());
        return Optional.of(userById);
    }

    Optional<HttpResponseModel> getUserByCodeFallback(Throwable t) {
        log.error("getUserByCodeFallback called with userId: ", t);
        return Optional.empty();
    }
}
