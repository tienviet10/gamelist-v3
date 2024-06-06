package com.gamelist.social_service.service;

import com.gamelist.social_service.projection.FollowView;
import com.gamelist.social_service.projection.UserBasicView;

public interface FollowService {
    UserBasicView createFollow(Long tokenUserId, Long userId);

    UserBasicView removeFollow(Long tokenUserId, Long userId);

    UserBasicView removeFollower(Long tokenUserId, Long userId);

    FollowView getAllFollows(Long userId);
}
