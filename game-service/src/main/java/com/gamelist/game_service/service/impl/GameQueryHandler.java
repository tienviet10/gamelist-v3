package com.gamelist.game_service.service.impl;

import com.gamelist.game_service.model.GameQueryFilters;
import com.gamelist.game_service.projection.GameProjection;
import com.gamelist.game_service.projection.GameProjectionDTO;
import com.gamelist.game_service.projection.GameProjectionImpl;
import com.gamelist.game_service.repository.GameRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class GameQueryHandler {
    private final GameRepository gameRepository;
    private final CacheManager cacheManager;

    public GameQueryHandler(GameRepository gameRepository, CacheManager cacheManager) {
        this.gameRepository = gameRepository;
        this.cacheManager = cacheManager;
    }

    public List<GameProjection> handleEmptyUserId(GameQueryFilters gameQueryFilters) {
        String cacheKey = gameQueryFilters.toString();
        Cache cache = cacheManager.getCache("gamesByEmptyUserId");

        if (cache != null) {
            List<GameProjectionDTO> cachedDTOs = cache.get(cacheKey, List.class);
            if (cachedDTOs != null) {
                return cachedDTOs.stream().map(this::convertToGameProjection).collect(Collectors.toList());
            }
        }

        if (gameQueryFilters.getGameQueryPaginationOptions() == null) {
            return gameRepository.findGameByCategoryAndLimitAndNoStartingId(gameQueryFilters.getLimit(), "");
        } else {
            return gameRepository.findGameByCategoryAndLimit(
                    gameQueryFilters.getGameQueryPaginationOptions().getLastName(), gameQueryFilters.getLimit(), "");
        }
    }

    public List<GameProjection> handleNonEmptyUserId(GameQueryFilters gameQueryFilters, String userId) {
        if (gameQueryFilters.getGameQueryPaginationOptions() == null) {
            return gameRepository.findGameByCategoryAndLimitAndNoStartingId(gameQueryFilters.getLimit(), userId);
        } else {
            return gameRepository.findGameByCategoryAndLimit(
                    gameQueryFilters.getGameQueryPaginationOptions().getLastName(),
                    gameQueryFilters.getLimit(),
                    userId);
        }
    }

    public List<GameProjection> preloadGamesCache(GameQueryFilters gameQueryFilters) {
        List<GameProjection> games;
        if (gameQueryFilters.getGameQueryPaginationOptions() == null) {
            games = gameRepository.findGameByCategoryAndLimitAndNoStartingId(gameQueryFilters.getLimit(), "");
        } else {
            games = gameRepository.findGameByCategoryAndLimit(
                    gameQueryFilters.getGameQueryPaginationOptions().getLastName(), gameQueryFilters.getLimit(), "");
        }

        List<GameProjectionDTO> gameDTOs = games.stream()
                .map(game -> {
                    GameProjectionDTO dto = new GameProjectionDTO();
                    dto.setId(game.getId());
                    dto.setName(game.getName());
                    dto.setAvgScore(game.getAvgScore());
                    dto.setDescription(game.getDescription());
                    dto.setImageUrl(game.getImageurl());
                    dto.setBannerUrl(game.getBannerurl());
                    dto.setGameAdded(game.getGameAdded());
                    dto.setReleaseDate(game.getReleasedate());
                    dto.setTotalRating(game.getTotalRating());
                    dto.setPlatforms(game.getPlatforms());
                    dto.setGenres(game.getGenres());
                    dto.setTags(game.getTags());
                    return dto;
                })
                .collect(Collectors.toList());

        String cacheKey = gameQueryFilters.toString();

        cacheManager.getCache("gamesByEmptyUserId").put(cacheKey, gameDTOs);

        return games;
    }

    private GameProjection convertToGameProjection(GameProjectionDTO dto) {
        GameProjectionImpl projection = new GameProjectionImpl();
        projection.setId(dto.getId());
        projection.setName(dto.getName());
        projection.setAvgScore(dto.getAvgScore());
        projection.setDescription(dto.getDescription());
        projection.setImageurl(dto.getImageUrl());
        projection.setBannerurl(dto.getBannerUrl());
        projection.setGameAdded(dto.getGameAdded());
        projection.setReleasedate(dto.getReleaseDate());
        projection.setTotalRating(dto.getTotalRating());
        projection.setPlatforms(dto.getPlatforms());
        projection.setGenres(dto.getGenres());
        projection.setTags(dto.getTags());
        return projection;
    }
}
