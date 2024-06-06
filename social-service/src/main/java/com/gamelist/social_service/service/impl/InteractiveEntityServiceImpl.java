package com.gamelist.social_service.service.impl;

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
import java.util.ArrayList;
import java.util.List;
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

    @Override
    public PostAndStatusUpdateResponse getPostAndStatusUpdateByUserId(Long userId) {
        List<PostDTO> posts = new ArrayList<>();
        List<StatusUpdateDTO> statusUpdates = new ArrayList<>();

        List<InteractiveEntity> postsAndStatusUpdates =
                interactiveEntityRepository.findAllPostsAndStatusUpdatesByUserId(userId);

        return handleGetPostAndStatusUpdateResponse(posts, statusUpdates, postsAndStatusUpdates);
    }

    @Override
    public PostAndStatusUpdateResponse getPostAndStatusUpdateByUserIdAndStartingId(
            Long userId, Long startingId, Integer limit) {
        List<PostDTO> posts = new ArrayList<>();
        List<StatusUpdateDTO> statusUpdates = new ArrayList<>();

        List<InteractiveEntity> postsAndStatusUpdates =
                interactiveEntityRepository.findPostsAndStatusUpdatesByUserIdAndStartingWithIdDesc(
                        userId, startingId, limit);

        return handleGetPostAndStatusUpdateResponse(posts, statusUpdates, postsAndStatusUpdates);
    }

    @Override
    public PostAndStatusUpdateResponse getPostAndStatusUpdateByUserIdFirstPage(Long userId, Integer limit) {
        List<PostDTO> posts = new ArrayList<>();
        List<StatusUpdateDTO> statusUpdates = new ArrayList<>();

        List<InteractiveEntity> postsAndStatusUpdates =
                interactiveEntityRepository.findPostsAndStatusUpdatesByUserIdFirstPage(userId, limit);

        return handleGetPostAndStatusUpdateResponse(posts, statusUpdates, postsAndStatusUpdates);
    }

    @Override
    public PostAndStatusUpdateResponse getAllPostAndStatusUpdatesFirstPage(Integer limit) {
        log.info("Getting all posts and status updates first page");
        List<PostDTO> posts = new ArrayList<>();
        List<StatusUpdateDTO> statusUpdates = new ArrayList<>();

        List<InteractiveEntity> postsAndStatusUpdates =
                interactiveEntityRepository.findAllPostsAndStatusUpdatesFirstPage(limit);
        log.info("Got all posts and status updates first page");
        return handleGetPostAndStatusUpdateResponse(posts, statusUpdates, postsAndStatusUpdates);
    }

    @Override
    public PostAndStatusUpdateResponse getAllPostAndStatusUpdatesByStartingId(Long startingId, Integer limit) {
        List<PostDTO> posts = new ArrayList<>();
        List<StatusUpdateDTO> statusUpdates = new ArrayList<>();

        List<InteractiveEntity> postsAndStatusUpdates =
                interactiveEntityRepository.findAllPostsAndStatusUpdatesStartingWithIdDesc(startingId, limit);

        return handleGetPostAndStatusUpdateResponse(posts, statusUpdates, postsAndStatusUpdates);
    }

    private PostAndStatusUpdateResponse handleGetPostAndStatusUpdateResponse(
            List<PostDTO> posts, List<StatusUpdateDTO> statusUpdates, List<InteractiveEntity> postsAndStatusUpdates) {
        for (InteractiveEntity postOrStatusUpdate : postsAndStatusUpdates) {
            if (postOrStatusUpdate instanceof Post) {
                posts.add(postMapper.postToPostDTO((Post) postOrStatusUpdate));
            } else if (postOrStatusUpdate instanceof StatusUpdate) {
                statusUpdates.add(statusUpdateMapper.statusUpdateToStatusUpdateDTO((StatusUpdate) postOrStatusUpdate));
            }
        }

        log.info("Got all posts and status updates");

        Long lastPostOrStatusUpdateId =
                postsAndStatusUpdates.get(postsAndStatusUpdates.size() - 1).getId();

        return PostAndStatusUpdateResponse.builder()
                .statusUpdates(statusUpdates)
                .posts(posts)
                .lastPostOrStatusUpdateId(lastPostOrStatusUpdateId)
                .build();
    }
}
