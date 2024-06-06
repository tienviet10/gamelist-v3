package com.gamelist.game_service.repository;

import com.gamelist.game_service.entity.Tag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("SELECT name FROM tags")
    List<String> getAllNames();
}
