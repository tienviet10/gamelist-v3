package com.gamelist.social_service.mapper;

import com.gamelist.social_service.dto.CommentDTO;
import com.gamelist.social_service.projection.CommentView;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "commentId", target = "id")
    @Mapping(source = "text", target = "text")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "commentCreatedAt", target = "createdAt")
    @Mapping(source = "commentUpdatedAt", target = "updatedAt")
    CommentDTO commentViewToCommentDTO(CommentView comment);

    List<CommentDTO> commentViewListToCommentDTOList(List<CommentView> comments);
}
