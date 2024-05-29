package com.gamelist.game_service.repository;

import com.gamelist.game_service.entity.GameJournal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameJournalRepository extends JpaRepository<GameJournal, Long> {}
