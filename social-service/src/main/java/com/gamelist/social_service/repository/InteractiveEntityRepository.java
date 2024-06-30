package com.gamelist.social_service.repository;

import com.gamelist.social_service.entity.InteractiveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InteractiveEntityRepository extends JpaRepository<InteractiveEntity, Long> {
    @Query("SELECT i FROM interactive_entities i WHERE "
            + "i.id IN (SELECT p.id FROM posts p WHERE p.userId = :userId) OR "
            + "i.id IN (SELECT su.id FROM status_updates su JOIN su.userGame ug WHERE ug.userId = :userId) "
            + "ORDER BY i.createdAt DESC")
    List<InteractiveEntity> findAllPostsAndStatusUpdatesByUserId(@Param("userId") String userId);

    @Query("SELECT i FROM interactive_entities i WHERE "
            + "(i.id IN (SELECT p.id FROM posts p WHERE p.userId = :userId) OR "
            + "i.id IN (SELECT su.id FROM status_updates su JOIN su.userGame ug WHERE ug.userId = :userId)) "
            + "AND i.id < :id "
            + "ORDER BY i.createdAt DESC "
            + "LIMIT :limit")
    List<InteractiveEntity> findPostsAndStatusUpdatesByUserIdAndStartingWithIdDesc(
            @Param("userId") String userId, @Param("id") Long id, @Param("limit") int limit);

    @Query("SELECT i FROM interactive_entities i WHERE "
            + "(i.id IN (SELECT p.id FROM posts p WHERE p.userId = :userId) OR "
            + "i.id IN (SELECT su.id FROM status_updates su JOIN su.userGame ug WHERE ug.userId = :userId)) "
            + "ORDER BY i.createdAt DESC "
            + "LIMIT :limit")
    List<InteractiveEntity> findPostsAndStatusUpdatesByUserIdFirstPage(
            @Param("userId") String userId, @Param("limit") int limit);

    @Query("SELECT i FROM interactive_entities i WHERE " + "i.id IN (SELECT p.id FROM posts p) OR "
            + "i.id IN (SELECT su.id FROM status_updates su) "
            + "ORDER BY i.createdAt DESC "
            + "LIMIT :limit")
    List<InteractiveEntity> findAllPostsAndStatusUpdatesFirstPage(@Param("limit") int limit);

    @Query("SELECT i FROM interactive_entities i WHERE " + "(i.id IN (SELECT p.id FROM posts p) OR "
            + "i.id IN (SELECT su.id FROM status_updates su)) "
            + "AND i.id < :id "
            + "ORDER BY i.createdAt DESC "
            + "LIMIT :limit")
    List<InteractiveEntity> findAllPostsAndStatusUpdatesStartingWithIdDesc(
            @Param("id") Long id, @Param("limit") int limit);
}
