package com.gamelist.social_service.mapper;

import com.gamelist.social_service.dto.CommentDTO;
import com.gamelist.social_service.dto.StatusUpdateDTO;
import com.gamelist.social_service.entity.Comment;
import com.gamelist.social_service.entity.StatusUpdate;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StatusUpdateMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "userGame", target = "userGame")
    @Mapping(source = "gameStatus", target = "gameStatus")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "likes", target = "likes")
    @Mapping(source = "comments", target = "comments")
    StatusUpdateDTO statusUpdateToStatusUpdateDTO(StatusUpdate statusUpdate);

    //    @Mapping(target = "statusUpdate.comments", ignore = true)
    List<CommentDTO> commentListToCommentDTOList(List<Comment> comments);
}
