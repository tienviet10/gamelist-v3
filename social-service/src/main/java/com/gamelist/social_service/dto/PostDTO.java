package com.gamelist.social_service.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class PostDTO {

    private Long id;
    //    private UserBasicDTO user;
    private String userId;
    private String text;
    private LocalDateTime createdAt;
    private List<LikeEntityDTO> likes;
    private List<CommentDTO> comments;
}
