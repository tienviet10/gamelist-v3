package com.gamelist.game_service.mapper;

import com.gamelist.game_service.dto.GameDTO;
import com.gamelist.game_service.projection.GameProjection;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface GameV2Mapper {
    @Mapping(source = "platforms", target = "platforms", qualifiedByName = "platformSetToStringList")
    @Mapping(source = "tags", target = "tags", qualifiedByName = "tagSetToStringList")
    @Mapping(source = "genres", target = "genres", qualifiedByName = "genreSetToStringList")
    @Mapping(source = "releasedate", target = "releaseDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(source = "avgScore", target = "avgScore")
    @Mapping(source = "totalRating", target = "totalRating")
    @Mapping(source = "imageurl", target = "imageURL", defaultValue = "")
    @Mapping(source = "bannerurl", target = "bannerURL", defaultValue = "")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name", defaultValue = "")
    @Mapping(source = "gameAdded", target = "gameAdded")
    GameDTO gameToGameDTO(GameProjection game);

    List<GameDTO> gamesToGameDTOs(List<GameProjection> games);

    @Named("platformSetToStringList")
    default List<String> platformSetToStringList(String platforms) {
        return List.of(platforms.split(", "));
    }

    @Named("tagSetToStringList")
    default List<String> tagSetToStringList(String tags) {
        return List.of(tags.split(", "));
    }

    @Named("genreSetToStringList")
    default List<String> genreSetToStringList(String genres) {
        return List.of(genres.split(", "));
    }
}
