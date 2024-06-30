package com.gamelist.social_service.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class LikeEntityDTO {
    private Long id;
    //    private UserBasicDTO user;
    private String userId;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
