package com.gamelist.game_service.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.*;
import org.hibernate.annotations.*;

import java.time.*;

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

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

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
