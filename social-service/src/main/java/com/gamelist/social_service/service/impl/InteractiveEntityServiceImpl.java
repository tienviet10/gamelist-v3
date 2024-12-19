package com.gamelist.social_service.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamelist.game.UserInfoGRPCResponse;
import com.gamelist.social_service.clients.user.UserDTO;
import com.gamelist.social_service.dto.*;
import com.gamelist.social_service.gRPCService.UserGRPCServiceClient;
import com.gamelist.social_service.mapper.InteractiveEntityMapper;
import com.gamelist.social_service.model.PostAndStatusUpdateResponseV2;
import com.gamelist.social_service.projection.InteractiveEntityProjection;
import com.gamelist.social_service.repository.InteractiveEntityRepository;
import com.gamelist.social_service.service.InteractiveEntityService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InteractiveEntityServiceImpl implements InteractiveEntityService {
    private static final Logger log = LoggerFactory.getLogger(InteractiveEntityServiceImpl.class);
    private final InteractiveEntityRepository interactiveEntityRepository;
    private final InteractiveEntityMapper interactiveEntityMapper;
    private final UserGRPCServiceClient userGRPCServiceClient;

    @Override
    public PostAndStatusUpdateResponseV2 getPostAndStatusUpdateByUserIdAndStartingId(
            String authorizationHeader, String userId, Long startingId, Integer limit) throws IOException {
        List<InteractiveEntityProjection> postsAndStatusUpdates =
                interactiveEntityRepository.findPostsAndStatusUpdatesByUserIdAndStartingWithIdDescV2(
                        userId, startingId, limit);

        return handleGetPostAndStatusUpdateResponseV2(postsAndStatusUpdates, authorizationHeader);
    }

    @Override
    public PostAndStatusUpdateResponseV2 getPostAndStatusUpdateByUserIdFirstPage(
            String authorizationHeader, String userId, Integer limit) throws IOException {
        List<InteractiveEntityProjection> postsAndStatusUpdates =
                interactiveEntityRepository.findPostsAndStatusUpdatesByUserIdFirstPageV2(userId, limit);

        return handleGetPostAndStatusUpdateResponseV2(postsAndStatusUpdates, authorizationHeader);
    }

    @Override
    public PostAndStatusUpdateResponseV2 getAllPostAndStatusUpdatesFirstPage(String authorizationHeader, Integer limit)
            throws IOException {
        List<InteractiveEntityProjection> postsAndStatusUpdatesV2 =
                interactiveEntityRepository.findAllPostsAndStatusUpdatesFirstPageV2(limit);
        return handleGetPostAndStatusUpdateResponseV2(postsAndStatusUpdatesV2, authorizationHeader);
    }

    @Override
    public PostAndStatusUpdateResponseV2 getAllPostAndStatusUpdatesByStartingId(
            String authorizationHeader, Long startingId, Integer limit) throws IOException {

        List<InteractiveEntityProjection> postsAndStatusUpdates =
                interactiveEntityRepository.findAllPostsAndStatusUpdatesStartingWithIdDescV2(startingId, limit);
        return handleGetPostAndStatusUpdateResponseV2(postsAndStatusUpdates, authorizationHeader);
    }

    private PostAndStatusUpdateResponseV2 handleGetPostAndStatusUpdateResponseV2(
            List<InteractiveEntityProjection> results, String authorizationHeader) throws IOException {
        List<PostDTO> posts = new ArrayList<>();
        List<StatusUpdateDTOV2> statusUpdates = new ArrayList<>();
        Map<String, UserDTO> userInfoCache = new HashMap<>();

        for (InteractiveEntityProjection data : results) {
            if (data.getUserId() != null) {
                String userId = data.getUserId();
                PostDTO postDTO = interactiveEntityMapper.toPostDTO(data);
                postDTO.setUser(fetchUserDetails(userId, authorizationHeader, userInfoCache));

                postDTO.setLikes(parseLikes(data.getLikes(), userInfoCache, authorizationHeader));
                postDTO.setComments(parseComments(data.getComments(), userInfoCache, authorizationHeader));

                posts.add(postDTO);
            } else if (data.getStatusUpdateId() != null) {
                String userId = data.getUserGameUserId();
                StatusUpdateDTOV2 statusUpdateDTO = interactiveEntityMapper.toStatusUpdateDTO(data);
                UserDTO newUser = fetchUserDetails(userId, authorizationHeader, userInfoCache);

                UserGameDTOV2 userGame = statusUpdateDTO.getUserGame();
                if (userGame != null) {
                    userGame.setUser(newUser);
                }

                statusUpdateDTO.setLikes(parseLikes(data.getLikes(), userInfoCache, authorizationHeader));
                statusUpdateDTO.setComments(parseComments(data.getComments(), userInfoCache, authorizationHeader));

                statusUpdates.add(statusUpdateDTO);
            }
        }

        Long lastPostOrStatusUpdateId = results.get(results.size() - 1).getId();

        return PostAndStatusUpdateResponseV2.builder()
                .statusUpdates(statusUpdates)
                .posts(posts)
                .lastPostOrStatusUpdateId(lastPostOrStatusUpdateId)
                .build();
    }

    private UserDTO fetchUserDetails(String userId, String authorizationHeader, Map<String, UserDTO> userInfoCache) {
        if (userInfoCache.containsKey(userId)) {
            return userInfoCache.get(userId);
        } else {

            UserInfoGRPCResponse tempUserInfo = userGRPCServiceClient.getShortUserInfo(userId);
            if (tempUserInfo == null) {
                throw new RuntimeException("User not found");
            }

            UserDTO saveUserDTO = new UserDTO(
                    tempUserInfo.getUsername(), tempUserInfo.getBannerPicture(), tempUserInfo.getUserPicture());
            userInfoCache.put(userId, saveUserDTO);
            return saveUserDTO;
        }
    }

    private List<LikeEntityDTO> parseLikes(
            String likesJson, Map<String, UserDTO> userInfoCache, String authorizationHeader) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(likesJson);
        List<LikeEntityDTO> likes = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            LikeEntityDTO like = new LikeEntityDTO();
            like.setId(node.get("id").asLong());
            like.setUser(fetchUserDetails(node.get("user_id").asText(), authorizationHeader, userInfoCache));
            like.setCreatedAt(LocalDateTime.parse(node.get("created_at").asText()));
            like.setUpdatedAt(LocalDateTime.parse(node.get("updated_at").asText()));
            likes.add(like);
        }
        return likes;
    }

    private List<CommentDTO> parseComments(
            String commentsJson, Map<String, UserDTO> userInfoCache, String authorizationHeader) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(commentsJson);
        List<CommentDTO> comments = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            CommentDTO comment = new CommentDTO();
            comment.setId(node.get("comment_id").asLong());
            comment.setUser(fetchUserDetails(node.get("user_id").asText(), authorizationHeader, userInfoCache));
            comment.setText(node.get("text").asText());
            //            comment.setCreatedAt(node.get("created_at").asText());
            //            comment.setUpdatedAt(node.get("updated_at").asText());
            comments.add(comment);
        }
        return comments;
    }
}
