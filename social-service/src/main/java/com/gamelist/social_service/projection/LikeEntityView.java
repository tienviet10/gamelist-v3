package com.gamelist.social_service.projection;

import java.time.LocalDateTime;

public interface LikeEntityView {
    Long getId();

    UserBasicView getUser();

    void setUser(UserBasicView user);

    LocalDateTime getUpdatedAt();
}
