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
@NoArgsConstructor
@Entity(name = "like_entities")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "interactive_entity_id"})})
public class LikeEntity {
    @Id
    @GeneratedValue
    private Long id;

    //    @ManyToOne(
    //            fetch = FetchType.LAZY,
    //            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    //    @JoinColumn(name = "user_id", referencedColumnName = "id")
    //    private User user;
    @Column(name = "user_id")
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "interactive_entity_id")
    private InteractiveEntity interactiveEntity;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
