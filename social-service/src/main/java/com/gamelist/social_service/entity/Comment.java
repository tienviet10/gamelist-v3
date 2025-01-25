package com.gamelist.social_service.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @CreationTimestamp
    @Column(name = "comment_created_at")
    private LocalDateTime commentCreatedAt;

    @UpdateTimestamp
    @Column(name = "comment_updated_at")
    private LocalDateTime commentUpdatedAt;
}
