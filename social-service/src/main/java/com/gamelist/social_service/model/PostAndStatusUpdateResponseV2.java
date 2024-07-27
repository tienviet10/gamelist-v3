package com.gamelist.social_service.model;

import com.gamelist.social_service.dto.PostDTO;
import com.gamelist.social_service.dto.StatusUpdateDTOV2;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PostAndStatusUpdateResponseV2 {
    private List<PostDTO> posts;
    private List<StatusUpdateDTOV2> statusUpdates;
    private Long lastPostOrStatusUpdateId;
}
