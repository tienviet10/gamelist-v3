package com.gamelist.social_service.service;

import com.gamelist.social_service.projection.CommentView;

public interface CommentService {

    CommentView createComment(Long userId, Long interactiveEntityId, String text);

    void deleteCommentById(Long userId, Long commentId);

    CommentView updateCommentById(Long userId, Long interactiveEntityId, String text);
}
