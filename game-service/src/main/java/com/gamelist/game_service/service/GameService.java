package com.gamelist.game_service.service;

import com.gamelist.game_service.dto.GameDTO;

public interface GameService {
    GameDTO getAGame(Long gameId);
}
