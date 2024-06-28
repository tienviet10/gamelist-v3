package com.gamelist.seeding.repository;

import com.gamelist.seeding.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long>, JpaSpecificationExecutor<Game> {
    @Query("SELECT g FROM games g ORDER BY g.id ASC")
    List<Game> findAllGamesOrderedById();
}
