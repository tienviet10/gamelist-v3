package com.gamelist.social_service.model;

import com.gamelist.social_service.dto.PostDTO;
import com.gamelist.social_service.dto.StatusUpdateDTO;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PostAndStatusUpdateResponse {
    private List<PostDTO> posts;
    private List<StatusUpdateDTO> statusUpdates;
    private Long lastPostOrStatusUpdateId;
}
