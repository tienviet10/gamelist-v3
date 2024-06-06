package com.gamelist.game_service.repository;

import com.gamelist.game_service.entity.Genre;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query("SELECT name FROM genres")
    List<String> getAllNames();
}
