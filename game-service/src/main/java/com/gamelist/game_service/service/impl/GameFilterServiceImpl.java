package com.gamelist.game_service.service.impl;

import com.gamelist.game_service.exception.InternalServerErrorException;
import com.gamelist.game_service.model.GameFilters;
import com.gamelist.game_service.repository.GameRepository;
import com.gamelist.game_service.repository.GenreRepository;
import com.gamelist.game_service.repository.PlatformRepository;
import com.gamelist.game_service.repository.TagRepository;
import com.gamelist.game_service.service.GameFilterService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameFilterServiceImpl implements GameFilterService {
    private final GenreRepository genreRepository;
    private final PlatformRepository platformRepository;
    private final TagRepository tagRepository;
    private final GameRepository gameRepository;

    private GameFilters cachedGameFilters;

    @Override
    public GameFilters getGameFilters() {
        if (cachedGameFilters == null) {
            try {
                final List<String> genres = genreRepository.getAllNames();
                final List<String> platforms = platformRepository.getAllNames();
                final List<String> tags = tagRepository.getAllNames();
                final int furthestYear = gameRepository.getFurthestYear();
                this.cachedGameFilters = new GameFilters(genres, platforms, tags, furthestYear);
            } catch (Exception e) {
                throw new InternalServerErrorException("Failed to fetch game filters");
            }
        }
        return cachedGameFilters;
    }
}
