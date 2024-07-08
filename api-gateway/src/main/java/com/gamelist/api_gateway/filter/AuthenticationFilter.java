package com.gamelist.api_gateway.filter;

import com.gamelist.api_gateway.exception.InvalidTokenException;
import com.gamelist.api_gateway.util.JwtUtil;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);
    private final RouteValidator validator;
    private final JwtUtil jwtUtil;

    public AuthenticationFilter(RouteValidator validator, JwtUtil jwtUtil) {
        super(Config.class);
        this.validator = validator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        log.info("Authentication filter is working");
        return ((exchange, chain) -> {
            ServerHttpRequest request = null;
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing authorization header");
                }

                String authHeader = Objects.requireNonNull(
                                exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION))
                        .getFirst();
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    jwtUtil.validateToken(authHeader);
                    request = exchange.getRequest()
                            .mutate()
                            .header("userId", jwtUtil.extractUserId(authHeader))
                            .header("email", jwtUtil.extractEmail(authHeader))
                            .build();
                } catch (Exception e) {
                    throw new InvalidTokenException("Unauthorized access to application");
                }
            }
            assert request != null;
            return chain.filter(exchange.mutate().request(request).build());
        });
    }

    public static class Config {}
}
