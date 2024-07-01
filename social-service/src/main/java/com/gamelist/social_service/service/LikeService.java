package com.gamelist.social_service.service;

import com.gamelist.social_service.projection.LikeEntityView;

public interface LikeService {
    LikeEntityView createLike(String userId, String authorizationHeader, Long interactiveEntityId);

    void deleteLikeById(String userId, Long interactiveEntityId);
}
