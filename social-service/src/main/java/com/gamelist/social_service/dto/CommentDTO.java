package com.gamelist.social_service.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class CommentDTO {
    private Long id;
    //    private UserBasicDTO user;
    private String userId;
    private String text;
    private String createdAt;
    private String updatedAt;
    private List<LikeEntityDTO> likes;
    private List<CommentDTO> comments;
}
