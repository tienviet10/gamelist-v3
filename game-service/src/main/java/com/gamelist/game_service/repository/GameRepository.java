package com.gamelist.game_service.repository;

import com.gamelist.game_service.entity.Game;
import com.gamelist.game_service.entity.GameStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GameRepository extends JpaRepository<Game, Long>, JpaSpecificationExecutor<Game> {
    @Query("SELECT EXTRACT(YEAR FROM MAX(releaseDate)) FROM games")
    int getFurthestYear();

    @Query("SELECT g FROM user_games ug JOIN ug.game g WHERE ug.user.id = :userId AND ug.gameStatus = :status")
    List<Game> findGamesByUserIdAndStatus(@Param("userId") Long userId, @Param("status") GameStatus status);
}
