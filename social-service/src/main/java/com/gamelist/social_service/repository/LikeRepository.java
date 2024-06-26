package com.gamelist.social_service.repository;

import com.gamelist.social_service.entity.LikeEntity;
import com.gamelist.social_service.projection.LikeEntityView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    LikeEntityView findProjectedById(Long likeEntityId);

    boolean existsByUserIdAndInteractiveEntityId(String userId, Long interactiveEntityId);

    void deleteByUserIdAndInteractiveEntityId(String userId, Long interactiveEntityId);
}
