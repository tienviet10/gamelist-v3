package com.gamelist.social_service.service.impl;

import com.gamelist.social_service.entity.Post;
import com.gamelist.social_service.entity.User;
import com.gamelist.social_service.exception.InvalidAuthorizationException;
import com.gamelist.social_service.exception.InvalidInputException;
import com.gamelist.social_service.exception.InvalidTokenException;
import com.gamelist.social_service.exception.ResourceNotFoundException;
import com.gamelist.social_service.projection.PostView;
import com.gamelist.social_service.projection.UserBasicView;
import com.gamelist.social_service.repository.PostRepository;
import com.gamelist.social_service.repository.UserRepository;
import com.gamelist.social_service.service.PostService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public PostView updatePostById(Long requestedId, Post post, Long userId) {
        if (userId == null) throw new InvalidTokenException("Invalid token");

        Optional<PostView> postOptional = postRepository.findProjectedById(requestedId);
        if (postOptional.isEmpty()) {
            throw new ResourceNotFoundException("Post not found with ID: " + requestedId);
        }

        PostView responseData = postOptional.get();

        if (post.getText() == null || post.getText().isEmpty()) {
            throw new InvalidInputException("Text input value is invalid");
        }
        UserBasicView postOwner = responseData.getUser();

        if (!userId.equals(postOwner.getId())) {
            throw new InvalidAuthorizationException("Invalid authorization");
        }

        Optional<Post> postFromDB = postRepository.findById(responseData.getId());

        Post updatedPost = postFromDB.get();
        updatedPost.setText(post.getText());
        responseData.setText(post.getText());
        postRepository.save(updatedPost);

        return responseData;
    }

    @Override
    public void deletePostById(Long requestedId, Long userId) {
        if (userId == null) throw new InvalidTokenException("Invalid token");
        Optional<Post> postOptional = postRepository.findById(requestedId);
        if (postOptional.isEmpty()) {
            throw new ResourceNotFoundException("Post not found with ID: " + requestedId);
        }

        Post responseData = postOptional.get();
        User postOwner = responseData.getUser();

        if (!userId.equals(postOwner.getId())) {
            throw new InvalidAuthorizationException("Invalid authorization");
        }

        postRepository.deleteById(requestedId);
    }

    @Override
    public List<PostView> findAllPosts(Long userId) {
        if (userId == null) throw new InvalidTokenException("Invalid token");

        return postRepository.findAllPosts();
    }

    @Override
    public PostView createPost(Post post, Long userId) {
        if (userId == null) throw new InvalidTokenException("Invalid token");

        if (post.getText() == null || post.getText().isEmpty()) {
            throw new InvalidInputException("Text input value is invalid");
        }
        User userFromDB = userRepository.findById(userId).get();
        post.setUser(userFromDB);
        postRepository.save(post);

        Optional<PostView> postOptional = postRepository.findProjectedById(post.getId());

        if (postOptional.isEmpty()) {
            throw new ResourceNotFoundException("Post not found with ID: " + post.getId());
        }

        return postOptional.get();
    }

    @Override
    public PostView findPostById(Long requestedId, Long userId) {
        if (userId == null) {
            throw new InvalidTokenException("Invalid token");
        }
        Optional<PostView> postOptional = postRepository.findProjectedById(requestedId);

        if (postOptional.isPresent()) {
            PostView responseData = postOptional.get();
            UserBasicView user = responseData.getUser();

            if (userId.equals(user.getId())) {
                return responseData;
            }
            throw new InvalidTokenException("Invalid token");
        }

        throw new ResourceNotFoundException("Post not found with ID: " + requestedId);
    }

    @Override
    public List<PostView> findAllPostsByUserId(Long userId) {
        if (userId == null) {
            throw new InvalidTokenException("Invalid token");
        }

        Optional<List<PostView>> optionalPosts = postRepository.findAllProjectedByUserId(userId);

        if (optionalPosts.isPresent()) {
            return optionalPosts.get();
        }

        throw new ResourceNotFoundException("Posts not found for user with ID: " + userId);
    }
}