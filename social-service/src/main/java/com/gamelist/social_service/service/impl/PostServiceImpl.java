package com.gamelist.social_service.service.impl;

import com.gamelist.game.UserInfoGRPCResponse;
import com.gamelist.social_service.clients.user.UserDTO;
import com.gamelist.social_service.dto.PostDTO;
import com.gamelist.social_service.entity.Post;
import com.gamelist.social_service.exception.InvalidAuthorizationException;
import com.gamelist.social_service.exception.InvalidInputException;
import com.gamelist.social_service.exception.InvalidTokenException;
import com.gamelist.social_service.exception.ResourceNotFoundException;
import com.gamelist.social_service.gRPCService.UserGRPCServiceClient;
import com.gamelist.social_service.mapper.PostMapper;
import com.gamelist.social_service.projection.PostView;
import com.gamelist.social_service.repository.PostRepository;
import com.gamelist.social_service.service.PostService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserGRPCServiceClient userGRPCServiceClient;
    private final PostMapper postMapper;

    @Override
    public PostView updatePostById(Long requestedId, Post post, String userId) {
        if (userId == null) throw new InvalidTokenException("Invalid token");

        Optional<PostView> postOptional = postRepository.findProjectedById(requestedId);
        if (postOptional.isEmpty()) {
            throw new ResourceNotFoundException("Post not found with ID: " + requestedId);
        }

        PostView responseData = postOptional.get();

        if (post.getText() == null || post.getText().isEmpty()) {
            throw new InvalidInputException("Text input value is invalid");
        }
        String postOwner = responseData.getUserId();

        if (!userId.equals(postOwner)) {
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
    public void deletePostById(Long requestedId, String userId) {
        if (userId == null) throw new InvalidTokenException("Invalid token");
        Optional<Post> postOptional = postRepository.findById(requestedId);
        if (postOptional.isEmpty()) {
            throw new ResourceNotFoundException("Post not found with ID: " + requestedId);
        }

        Post responseData = postOptional.get();
        String postOwner = responseData.getUserId();

        if (!userId.equals(postOwner)) {
            throw new InvalidAuthorizationException("Invalid authorization");
        }

        postRepository.deleteById(requestedId);
    }

    @Override
    public List<PostView> findAllPosts(String userId) {
        if (userId == null) throw new InvalidTokenException("Invalid token");

        return postRepository.findAllPosts();
    }

    @Override
    public PostDTO createPost(String authorizationHeader, Post post, String userId) {
        if (userId == null) throw new InvalidTokenException("Invalid token");

        if (post.getText() == null || post.getText().isEmpty()) {
            throw new InvalidInputException("Text input value is invalid");
        }

        UserInfoGRPCResponse tempUserInfo = userGRPCServiceClient.getShortUserInfo(userId);
        if (tempUserInfo == null) {
            throw new RuntimeException("User not found");
        }
        UserDTO userDTO = new UserDTO(
                tempUserInfo.getUsername(), tempUserInfo.getBannerPicture(), tempUserInfo.getUserPicture(), userId);

        post.setUserId(userId);
        postRepository.save(post);
        Optional<PostView> postOptional = postRepository.findProjectedById(post.getId());

        if (postOptional.isEmpty()) {
            throw new ResourceNotFoundException("Post not found with ID: " + post.getId());
        }

        PostDTO newPost = postMapper.postViewToPostDTO(postOptional.get());
        newPost.setUser(userDTO);
        return newPost;
    }

    @Override
    public PostView findPostById(Long requestedId, String userId) {
        if (userId == null) {
            throw new InvalidTokenException("Invalid token");
        }
        Optional<PostView> postOptional = postRepository.findProjectedById(requestedId);

        if (postOptional.isPresent()) {
            PostView responseData = postOptional.get();
            String user = responseData.getUserId();

            if (userId.equals(user)) {
                return responseData;
            }
            throw new InvalidTokenException("Invalid token");
        }

        throw new ResourceNotFoundException("Post not found with ID: " + requestedId);
    }

    @Override
    public List<PostView> findAllPostsByUserId(String userId) {
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
