package com.gamelist.game_service.repository;

import com.gamelist.game_service.entity.StatusUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusUpdateRepository extends JpaRepository<StatusUpdate, Long> {

    //    @Query("SELECT s FROM status_updates s WHERE s.userGame.user.id = ?1")
    //    List<StatusUpdateView> findAllProjectedByUserId(Long userId);
}
