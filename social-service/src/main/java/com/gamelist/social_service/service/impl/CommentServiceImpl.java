package com.gamelist.social_service.service.impl;

import com.gamelist.social_service.entity.Comment;
import com.gamelist.social_service.entity.InteractiveEntity;
import com.gamelist.social_service.entity.User;
import com.gamelist.social_service.exception.InvalidAuthorizationException;
import com.gamelist.social_service.exception.ResourceNotFoundException;
import com.gamelist.social_service.projection.CommentView;
import com.gamelist.social_service.repository.CommentRepository;
import com.gamelist.social_service.repository.InteractiveEntityRepository;
import com.gamelist.social_service.repository.UserRepository;
import com.gamelist.social_service.service.CommentService;
import java.util.ArrayList;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final InteractiveEntityRepository interactiveEntityRepository;

    @Override
    public CommentView createComment(Long userId, Long interactiveEntityId, String text) {
        InteractiveEntity interactiveEntity = interactiveEntityRepository
                .findById(interactiveEntityId)
                .orElseThrow(() -> new ResourceNotFoundException("Interactive entity not found"));

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Comment comment = Comment.builder()
                .text(text)
                .user(user)
                .interactiveEntity(interactiveEntity)
                .likes(new ArrayList<>())
                .comments(new ArrayList<>())
                .build();

        comment = commentRepository.save(comment);

        return commentRepository
                .findProjectedById(comment.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Comment not created successfully"));
    }

    @Override
    public void deleteCommentById(Long userId, Long commentId) {
        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment was not found"));

        if (!Objects.equals(comment.getUser().getId(), userId)) {
            throw new InvalidAuthorizationException("You are not authorized to delete this comment");
        }

        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentView updateCommentById(Long userId, Long commentId, String text) {
        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment was not found"));

        if (!Objects.equals((comment).getUser().getId(), userId)) {
            throw new InvalidAuthorizationException("You are not authorized to edit this comment");
        }

        comment.setText(text);
        commentRepository.save(comment);

        return commentRepository
                .findProjectedById(comment.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Comment not updated successfully"));
    }
}