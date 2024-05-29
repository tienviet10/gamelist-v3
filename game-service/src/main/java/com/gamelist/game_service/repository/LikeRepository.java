package com.gamelist.game_service.repository;

import com.gamelist.game_service.entity.LikeEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

    //    Optional<LikeEntityView> findLikeEntityViewById(Long id);
    //
    //    LikeEntityView findProjectedById(Long likeEntityId);

    boolean existsByUserIdAndInteractiveEntityId(Long userId, Long interactiveEntityId);

    Optional<LikeEntity> findByUserIdAndInteractiveEntityId(Long userId, Long interactiveEntityId);

    void deleteByUserIdAndInteractiveEntityId(Long userId, Long interactiveEntityId);
}
