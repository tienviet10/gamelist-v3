package com.gamelist.game_service.service.impl;

import com.gamelist.game_service.projection.StatusUpdateView;
import com.gamelist.game_service.repository.StatusUpdateRepository;
import com.gamelist.game_service.service.StatusUpdateService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatusUpdateServiceImpl implements StatusUpdateService {

    private final StatusUpdateRepository statusUpdateRepository;

    @Override
    public List<StatusUpdateView> findAllStatusUpdatesByUserId(String userId) {
        return statusUpdateRepository.findAllProjectedByUserId(userId);
    }
}
