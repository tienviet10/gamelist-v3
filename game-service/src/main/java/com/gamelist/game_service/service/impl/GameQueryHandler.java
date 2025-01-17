package com.gamelist.game_service.service.impl;

import com.gamelist.game_service.dto.GameDTO;
import com.gamelist.game_service.mapper.GameV2Mapper;
import com.gamelist.game_service.model.GameQueryFilters;
import com.gamelist.game_service.repository.GameRepository;
import java.util.List;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class GameQueryHandler {
    private final GameRepository gameRepository;
    private final CacheManager cacheManager;
    private final GameV2Mapper gameV2Mapper;

    public GameQueryHandler(GameRepository gameRepository, CacheManager cacheManager, GameV2Mapper gameV2Mapper) {
        this.gameRepository = gameRepository;
        this.cacheManager = cacheManager;
        this.gameV2Mapper = gameV2Mapper;
    }

    public List<GameDTO> handleEmptyUserId(GameQueryFilters gameQueryFilters) {
        String cacheKey = gameQueryFilters.toString();
        Cache cache = cacheManager.getCache("gamesByEmptyUserId");

        if (cache != null) {
            List<GameDTO> cachedDTOs = cache.get(cacheKey, List.class);
            if (cachedDTOs != null) {
                return cachedDTOs;
            }
        }

        if (gameQueryFilters.getGameQueryPaginationOptions() == null) {
            return gameV2Mapper.gamesToGameDTOs(
                    gameRepository.findGameByCategoryAndLimitAndNoStartingId(gameQueryFilters.getLimit(), ""));
        } else {
            return gameV2Mapper.gamesToGameDTOs(gameRepository.findGameByCategoryAndLimit(
                    gameQueryFilters.getGameQueryPaginationOptions().getLastName(), gameQueryFilters.getLimit(), ""));
        }
    }

    public List<GameDTO> handleNonEmptyUserId(GameQueryFilters gameQueryFilters, String userId) {
        if (gameQueryFilters.getGameQueryPaginationOptions() == null) {
            return gameV2Mapper.gamesToGameDTOs(
                    gameRepository.findGameByCategoryAndLimitAndNoStartingId(gameQueryFilters.getLimit(), userId));
        } else {
            return gameV2Mapper.gamesToGameDTOs(gameRepository.findGameByCategoryAndLimit(
                    gameQueryFilters.getGameQueryPaginationOptions().getLastName(),
                    gameQueryFilters.getLimit(),
                    userId));
        }
    }

    public List<GameDTO> preloadGamesCache(GameQueryFilters gameQueryFilters) {
        List<GameDTO> games;
        if (gameQueryFilters.getGameQueryPaginationOptions() == null) {
            games = gameV2Mapper.gamesToGameDTOs(
                    gameRepository.findGameByCategoryAndLimitAndNoStartingId(gameQueryFilters.getLimit(), ""));
        } else {
            games = gameV2Mapper.gamesToGameDTOs(gameRepository.findGameByCategoryAndLimit(
                    gameQueryFilters.getGameQueryPaginationOptions().getLastName(), gameQueryFilters.getLimit(), ""));
        }

        String cacheKey = gameQueryFilters.toString();

        cacheManager.getCache("gamesByEmptyUserId").put(cacheKey, games);

        return games;
    }
}
