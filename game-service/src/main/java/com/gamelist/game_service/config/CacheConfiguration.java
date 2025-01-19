package com.gamelist.game_service.config;

import com.github.benmanes.caffeine.cache.*;
import org.springframework.cache.*;
import org.springframework.cache.annotation.*;
import org.springframework.cache.caffeine.*;
import org.springframework.context.annotation.*;

import java.time.*;

@EnableCaching
@Configuration
public class CacheConfiguration {

    // TODO: Make the cache expiration time configurable
    @Bean
    public CacheManager gameFiltersCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        cacheManager.registerCustomCache("gameFilters",
                Caffeine.newBuilder()
                        .expireAfterWrite(Duration.ofHours(1))
                        .build());

        return cacheManager;
    }
}