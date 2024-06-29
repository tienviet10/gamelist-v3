package com.gamelist.game_service.service.impl;

import com.gamelist.game_service.projection.StatusUpdateView;
import com.gamelist.game_service.repository.StatusUpdateRepository;
import com.gamelist.game_service.service.StatusUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusUpdateServiceImpl implements StatusUpdateService {

    private final StatusUpdateRepository statusUpdateRepository;

    @Override
    public List<StatusUpdateView> findAllStatusUpdatesByUserId(String userId) {
        return statusUpdateRepository.findAllProjectedByUserId(userId);
    }
}
