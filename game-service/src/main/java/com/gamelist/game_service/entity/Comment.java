package com.gamelist.game_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@PrimaryKeyJoinColumn(name = "comment_id")
@NoArgsConstructor
@Entity(name = "comments")
public class Comment extends InteractiveEntity {
    private String text;

    //    @ManyToOne(fetch = FetchType.LAZY)
    //    @JoinColumn(name = "user_id", referencedColumnName = "id")
    //    private User user;
    @Column(name = "user_id")
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interactive_entity_id", referencedColumnName = "id")
    private InteractiveEntity interactiveEntity;
}
