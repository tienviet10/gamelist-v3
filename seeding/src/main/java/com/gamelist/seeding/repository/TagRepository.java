package com.gamelist.seeding.repository;

import com.gamelist.seeding.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("SELECT name FROM tags")
    List<String> getAllNames();

    Tag findByName(String tagName);
}
