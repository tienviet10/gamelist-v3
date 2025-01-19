package com.gamelist.game_service.config;

import com.github.benmanes.caffeine.cache.*;
import org.springframework.boot.context.properties.*;
import org.springframework.cache.*;
import org.springframework.cache.annotation.*;
import org.springframework.cache.caffeine.*;
import org.springframework.context.annotation.*;

import java.time.*;

@EnableCaching
@Configuration
@ConfigurationProperties(prefix = "gamelist.game-filters")
public class CacheConfiguration {

    private int expirationTime;

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        Caffeine<Object, Object> builder = Caffeine.newBuilder();

        if (expirationTime > 0) {
            builder.expireAfterWrite(Duration.ofHours(expirationTime));
        }

        cacheManager.registerCustomCache("gameFilters", builder.build());

        return cacheManager;
    }
}