package com.gamelist.social_service.service;

import com.gamelist.social_service.model.PostAndStatusUpdateResponseV2;
import java.io.IOException;

public interface InteractiveEntityService {

    PostAndStatusUpdateResponseV2 getPostAndStatusUpdateByUserIdAndStartingId(
            String authorizationHeader, String userId, Long startingId, Integer limit) throws IOException;

    PostAndStatusUpdateResponseV2 getPostAndStatusUpdateByUserIdFirstPage(
            String authorizationHeader, String userId, Integer limit) throws IOException;

    PostAndStatusUpdateResponseV2 getAllPostAndStatusUpdatesFirstPage(String authorizationHeader, Integer limit)
            throws IOException;

    PostAndStatusUpdateResponseV2 getAllPostAndStatusUpdatesByStartingId(
            String authorizationHeader, Long startingId, Integer limit) throws IOException;
}
