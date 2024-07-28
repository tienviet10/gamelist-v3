package com.gamelist.game_service.repository;

import com.gamelist.game_service.entity.Game;
import com.gamelist.game_service.projection.GameProjection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GameRepository extends JpaRepository<Game, Long>, JpaSpecificationExecutor<Game> {
    String CASE_WHEN = "\nCASE\n"
            + "    WHEN :field = 'day_01' THEN day_01\n"
            + "    WHEN :field = 'day_02' THEN day_02\n"
            + "    WHEN :field = 'day_03' THEN day_03\n"
            + "    WHEN :field = 'day_04' THEN day_04\n"
            + "    WHEN :field = 'day_05' THEN day_05\n"
            + "    ELSE 0\n"
            + "END\n";

    @Query("SELECT EXTRACT(YEAR FROM MAX(releaseDate)) FROM games")
    int getFurthestYear();

    @Query(
            value =
                    """
                            SELECT
                                g.id,
                                g.name,
                                g.avg_score as avgScore,
                                g.description,
                                g.imageurl,
                                g.bannerurl,
                                g.releasedate,
                                g.total_rating as totalRating,
                                string_agg(DISTINCT ge.name, ', ' ORDER BY ge.name) AS genres,
                                string_agg(DISTINCT p.name, ', ' ORDER BY p.name) AS platforms,
                                string_agg(DISTINCT t.name, ', ' ORDER BY t.name) AS tags,
                                CASE
                                    WHEN EXISTS (
                                        SELECT 1
                                        FROM user_games ug
                                        WHERE ug.game_id = g.id AND ug.game_status != 'Inactive' AND ug.user_id = :userId
                                    ) THEN true
                                    ELSE false
                                    END AS gameAdded
                            FROM
                                games g
                                    LEFT JOIN
                                games_genres gg ON g.id = gg.game_id
                                    LEFT JOIN
                                genres ge ON gg.genre_id = ge.id
                                    LEFT JOIN
                                games_platforms gp ON g.id = gp.game_id
                                    LEFT JOIN
                                platforms p ON gp.platform_id = p.id
                                    LEFT JOIN
                                games_tags gt ON g.id = gt.game_id
                                    LEFT JOIN
                                tags t ON gt.tag_id = t.id
                            WHERE
                                g.name > :name
                            GROUP BY
                                g.name, g.id
                            ORDER BY
                                g.name
                            LIMIT :limit """,
            nativeQuery = true)
    List<GameProjection> findGameByCategoryAndLimit(
            @Param("name") String name, @Param("limit") int limit, @Param("userId") String userId);

    @Query(
            value =
                    """
                            SELECT
                                g.id,
                                g.name,
                                g.avg_score as avgScore,
                                g.description,
                                g.imageurl,
                                g.bannerurl,
                                g.releasedate,
                                g.total_rating as totalRating,
                                string_agg(DISTINCT ge.name, ', ' ORDER BY ge.name) AS genres,
                                string_agg(DISTINCT p.name, ', ' ORDER BY p.name) AS platforms,
                                string_agg(DISTINCT t.name, ', ' ORDER BY t.name) AS tags,
                                CASE
                                    WHEN EXISTS (
                                        SELECT 1
                                        FROM user_games ug
                                        WHERE ug.game_id = g.id AND ug.game_status != 'Inactive' AND ug.user_id = :userId
                                    ) THEN true
                                    ELSE false
                                    END AS gameAdded
                            FROM
                                games g
                                    LEFT JOIN
                                games_genres gg ON g.id = gg.game_id
                                    LEFT JOIN
                                genres ge ON gg.genre_id = ge.id
                                    LEFT JOIN
                                games_platforms gp ON g.id = gp.game_id
                                    LEFT JOIN
                                platforms p ON gp.platform_id = p.id
                                    LEFT JOIN
                                games_tags gt ON g.id = gt.game_id
                                    LEFT JOIN
                                tags t ON gt.tag_id = t.id
                            GROUP BY
                                g.name, g.id
                            ORDER BY
                                g.name
                            LIMIT :limit
                            """,
            nativeQuery = true)
    List<GameProjection> findGameByCategoryAndLimitAndNoStartingId(
            @Param("limit") int limit, @Param("userId") String userId);
}
