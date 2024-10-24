package com.gamelist.game_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.*;

@Getter
@Setter
@SuperBuilder
@PrimaryKeyJoinColumn(name = "comment_id")
@NoArgsConstructor
@Entity(name = "comments")
public class Comment extends InteractiveEntity {

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interactive_entity_id", referencedColumnName = "id")
    private InteractiveEntity interactiveEntity;
}
