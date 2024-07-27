package com.gamelist.social_service.mapper;

import com.gamelist.social_service.dto.PostDTO;
import com.gamelist.social_service.dto.StatusUpdateDTOV2;
import com.gamelist.social_service.projection.InteractiveEntityProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InteractiveEntityMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "text", source = "postText")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "comments", ignore = true)
    PostDTO toPostDTO(InteractiveEntityProjection data);

    @Mapping(target = "id", source = "statusUpdateId")
    @Mapping(target = "userGameId", source = "userGameId")
    @Mapping(target = "gameStatus", source = "suGameStatus")
    @Mapping(target = "userGameUserId", source = "userGameUserId")
    @Mapping(target = "gameId", source = "gameId")
    @Mapping(target = "gameName", source = "gameName")
    @Mapping(target = "gameImageUrl", source = "gameImageurl")
    @Mapping(target = "gameBannerUrl", source = "gameBannerurl")
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "comments", ignore = true)
    StatusUpdateDTOV2 toStatusUpdateDTO(InteractiveEntityProjection data);
}
