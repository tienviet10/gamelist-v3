package com.gamelist.social_service.service;

import com.gamelist.social_service.model.PostAndStatusUpdateResponse;

public interface InteractiveEntityService {

    PostAndStatusUpdateResponse getPostAndStatusUpdateByUserId(String authorizationHeader, String userId);

    PostAndStatusUpdateResponse getPostAndStatusUpdateByUserIdAndStartingId(
            String authorizationHeader, String userId, Long startingId, Integer limit);

    PostAndStatusUpdateResponse getPostAndStatusUpdateByUserIdFirstPage(
            String authorizationHeader, String userId, Integer limit);

    PostAndStatusUpdateResponse getAllPostAndStatusUpdatesFirstPage(String authorizationHeader, Integer limit);

    PostAndStatusUpdateResponse getAllPostAndStatusUpdatesByStartingId(
            String authorizationHeader, Long startingId, Integer limit);
}
