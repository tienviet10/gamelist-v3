package com.gamelist.game_service.projection;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentView {

    Long getId();

    String getUserId();

    String getText();

    void setText(String text);

    LocalDateTime getCreatedAt();

    List<LikeEntityView> getLikes();

    List<CommentView> getComments();
}
