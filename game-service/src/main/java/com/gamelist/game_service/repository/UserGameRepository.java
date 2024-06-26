package com.gamelist.game_service.repository;

import com.gamelist.game_service.entity.UserGame;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserGameRepository extends JpaRepository<UserGame, Long> {
    UserGame findFirstByUserIdAndGameId(String userId, Long gameId);

    Optional<Set<UserGame>> findAllByUserId(String userId);

    Optional<UserGame> findByGameIdAndUserId(Long gameId, String userId);

    @Query("SELECT CASE WHEN COUNT(ug) > 0 THEN true ELSE false END FROM user_games ug " + "WHERE ug.game.id = :gameId "
            + "AND ug.userId = :userId "
            + "AND ug.gameStatus != 'Inactive'")
    boolean existsByGameIdAndUserIdAndGameStatusNotInactive(
            @Param("gameId") Long gameId, @Param("userId") String userId);
}
