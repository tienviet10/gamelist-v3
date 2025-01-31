package com.gamelist.social_service.service;

import com.gamelist.social_service.dto.LikeEntityDTO;

public interface LikeService {
    LikeEntityDTO createLike(String userId, String authorizationHeader, Long interactiveEntityId);

    void deleteLikeById(String userId, Long interactiveEntityId);
}
