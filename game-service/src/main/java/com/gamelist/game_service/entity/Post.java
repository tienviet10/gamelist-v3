package com.gamelist.game_service.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.*;

@Getter
@Setter
@SuperBuilder
@PrimaryKeyJoinColumn(name = "post_id")
@NoArgsConstructor
@Entity(name = "posts")
public class Post extends InteractiveEntity {

    @Column(nullable = false)
    private String text;

    @ManyToOne(
            fetch = FetchType.LAZY
//            TODO: Commented out to seed database
//            cascade = CascadeType.PERSIST
    )
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}
