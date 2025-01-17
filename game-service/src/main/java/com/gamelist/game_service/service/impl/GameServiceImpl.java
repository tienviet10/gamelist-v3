package com.gamelist.game_service.service.impl;

import com.gamelist.game_service.dto.GameDTO;
import com.gamelist.game_service.entity.Game;
import com.gamelist.game_service.mapper.GameMapper;
import com.gamelist.game_service.mapper.GameV2Mapper;
import com.gamelist.game_service.model.GameQueryFilters;
import com.gamelist.game_service.repository.GameRepository;
import com.gamelist.game_service.service.GameService;
import com.gamelist.game_service.specification.GameSpecification;
import com.gamelist.game_service.utils.Utils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private static final int DEFAULT_QUERY_LIMIT = 500;
    private static final int MIN_QUERY_LIMIT = 1;
    private static final int MAX_QUERY_LIMIT = 500;
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;
    private final GameV2Mapper gameV2Mapper;
    private final GameQueryHandler gameQueryHandler;
    private final EntityManager em;

    @Override
    public GameDTO getAGame(Long gameId) {
        return gameMapper.gameToGameDTO(gameRepository.findById(gameId).orElseThrow());
    }

    @Override
    public List<GameDTO> getAllGames(GameQueryFilters gameQueryFilters, String userId) {
        if (gameQueryFilters == null) {
            gameQueryFilters = new GameQueryFilters();
            gameQueryFilters.setLimit(DEFAULT_QUERY_LIMIT);
            gameQueryFilters.setSortBy("newest_releases");
        }

        // Ensure a limit is set, we don't want to fetch too many rows
        gameQueryFilters.setLimit(clampLimit(gameQueryFilters.getLimit()));

        if (isDefaultGameFilters(gameQueryFilters)
                && gameQueryFilters.getSortBy().equals("name")) {

            String finalUserId = userId == null ? "" : userId;

            if (finalUserId.isEmpty()) {
                return gameQueryHandler.handleEmptyUserId(gameQueryFilters);
            } else {
                return gameQueryHandler.handleNonEmptyUserId(gameQueryFilters, finalUserId);
            }
        }

        Specification<Game> gameSpecification = new GameSpecification(gameQueryFilters);

        TypedQuery<Game> foundGames = getQuery(gameSpecification, Game.class);
        foundGames.setMaxResults(gameQueryFilters.getLimit());

        return gameMapper.gamesToGameDTOs(foundGames.getResultList());
    }

    private <T> TypedQuery<T> getQuery(Specification<T> specification, Class<T> clazz) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(clazz);
        Root<T> root = query.from(clazz);

        root.fetch("genres", JoinType.LEFT);
        root.fetch("platforms", JoinType.LEFT);
        root.fetch("tags", JoinType.LEFT);

        query.select(root);
        if (specification != null) {
            query.where(specification.toPredicate(root, query, builder));
        }

        query.distinct(true);

        return em.createQuery(query);
    }

    private int clampLimit(int limit) {
        return Utils.clamp(limit, MIN_QUERY_LIMIT, MAX_QUERY_LIMIT);
    }

    private boolean isDefaultGameFilters(GameQueryFilters gameQueryFilters) {
        GameQueryFilters defaultGameFilter = createDefaultGameQueryFilters();
        return (gameQueryFilters.getGenres() == null
                        && gameQueryFilters.getExcludedGenres() == null
                        && gameQueryFilters.getPlatforms() == null
                        && gameQueryFilters.getExcludedPlatforms() == null
                        && gameQueryFilters.getTags() == null
                        && gameQueryFilters.getExcludedTags() == null
                        && gameQueryFilters.getYear() == 0
                        && gameQueryFilters.getSearch() == null)
                || (gameQueryFilters.getGenres().equals(defaultGameFilter.getGenres())
                        && gameQueryFilters.getExcludedGenres().equals(defaultGameFilter.getExcludedGenres())
                        && gameQueryFilters.getPlatforms().equals(defaultGameFilter.getPlatforms())
                        && gameQueryFilters.getExcludedPlatforms().equals(defaultGameFilter.getExcludedPlatforms())
                        && gameQueryFilters.getTags().equals(defaultGameFilter.getTags())
                        && gameQueryFilters.getExcludedTags().equals(defaultGameFilter.getExcludedTags())
                        && gameQueryFilters.getYear() == defaultGameFilter.getYear()
                        && gameQueryFilters.getSearch().equals(defaultGameFilter.getSearch()));
    }

    private GameQueryFilters createDefaultGameQueryFilters() {
        GameQueryFilters defaultFilters = new GameQueryFilters();
        defaultFilters.setLimit(DEFAULT_QUERY_LIMIT);
        defaultFilters.setSortBy("name");
        defaultFilters.setGenres(List.of());
        defaultFilters.setExcludedGenres(List.of());
        defaultFilters.setPlatforms(List.of());
        defaultFilters.setExcludedPlatforms(List.of());
        defaultFilters.setTags(List.of());
        defaultFilters.setExcludedTags(List.of());
        defaultFilters.setYear(0);
        defaultFilters.setSearch("");

        return defaultFilters;
    }
}
