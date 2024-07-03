package com.gamelist.game_service.repository;

import com.gamelist.game_service.entity.UserGame;
import com.gamelist.game_service.projection.UserGameProjection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserGameRepository extends JpaRepository<UserGame, Long> {
    UserGame findFirstByUserIdAndGameId(String userId, Long gameId);

    Optional<Set<UserGame>> findAllByUserId(String userId);

    Optional<UserGame> findByGameIdAndUserId(Long gameId, String userId);

    @Query("SELECT CASE WHEN COUNT(ug) > 0 THEN true ELSE false END FROM user_games ug " + "WHERE ug.game.id = :gameId "
            + "AND ug.userId = :userId "
            + "AND ug.gameStatus != 'Inactive'")
    boolean existsByGameIdAndUserIdAndGameStatusNotInactive(
            @Param("gameId") Long gameId, @Param("userId") String userId);

    @Query(
            value =
                    """
            SELECT
                g.id,
                g.name,
                g.avg_score AS avgScore,
                g.description,
                g.imageurl,
                g.bannerurl,
                g.releasedate,
                g.total_rating AS totalRating,
                ug.game_status AS gameStatus,
                string_agg(DISTINCT t.name, ',') AS tags,
                string_agg(DISTINCT p.name, ',') AS platforms,
                string_agg(DISTINCT ge.name, ',') AS genres,
                CASE WHEN EXISTS (SELECT 1 FROM like_entities l WHERE l.user_id = :userId AND l.interactive_entity_id = g.id) THEN true ELSE false END AS gameLiked,
                CASE WHEN ug.game_status != 'Inactive' THEN true ELSE false END AS gameAdded
            FROM user_games ug JOIN games g ON ug.game_id = g.id
                               LEFT JOIN games_tags gt ON g.id = gt.game_id
                               LEFT JOIN tags t ON gt.tag_id = t.id
                               LEFT JOIN games_platforms gp ON g.id = gp.game_id
                               LEFT JOIN platforms p ON gp.platform_id = p.id
                               LEFT JOIN games_genres gg on g.id = gg.game_id
                               LEFT JOIN genres ge on gg.genre_id = ge.id
            WHERE ug.user_id = :userId
            GROUP BY g.id, g.name, g.avg_score, g.description, g.imageurl, g.bannerurl, g.releasedate, g.total_rating, ug.game_status
            ORDER BY ug.game_status
            """,
            nativeQuery = true)
    List<UserGameProjection> findUserGamesByUserId(@Param("userId") String userId);
}
