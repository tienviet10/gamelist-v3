package com.gamelist.social_service.repository;

import com.gamelist.social_service.entity.Comment;
import com.gamelist.social_service.projection.CommentView;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<CommentView> findProjectedById(Long id);
}
