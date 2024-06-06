package com.gamelist.social_service.service;

import com.gamelist.social_service.entity.Post;
import com.gamelist.social_service.projection.PostView;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface PostService {

    List<PostView> findAllPostsByUserId(Long userId);

    PostView findPostById(Long requestedId, Long userId);

    PostView createPost(Post post, Long userId);

    PostView updatePostById(Long requestedId, Post post, Long userId);

    void deletePostById(Long requestedId, Long userId);

    List<PostView> findAllPosts(Long userId);
}
