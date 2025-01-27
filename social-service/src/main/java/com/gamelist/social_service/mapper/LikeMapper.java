package com.gamelist.social_service.mapper;

import com.gamelist.social_service.dto.LikeEntityDTO;
import com.gamelist.social_service.projection.LikeEntityView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LikeMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "updatedAt", target = "updatedAt")
    LikeEntityDTO likeViewToLikeDTO(LikeEntityView like);
}
