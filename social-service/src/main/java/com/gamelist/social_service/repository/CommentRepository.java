package com.gamelist.social_service.repository;

import com.gamelist.social_service.entity.Comment;
import com.gamelist.social_service.projection.CommentView;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByInteractiveEntityId(Long interactiveEntityId);

    List<CommentView> findProjectedByInteractiveEntityId(Long interactiveEntityId);

    Optional<CommentView> findProjectedById(Long id);
}
