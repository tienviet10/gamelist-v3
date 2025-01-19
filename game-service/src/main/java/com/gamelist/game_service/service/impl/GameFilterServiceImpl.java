package com.gamelist.game_service.service.impl;

import com.gamelist.game_service.exception.*;
import com.gamelist.game_service.model.*;
import com.gamelist.game_service.repository.*;
import com.gamelist.game_service.service.*;
import lombok.*;
import org.slf4j.*;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@EnableCaching
@RequiredArgsConstructor
public class GameFilterServiceImpl implements GameFilterService {

    private final Logger logger = LoggerFactory.getLogger(GameFilterServiceImpl.class);
    private final GenreRepository genreRepository;
    private final PlatformRepository platformRepository;
    private final TagRepository tagRepository;
    private final GameRepository gameRepository;

    @Cacheable(value = "gameFilters")
    @Override
    public GameFilters getGameFilters() {
        logger.info("Fetching game filters");
        try {
            final List<String> genres = genreRepository.getAllNames();
            final List<String> platforms = platformRepository.getAllNames();
            final List<String> tags = tagRepository.getAllNames();
            final int furthestYear = gameRepository.getFurthestYear();
            logger.info("Retrieved filters: Genres = {}, Platforms = {}, Tags = {}", genres.size(), platforms.size(), tags.size());
            return new GameFilters(genres, platforms, tags, furthestYear);
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to fetch game filters");
        }
    }
}
