package com.gamelist.seeding.repository;

import com.gamelist.seeding.entity.StatusUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusUpdateRepository extends JpaRepository<StatusUpdate, Long> {

//    @Query("SELECT s FROM status_updates s WHERE s.userGame.user.id = ?1")
//    List<StatusUpdateView> findAllProjectedByUserId(Long userId);
}
