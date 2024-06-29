package com.gamelist.game_service.repository;

import com.gamelist.game_service.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface GameRepository extends JpaRepository<Game, Long>, JpaSpecificationExecutor<Game> {
    @Query("SELECT EXTRACT(YEAR FROM MAX(releaseDate)) FROM games")
    int getFurthestYear();

    //    @Query("SELECT g FROM user_games ug JOIN ug.game g WHERE ug.user.id = :userId AND ug.gameStatus = :status")
    //    List<Game> findGamesByUserIdAndStatus(@Param("userId") String userId, @Param("status") GameStatus status);
}
