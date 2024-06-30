package com.gamelist.social_service.mapper;

import com.gamelist.social_service.dto.CommentDTO;
import com.gamelist.social_service.dto.PostDTO;
import com.gamelist.social_service.entity.Comment;
import com.gamelist.social_service.entity.Post;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "text", target = "text")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "likes", target = "likes")
    @Mapping(source = "comments", target = "comments")
    PostDTO postToPostDTO(Post post);

    //    @Mapping(target = "post.comments", ignore = true)
    List<CommentDTO> commentListToCommentDTOList(List<Comment> comments);
}
