package com.gamelist.social_service.clients.user;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

// TODO: REMOVE THIS CLASS
// This class is not used in the project. Being replaced by gRPC.
@Component
public class UserServiceClient {
    private static final Logger log = LoggerFactory.getLogger(UserServiceClient.class);

    @Qualifier("restUserServiceClient") private final RestClient restUserServiceClient;

    public UserServiceClient(@Qualifier("restUserServiceClient") RestClient restUserServiceClient) {
        this.restUserServiceClient = restUserServiceClient;
    }

    @CircuitBreaker(name = "user-service")
    @Retry(name = "user-service", fallbackMethod = "checkedUserExistsFallback")
    public Optional<UserExistResponse> checkedIfUserExists(String authorizationHeader) {
        UserExistResponse userExists = Objects.requireNonNull(restUserServiceClient
                .get()
                .uri("/api/v1/user/user-exist")
                .header("Authorization", authorizationHeader)
                .retrieve()
                .toEntity(UserExistResponse.class)
                .getBody());
        return Optional.of(userExists);
    }

    @CircuitBreaker(name = "user-service")
    @Retry(name = "user-service", fallbackMethod = "getUserByCodeFallback")
    public Optional<UserInfoResponse> getUserInfoById(String authorizationHeader, String userInfoId) {
        UserInfoResponse userById = Objects.requireNonNull(restUserServiceClient
                .get()
                .uri("/api/v1/user/userinfo/{userInfoId}", userInfoId)
                .header("Authorization", authorizationHeader)
                .retrieve()
                .toEntity(UserInfoResponse.class)
                .getBody());

        return Optional.of(userById);
    }

    Optional<UserInfoResponse> getUserByCodeFallback(Throwable t) {
        log.error("getUserByCodeFallback called with userId: ", t);
        return Optional.empty();
    }

    Optional<UserExistResponse> checkedUserExistsFallback(Throwable t) {
        log.error("getUserByCodeFallback called with userId: ", t);
        return Optional.empty();
    }
}
