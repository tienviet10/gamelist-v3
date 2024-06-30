package com.gamelist.social_service.service;

import com.gamelist.social_service.projection.CommentView;

public interface CommentService {

    CommentView createComment(String userId, Long interactiveEntityId, String text);

    void deleteCommentById(String userId, Long commentId);

    CommentView updateCommentById(String userId, Long commentId, String text);
}
