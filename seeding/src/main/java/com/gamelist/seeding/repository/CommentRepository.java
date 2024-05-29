package com.gamelist.seeding.repository;

import com.gamelist.seeding.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByInteractiveEntityId(Long interactiveEntityId);

//    List<CommentView> findProjectedByInteractiveEntityId(Long interactiveEntityId);
//
//    Optional<CommentView> findProjectedById(Long id);
}
