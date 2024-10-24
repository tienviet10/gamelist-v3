package com.gamelist.game_service.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.*;
import org.hibernate.annotations.*;

import java.time.*;
import java.util.*;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@Entity(name = "interactive_entities")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class InteractiveEntity {

    @Id
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "interactiveEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LikeEntity> likes = new ArrayList<>();

//    @OneToMany(mappedBy = "interactiveEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<Comment> comments = new ArrayList<>();

    public void addLike(LikeEntity likeEntity) {
        likes.add(likeEntity);
        likeEntity.setInteractiveEntity(this);
    }

    public void removeLike(LikeEntity likeEntity) {
        likes.remove(likeEntity);
        likeEntity.setInteractiveEntity(null);
    }

//    public void addComment(Comment comment) {
//        comments.add(comment);
//        comment.setInteractiveEntity(this);
//    }
//
//    public void removeComment(Comment comment) {
//        comments.remove(comment);
//        comment.setInteractiveEntity(null);
//    }
}
