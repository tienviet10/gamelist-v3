package com.gamelist.social_service.service;

import com.gamelist.social_service.model.PostAndStatusUpdateResponse;

public interface InteractiveEntityService {

    PostAndStatusUpdateResponse getPostAndStatusUpdateByUserId(Long userId);

    PostAndStatusUpdateResponse getPostAndStatusUpdateByUserIdAndStartingId(
            Long userId, Long startingId, Integer limit);

    PostAndStatusUpdateResponse getPostAndStatusUpdateByUserIdFirstPage(Long userId, Integer limit);

    PostAndStatusUpdateResponse getAllPostAndStatusUpdatesFirstPage(Integer limit);

    PostAndStatusUpdateResponse getAllPostAndStatusUpdatesByStartingId(Long startingId, Integer limit);
}
