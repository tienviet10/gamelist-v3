package com.gamelist.social_service.service;

import com.gamelist.social_service.dto.CommentDTO;
import com.gamelist.social_service.projection.CommentView;

public interface CommentService {

    CommentDTO createComment(String userId, String authorizationHeader, Long interactiveEntityId, String text);

    void deleteCommentById(String userId, Long commentId);

    CommentView updateCommentById(String userId, Long commentId, String text);
}
