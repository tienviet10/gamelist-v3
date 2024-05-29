package com.gamelist.seeding.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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

    @ManyToOne(
            fetch = FetchType.LAZY
//            TODO: Commented out to seed database
//            cascade = CascadeType.PERSIST
    )
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
