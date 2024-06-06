package com.gamelist.game_service.service;

import com.gamelist.game_service.projection.StatusUpdateView;
import java.util.List;

public interface StatusUpdateService {
    List<StatusUpdateView> findAllStatusUpdatesByUserId(Long userId);
}
