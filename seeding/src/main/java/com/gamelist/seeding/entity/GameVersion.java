package com.gamelist.seeding.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "game_versions")
public class GameVersion {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false)
    private Game game;

    // todo
//    @OneToMany
//    @JoinColumn(nullable = false)
//    private Game games;

    @OneToMany
    @JoinColumn(name = "game_feature_id", nullable = false)
    private Set<GameVersionFeature> gameVersionFeature;

    @Column(unique = true, nullable = false)
    private UUID checksum;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
