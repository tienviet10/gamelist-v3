package com.gamelist.social_service.repository;

import com.gamelist.social_service.entity.InteractiveEntity;
import com.gamelist.social_service.projection.InteractiveEntityProjection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InteractiveEntityRepository extends JpaRepository<InteractiveEntity, Long> {
    //    @Query("SELECT i FROM interactive_entities i WHERE "
    //            + "(i.id IN (SELECT p.id FROM posts p WHERE p.userId = :userId) OR "
    //            + "i.id IN (SELECT su.id FROM status_updates su JOIN su.userGame ug WHERE ug.userId = :userId)) "
    //            + "AND i.id < :id "
    //            + "ORDER BY i.createdAt DESC "
    //            + "LIMIT :limit")
    //    List<InteractiveEntity> findPostsAndStatusUpdatesByUserIdAndStartingWithIdDesc(
    //            @Param("userId") String userId, @Param("id") Long id, @Param("limit") int limit);
    @Query(
            value =
                    """
                            SELECT
                                i.id,
                                i.created_at as createdAt,
                                p.user_id as userId,
                                p.text as postText,
                                su.status_update_id as statusUpdateId,
                                su.user_game_id as suUsergameId,
                                su.game_status as suGameStatus,
                                ug.id as userGameId,
                                ug.user_id as userGameUserId,
                                g.id as gameId,
                                g.name as gameName,
                                g.imageurl as gameImageurl,
                                g.bannerurl as gameBannerurl,
                                COALESCE(
                                        (
                                            SELECT json_agg(likes)
                                            FROM like_entities likes
                                            WHERE likes.interactive_entity_id = i.id
                                        ), '[]'
                                ) AS likes,
                                COALESCE(
                                        (
                                            SELECT json_agg(comments)
                                            FROM comments comments
                                            WHERE comments.interactive_entity_id = i.id
                                        ), '[]'
                                ) AS comments
                            FROM
                                interactive_entities i
                                    LEFT JOIN
                                posts p ON i.id = p.post_id
                                    LEFT JOIN
                                status_updates su ON i.id = su.status_update_id
                                    LEFT JOIN
                                user_games ug ON su.user_game_id = ug.id
                                    LEFT JOIN
                                games g ON ug.game_id = g.id
                            WHERE
                                (i.id IN (SELECT p.post_id FROM posts p WHERE p.user_id = :userId)
                               OR i.id IN (SELECT su.status_update_id FROM status_updates su JOIN user_games ug ON su.user_game_id = ug.id WHERE ug.user_id = :userId))
                                AND i.id < :id
                            ORDER BY
                                i.created_at DESC
                            LIMIT :limit
                                                        """,
            nativeQuery = true)
    List<InteractiveEntityProjection> findPostsAndStatusUpdatesByUserIdAndStartingWithIdDescV2(
            @Param("userId") String userId, @Param("id") Long id, @Param("limit") int limit);

    //    @Query("SELECT i FROM interactive_entities i WHERE "
    //            + "(i.id IN (SELECT p.id FROM posts p WHERE p.userId = :userId) OR "
    //            + "i.id IN (SELECT su.id FROM status_updates su JOIN su.userGame ug WHERE ug.userId = :userId)) "
    //            + "ORDER BY i.createdAt DESC "
    //            + "LIMIT :limit")
    //    List<InteractiveEntity> findPostsAndStatusUpdatesByUserIdFirstPage(
    //            @Param("userId") String userId, @Param("limit") int limit);
    @Query(
            value =
                    """
                            SELECT
                                i.id,
                                i.created_at as createdAt,
                                p.user_id as userId,
                                p.text as postText,
                                su.status_update_id as statusUpdateId,
                                su.user_game_id as suUsergameId,
                                su.game_status as suGameStatus,
                                ug.id as userGameId,
                                ug.user_id as userGameUserId,
                                g.id as gameId,
                                g.name as gameName,
                                g.imageurl as gameImageurl,
                                g.bannerurl as gameBannerurl,
                                COALESCE(
                                        (
                                            SELECT json_agg(likes)
                                            FROM like_entities likes
                                            WHERE likes.interactive_entity_id = i.id
                                        ), '[]'
                                ) AS likes,
                                COALESCE(
                                        (
                                            SELECT json_agg(comments)
                                            FROM comments comments
                                            WHERE comments.interactive_entity_id = i.id
                                        ), '[]'
                                ) AS comments
                            FROM
                                interactive_entities i
                                    LEFT JOIN
                                posts p ON i.id = p.post_id
                                    LEFT JOIN
                                status_updates su ON i.id = su.status_update_id
                                    LEFT JOIN
                                user_games ug ON su.user_game_id = ug.id
                                    LEFT JOIN
                                games g ON ug.game_id = g.id
                            WHERE
                                i.id IN (SELECT p.post_id FROM posts p WHERE p.user_id = :userId)
                               OR i.id IN (SELECT su.status_update_id FROM status_updates su JOIN user_games ug ON su.user_game_id = ug.id WHERE ug.user_id = :userId)
                            ORDER BY
                                i.created_at DESC
                            LIMIT :limit
                                                        """,
            nativeQuery = true)
    List<InteractiveEntityProjection> findPostsAndStatusUpdatesByUserIdFirstPageV2(
            @Param("userId") String userId, @Param("limit") int limit);

    //    @Query("SELECT i FROM interactive_entities i WHERE " + "i.id IN (SELECT p.id FROM posts p) OR "
    //            + "i.id IN (SELECT su.id FROM status_updates su) "
    //            + "ORDER BY i.createdAt DESC "
    //            + "LIMIT :limit")
    //    List<InteractiveEntity> findAllPostsAndStatusUpdatesFirstPage(@Param("limit") int limit);

    @Query(
            value =
                    """
                            SELECT
                                i.id,
                                i.created_at as createdAt,
                                p.user_id as userId,
                                p.text as postText,
                                su.status_update_id as statusUpdateId,
                                su.user_game_id as suUsergameId,
                                su.game_status as suGameStatus,
                                ug.id as userGameId,
                                ug.user_id as userGameUserId,
                                g.id as gameId,
                                g.name as gameName,
                                g.imageurl as gameImageurl,
                                g.bannerurl as gameBannerurl,
                                COALESCE(
                                        (
                                            SELECT json_agg(likes)
                                            FROM like_entities likes
                                            WHERE likes.interactive_entity_id = i.id
                                        ), '[]'
                                ) AS likes,
                                COALESCE(
                                        (
                                            SELECT json_agg(comments)
                                            FROM comments comments
                                            WHERE comments.interactive_entity_id = i.id
                                        ), '[]'
                                ) AS comments
                            FROM
                                interactive_entities i
                                    LEFT JOIN
                                posts p ON i.id = p.post_id
                                    LEFT JOIN
                                status_updates su ON i.id = su.status_update_id
                                    LEFT JOIN
                                user_games ug ON su.user_game_id = ug.id
                                    LEFT JOIN
                                games g ON ug.game_id = g.id
                            WHERE
                                i.id IN (SELECT p.post_id FROM posts p)
                               OR i.id IN (SELECT su.status_update_id FROM status_updates su)
                            ORDER BY
                                i.created_at DESC
                            LIMIT :limit
                                                        """,
            nativeQuery = true)
    List<InteractiveEntityProjection> findAllPostsAndStatusUpdatesFirstPageV2(@Param("limit") int limit);

    //    @Query("SELECT i FROM interactive_entities i WHERE " + "(i.id IN (SELECT p.id FROM posts p) OR "
    //            + "i.id IN (SELECT su.id FROM status_updates su)) "
    //            + "AND i.id < :id "
    //            + "ORDER BY i.createdAt DESC "
    //            + "LIMIT :limit")
    //    List<InteractiveEntity> findAllPostsAndStatusUpdatesStartingWithIdDesc(
    //            @Param("id") Long id, @Param("limit") int limit);
    @Query(
            value =
                    """
                            SELECT
                                i.id,
                                i.created_at as createdAt,
                                p.user_id as userId,
                                p.text as postText,
                                su.status_update_id as statusUpdateId,
                                su.user_game_id as suUsergameId,
                                su.game_status as suGameStatus,
                                ug.id as userGameId,
                                ug.user_id as userGameUserId,
                                g.id as gameId,
                                g.name as gameName,
                                g.imageurl as gameImageurl,
                                g.bannerurl as gameBannerurl,
                                COALESCE(
                                        (
                                            SELECT json_agg(likes)
                                            FROM like_entities likes
                                            WHERE likes.interactive_entity_id = i.id
                                        ), '[]'
                                ) AS likes,
                                COALESCE(
                                        (
                                            SELECT json_agg(comments)
                                            FROM comments comments
                                            WHERE comments.interactive_entity_id = i.id
                                        ), '[]'
                                ) AS comments
                            FROM
                                interactive_entities i
                                    LEFT JOIN
                                posts p ON i.id = p.post_id
                                    LEFT JOIN
                                status_updates su ON i.id = su.status_update_id
                                    LEFT JOIN
                                user_games ug ON su.user_game_id = ug.id
                                    LEFT JOIN
                                games g ON ug.game_id = g.id
                            WHERE
                                (i.id IN (SELECT p.post_id FROM posts p)
                                OR i.id IN (SELECT su.status_update_id FROM status_updates su))
                                AND i.id < :id
                            ORDER BY
                                i.created_at DESC
                            LIMIT :limit
                                                        """,
            nativeQuery = true)
    List<InteractiveEntityProjection> findAllPostsAndStatusUpdatesStartingWithIdDescV2(
            @Param("id") Long id, @Param("limit") int limit);
}
