package com.gamelist.game_service.repository;

import com.gamelist.game_service.entity.StatusUpdate;
import com.gamelist.game_service.projection.StatusUpdateView;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StatusUpdateRepository extends JpaRepository<StatusUpdate, Long> {
    @Query("SELECT s FROM status_updates s WHERE s.userGame.userId = ?1")
    List<StatusUpdateView> findAllProjectedByUserId(String userId);
}
