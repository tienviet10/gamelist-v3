package com.gamelist.social_service.repository;

import com.gamelist.social_service.entity.Post;
import com.gamelist.social_service.projection.PostView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<List<PostView>> findAllProjectedByUserId(String userId);

    Optional<PostView> findProjectedById(Long postId);

    @Query("SELECT p FROM posts p LEFT JOIN FETCH p.likes l")
    List<PostView> findAllPosts();
}
