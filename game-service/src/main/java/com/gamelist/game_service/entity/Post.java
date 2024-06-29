package com.gamelist.game_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@PrimaryKeyJoinColumn(name = "post_id")
@NoArgsConstructor
@Entity(name = "posts")
public class Post extends InteractiveEntity {

    private String text;

    //    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    //    @JsonIgnore
    //    @JoinColumn(name = "user_id", referencedColumnName = "id")
    //    private User user;
    @Column(name = "user_id")
    private String userId;
}
