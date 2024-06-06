package com.gamelist.social_service.projection;

import java.util.Set;

public interface FollowView {
    Long getId();

    Set<UserBasicView> getFollowers();

    void setFollowers(Set<UserBasicView> followers);

    Set<UserBasicView> getFollowing();

    void setFollowing(Set<UserBasicView> following);
}
