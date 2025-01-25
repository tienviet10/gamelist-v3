package com.gamelist.social_service.model;

import com.gamelist.social_service.dto.CommentDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentResponse {
    private List<CommentDTO> comments;
    private boolean hasNextPage;
}
