package com.gamelist.game_service.repository;

import com.gamelist.game_service.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByInteractiveEntityId(Long interactiveEntityId);

    //    List<CommentView> findProjectedByInteractiveEntityId(Long interactiveEntityId);
    //
    //    Optional<CommentView> findProjectedById(Long id);
}
