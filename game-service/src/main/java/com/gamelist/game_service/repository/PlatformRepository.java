package com.gamelist.game_service.repository;

import com.gamelist.game_service.entity.Platform;
import java.util.List;

import jakarta.transaction.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.*;
import org.springframework.transaction.annotation.Transactional;

public interface PlatformRepository extends JpaRepository<Platform, Long> {
    @Query("SELECT name FROM platforms")
    List<String> getAllNames();

    @Transactional(propagation = Propagation.REQUIRES_NEW) //creates a new transaction every time
    <S extends Platform> List <S> saveAll (Iterable<S> entities);
}
