package com.gamelist.game_service.service;

import com.gamelist.game_service.dto.GameDTO;
import com.gamelist.game_service.model.GameQueryFilters;
import java.util.List;

public interface GameService {
    GameDTO getAGame(Long gameId, String userId);

    List<GameDTO> getAllGames(GameQueryFilters gameQueryFilters, String userId);
}
