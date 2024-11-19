package com.gamelist.game_service.service.impl;

import com.gamelist.game_service.dto.GameDTO;
import com.gamelist.game_service.entity.Game;
import com.gamelist.game_service.mapper.GameMapper;
import com.gamelist.game_service.model.GameQueryFilters;
import com.gamelist.game_service.repository.GameRepository;
import com.gamelist.game_service.repository.LikeRepository;
import com.gamelist.game_service.repository.UserGameRepository;
import com.gamelist.game_service.service.GameService;
import com.gamelist.game_service.specification.GameSpecification;
import com.gamelist.game_service.utils.Utils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
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
    private final UserGameRepository userGameRepository;
    private final LikeRepository likeRepository;
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

        Specification<Game> gameSpecification = new GameSpecification(gameQueryFilters);

        TypedQuery<Game> foundGames = getQuery(gameSpecification, Game.class);
        foundGames.setMaxResults(gameQueryFilters.getLimit());

        List<GameDTO> gameDTOs = gameMapper.gamesToGameDTOs(foundGames.getResultList());

        for (GameDTO gameDTO : gameDTOs) {
            if (userId == null) {
                gameDTO.setGameAdded(false);
                gameDTO.setGameLiked(false);
                continue;
            }

            //            gameDTO.setGameAdded(
            //                    userGameRepository.existsByGameIdAndUserIdAndGameStatusNotInactive(gameDTO.getId(),
            // userId));
            //            gameDTO.setGameLiked(likeRepository.existsByUserIdAndInteractiveEntityId(userId,
            // gameDTO.getId()));
        }

        return gameDTOs;
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
}
