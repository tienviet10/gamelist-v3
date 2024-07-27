package com.gamelist.social_service.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InteractiveEntityDataDTO {
    private Long id;
    private Long postId;
    private String postUserId;
    private String postText;
    private LocalDateTime createdAt;
    private Long statusUpdateId;
    private Long userGameId;
    private String gameStatus;
    private String userGameUserId;
    private Long gameId;
    private String gameName;
    private String gameImageUrl;
    private String gameBannerUrl;
    private String likesJson;
    private String commentsJson;
}
