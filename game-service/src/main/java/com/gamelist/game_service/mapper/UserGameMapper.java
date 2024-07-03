package com.gamelist.game_service.mapper;

import com.gamelist.game_service.dto.UserGamesDTO;
import com.gamelist.game_service.projection.UserGameProjection;
import java.util.Arrays;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserGameMapper {
    @Named("splitStringToList")
    static List<String> splitStringToList(String input) {
        return input == null ? List.of() : Arrays.asList(input.split("\\s*,\\s*"));
    }

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "avgScore", target = "avgScore")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "imageURL", target = "imageURL")
    @Mapping(source = "bannerURL", target = "bannerURL")
    @Mapping(source = "releaseDate", target = "releaseDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(source = "totalRating", target = "totalRating")
    @Mapping(source = "tags", target = "tags", qualifiedByName = "splitStringToList")
    @Mapping(source = "platforms", target = "platforms", qualifiedByName = "splitStringToList")
    @Mapping(source = "genres", target = "genres", qualifiedByName = "splitStringToList")
    @Mapping(source = "gameAdded", target = "gameAdded")
    @Mapping(source = "gameStatus", target = "gameStatus")
    @Mapping(source = "gameliked", target = "gameLiked")
    UserGamesDTO userGameProjectionToGameDTO(UserGameProjection userGameProjection);

    List<UserGamesDTO> userGameProjectionsToGameDTOs(List<UserGameProjection> userGameProjections);
}
