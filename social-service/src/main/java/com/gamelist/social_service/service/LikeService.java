package com.gamelist.social_service.service;

import com.gamelist.social_service.projection.LikeEntityView;

public interface LikeService {
    LikeEntityView createLike(Long userId, Long interactiveEntityId);

    void deleteLikeById(Long userId, Long interactiveEntityId);
}
