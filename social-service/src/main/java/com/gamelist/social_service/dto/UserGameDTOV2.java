package com.gamelist.social_service.dto;

import com.gamelist.social_service.clients.user.UserDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class UserGameDTOV2 {
    private Long id;
    private GameBasicDTO game;
    private UserDTO user;
}
