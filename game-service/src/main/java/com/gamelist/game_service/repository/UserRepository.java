package com.gamelist.game_service.repository;

import com.gamelist.game_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u.listsOrder FROM users u WHERE u.id = ?1")
    String findListsOrderById(Long id);
}
