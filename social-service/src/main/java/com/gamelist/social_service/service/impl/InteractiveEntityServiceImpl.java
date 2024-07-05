package com.gamelist.social_service.service.impl;

import com.gamelist.social_service.clients.user.UserDTO;
import com.gamelist.social_service.clients.user.UserInfoResponse;
import com.gamelist.social_service.clients.user.UserServiceClient;
import com.gamelist.social_service.dto.PostDTO;
import com.gamelist.social_service.dto.StatusUpdateDTO;
import com.gamelist.social_service.entity.InteractiveEntity;
import com.gamelist.social_service.entity.Post;
import com.gamelist.social_service.entity.StatusUpdate;
import com.gamelist.social_service.mapper.PostMapper;
import com.gamelist.social_service.mapper.StatusUpdateMapper;
import com.gamelist.social_service.model.PostAndStatusUpdateResponse;
import com.gamelist.social_service.repository.InteractiveEntityRepository;
import com.gamelist.social_service.service.InteractiveEntityService;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InteractiveEntityServiceImpl implements InteractiveEntityService {
    private static final Logger log = LoggerFactory.getLogger(InteractiveEntityServiceImpl.class);
    private final InteractiveEntityRepository interactiveEntityRepository;
    private final PostMapper postMapper;
    private final StatusUpdateMapper statusUpdateMapper;
    private final UserServiceClient userServiceClient;

    @Override
    public PostAndStatusUpdateResponse getPostAndStatusUpdateByUserId(String authorizationHeader, String userId) {

        List<InteractiveEntity> postsAndStatusUpdates =
                interactiveEntityRepository.findAllPostsAndStatusUpdatesByUserId(userId);

        return handleGetPostAndStatusUpdateResponse(postsAndStatusUpdates, authorizationHeader);
    }

    @Override
    public PostAndStatusUpdateResponse getPostAndStatusUpdateByUserIdAndStartingId(
            String authorizationHeader, String userId, Long startingId, Integer limit) {
        List<InteractiveEntity> postsAndStatusUpdates =
                interactiveEntityRepository.findPostsAndStatusUpdatesByUserIdAndStartingWithIdDesc(
                        userId, startingId, limit);

        return handleGetPostAndStatusUpdateResponse(postsAndStatusUpdates, authorizationHeader);
    }

    @Override
    public PostAndStatusUpdateResponse getPostAndStatusUpdateByUserIdFirstPage(
            String authorizationHeader, String userId, Integer limit) {
        List<InteractiveEntity> postsAndStatusUpdates =
                interactiveEntityRepository.findPostsAndStatusUpdatesByUserIdFirstPage(userId, limit);

        return handleGetPostAndStatusUpdateResponse(postsAndStatusUpdates, authorizationHeader);
    }

    @Override
    public PostAndStatusUpdateResponse getAllPostAndStatusUpdatesFirstPage(String authorizationHeader, Integer limit) {
        List<InteractiveEntity> postsAndStatusUpdates =
                interactiveEntityRepository.findAllPostsAndStatusUpdatesFirstPage(limit);

        return handleGetPostAndStatusUpdateResponse(postsAndStatusUpdates, authorizationHeader);
    }

    @Override
    public PostAndStatusUpdateResponse getAllPostAndStatusUpdatesByStartingId(
            String authorizationHeader, Long startingId, Integer limit) {

        List<InteractiveEntity> postsAndStatusUpdates =
                interactiveEntityRepository.findAllPostsAndStatusUpdatesStartingWithIdDesc(startingId, limit);

        return handleGetPostAndStatusUpdateResponse(postsAndStatusUpdates, authorizationHeader);
    }

    private PostAndStatusUpdateResponse handleGetPostAndStatusUpdateResponse(
            List<InteractiveEntity> postsAndStatusUpdates, String authorizationHeader) {
        List<PostDTO> posts = new ArrayList<>();
        List<StatusUpdateDTO> statusUpdates = new ArrayList<>();
        Map<String, UserDTO> userInfoCache = new HashMap<>();

        for (InteractiveEntity postOrStatusUpdate : postsAndStatusUpdates) {
            if (postOrStatusUpdate instanceof Post post) {
                PostDTO postDTO = postMapper.postToPostDTO(post);
                String userId = postDTO.getUserId();
                if (userInfoCache.containsKey(userId)) {
                    postDTO.setUser(userInfoCache.get(userId));
                } else {
                    Optional<UserInfoResponse> userDTO = userServiceClient.getUserInfoById(authorizationHeader, userId);
                    if (userDTO.isEmpty()) {
                        throw new RuntimeException("User not found");
                    }
                    UserDTO saveUserDTO = userDTO.get().getData();
                    userInfoCache.put(userId, saveUserDTO);
                    postDTO.setUser(saveUserDTO);
                }

                posts.add(postDTO);
            } else if (postOrStatusUpdate instanceof StatusUpdate) {
                statusUpdates.add(statusUpdateMapper.statusUpdateToStatusUpdateDTO((StatusUpdate) postOrStatusUpdate));
            }
        }

        Long lastPostOrStatusUpdateId =
                postsAndStatusUpdates.get(postsAndStatusUpdates.size() - 1).getId();

        return PostAndStatusUpdateResponse.builder()
                .statusUpdates(statusUpdates)
                .posts(posts)
                .lastPostOrStatusUpdateId(lastPostOrStatusUpdateId)
                .build();
    }
}
