package com.gamelist.social_service.clients.user;

import com.gamelist.social_service.clients.HttpResponseGeneralModel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class UserServiceClient {
    private static final Logger log = LoggerFactory.getLogger(UserServiceClient.class);

    @Qualifier("restUserServiceClient") private final RestClient restUserServiceClient;

    public UserServiceClient(@Qualifier("restUserServiceClient") RestClient restUserServiceClient) {
        this.restUserServiceClient = restUserServiceClient;
    }

    @CircuitBreaker(name = "user-service")
    @Retry(name = "user-service", fallbackMethod = "checkedUserExistsFallback")
    public Optional<HttpResponseGeneralModel<Boolean>> checkedIfUserExists(String authorizationHeader) {
        HttpResponseGeneralModel<Boolean> userExists = Objects.requireNonNull(restUserServiceClient
                .get()
                .uri("/api/v1/user/user-exist")
                .header("Authorization", authorizationHeader)
                .retrieve()
                .toEntity(HttpResponseGeneralModel.class)
                .getBody());
        return Optional.of(userExists);
    }

    Optional<HttpResponseGeneralModel> checkedUserExistsFallback(Throwable t) {
        log.error("getUserByCodeFallback called with userId: ", t);
        return Optional.empty();
    }
}
