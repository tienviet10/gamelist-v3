package com.gamelist.social_service.dto;

import com.gamelist.social_service.clients.user.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    //    private UserBasicDTO user;
    private UserDTO user;
    private String text;
    private String createdAt;
    private String updatedAt;
    //    private List<LikeEntityDTO> likes;
    //    private List<CommentDTO> comments;
}
