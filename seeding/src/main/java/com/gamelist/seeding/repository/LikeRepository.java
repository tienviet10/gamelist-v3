package com.gamelist.seeding.repository;

import com.gamelist.seeding.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

//    Optional<LikeEntityView> findLikeEntityViewById(Long id);
//
//    LikeEntityView findProjectedById(Long likeEntityId);
//
//    boolean existsByUserIdAndInteractiveEntityId(Long userId, Long interactiveEntityId);
//
//    Optional<LikeEntity> findByUserIdAndInteractiveEntityId(Long userId, Long interactiveEntityId);
//
//    void deleteByUserIdAndInteractiveEntityId(Long userId, Long interactiveEntityId);
}
