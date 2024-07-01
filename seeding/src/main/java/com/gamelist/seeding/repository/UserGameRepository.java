package com.gamelist.seeding.repository;

import com.gamelist.seeding.entity.UserGame;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserGameRepository extends JpaRepository<UserGame, Long> {
}
