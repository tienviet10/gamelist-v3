package com.gamelist.social_service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class UserGameDTO {
    private Long id;
    private GameBasicDTO game;
    //    private UserBasicDTO user;
    private String userId;
}
