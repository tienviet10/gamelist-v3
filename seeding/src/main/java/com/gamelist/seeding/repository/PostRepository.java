package com.gamelist.seeding.repository;

import com.gamelist.seeding.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM posts p LEFT JOIN FETCH p.likes l WHERE p.id = :postId")
    Optional<Post> findPostWithLikesById(@Param("postId") Long postId);
}
