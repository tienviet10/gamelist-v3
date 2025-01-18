package com.gamelist.game_service.crons;

import com.gamelist.game_service.dto.GameDTO;
import com.gamelist.game_service.model.GameQueryFilters;
import com.gamelist.game_service.service.impl.GameQueryHandler;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GameCachePreloader {
    private final GameQueryHandler gameQueryHandler;

    public GameCachePreloader(GameQueryHandler gameQueryHandler) {
        this.gameQueryHandler = gameQueryHandler;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void preloadGames() {
        GameQueryFilters filters = new GameQueryFilters();
        filters.setLimit(20);
        filters.setSortBy("name");
        filters.setGenres(new ArrayList<>());
        filters.setExcludedGenres(new ArrayList<>());
        filters.setPlatforms(new ArrayList<>());
        filters.setExcludedPlatforms(new ArrayList<>());
        filters.setTags(new ArrayList<>());
        filters.setExcludedTags(new ArrayList<>());
        filters.setYear(0);
        filters.setSearch("");
        GameQueryFilters.GameQueryPaginationOptions paginationOptions = null;

        for (int i = 0; i < 4; i++) {
            filters.setGameQueryPaginationOptions(paginationOptions);

            List<GameDTO> games = gameQueryHandler.preloadGamesCache(filters);

            if (!games.isEmpty()) {
                paginationOptions = new GameQueryFilters.GameQueryPaginationOptions();
                GameDTO lastGame = games.get(games.size() - 1);
                paginationOptions.setLastId(lastGame.getId().intValue());
                paginationOptions.setLastName(lastGame.getName());
                paginationOptions.setLastReleaseDateEpoch(
                        lastGame.getReleaseDate().atZone(ZoneId.systemDefault()).toEpochSecond());
                paginationOptions.setLastAverageScore(lastGame.getAvgScore());
                paginationOptions.setLastTotalRating(lastGame.getTotalRating());
            } else {
                break;
            }
        }
    }
}
