package com.gamelist.social_service.service;

import com.gamelist.social_service.model.PostAndStatusUpdateResponse;

public interface InteractiveEntityService {

    PostAndStatusUpdateResponse getPostAndStatusUpdateByUserId(String userId);

    PostAndStatusUpdateResponse getPostAndStatusUpdateByUserIdAndStartingId(
            String userId, Long startingId, Integer limit);

    PostAndStatusUpdateResponse getPostAndStatusUpdateByUserIdFirstPage(String userId, Integer limit);

    PostAndStatusUpdateResponse getAllPostAndStatusUpdatesFirstPage(Integer limit);

    PostAndStatusUpdateResponse getAllPostAndStatusUpdatesByStartingId(Long startingId, Integer limit);
}
