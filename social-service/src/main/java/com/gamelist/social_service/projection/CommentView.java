package com.gamelist.social_service.projection;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentView {
    Long getId();

    Long getCommentId();

    String getUserId();

    String getText();

    void setText(String text);

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

    LocalDateTime getCommentCreatedAt();

    LocalDateTime getCommentUpdatedAt();

    List<LikeEntityView> getLikes();

    List<CommentView> getComments();
}
