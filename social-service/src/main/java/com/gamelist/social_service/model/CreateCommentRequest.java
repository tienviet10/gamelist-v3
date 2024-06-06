package com.gamelist.social_service.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCommentRequest {
    private Long interactiveEntityId;

    @NotEmpty(message = "Text is required")
    private String text;
}
