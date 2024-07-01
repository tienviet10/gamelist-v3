package com.gamelist.social_service.service;

import com.gamelist.social_service.entity.Post;
import com.gamelist.social_service.projection.PostView;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface PostService {

    List<PostView> findAllPostsByUserId(String userId);

    PostView findPostById(Long requestedId, String userId);

    PostView createPost(String authorizationHeader, Post post, String userId);

    PostView updatePostById(Long requestedId, Post post, String userId);

    void deletePostById(Long requestedId, String userId);

    List<PostView> findAllPosts(String userId);
}
