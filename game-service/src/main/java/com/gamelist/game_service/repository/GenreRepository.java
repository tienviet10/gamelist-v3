package com.gamelist.game_service.repository;

import com.gamelist.game_service.entity.Genre;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.*;

public interface GenreRepository extends PagingAndSortingRepository<Genre, Long> {
    @Query("SELECT name FROM genres")
    List<String> getAllNames();
}
