package com.gamelist.social_service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

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
