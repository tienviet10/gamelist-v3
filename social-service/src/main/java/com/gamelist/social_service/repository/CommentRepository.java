package com.gamelist.social_service.repository;

import com.gamelist.social_service.entity.Comment;
import com.gamelist.social_service.projection.CommentView;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<CommentView> findProjectedById(Long id);

    @Query(
            value =
                    """
                            SELECT
                                comment_id AS commentId,
                                interactive_entity_id AS interactiveEntityId,
                                user_id AS userId,
                                comment_created_at AS commentCreatedAt,
                                comment_updated_at AS commentUpdatedAt,
                                text
                            FROM
                                comments
                            WHERE
                                interactive_entity_id = :interactiveEntityId AND comment_id < :commendId
                            ORDER BY
                                comment_created_at desc
                            LIMIT 6
                            """,
            nativeQuery = true)
    List<CommentView> getCommentsByStartingId(
            @Param("interactiveEntityId") Long interactiveEntityId, @Param("commendId") Long commendId);
}
