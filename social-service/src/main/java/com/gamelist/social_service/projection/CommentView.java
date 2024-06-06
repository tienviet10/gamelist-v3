package com.gamelist.social_service.projection;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentView {

    Long getId();

    UserBasicView getUser();

    String getText();

    void setText(String text);

    LocalDateTime getCreatedAt();

    List<LikeEntityView> getLikes();

    List<CommentView> getComments();
}
