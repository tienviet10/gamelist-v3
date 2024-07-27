package com.gamelist.social_service.dto;

import com.gamelist.social_service.clients.user.UserDTO;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class LikeEntityDTO {
    private Long id;
    //    private UserBasicDTO user;
    private UserDTO user;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
