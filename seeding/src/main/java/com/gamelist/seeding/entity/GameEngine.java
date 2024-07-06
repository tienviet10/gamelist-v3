package com.gamelist.seeding.entity;

import jakarta.persistence.Index;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "game_engines")
public class GameEngine {

    @Id
    private Long id;

    private String description;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String slug;

    @Column(unique = true, nullable = false)
    private UUID checksum;

    @ManyToMany(mappedBy = "gameEngines", fetch = FetchType.LAZY)
    private Set<Company> companies;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "game_engines_platforms",
            joinColumns = @JoinColumn(name = "game_engine_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "platform_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_game_engines_platforms", columnList = "game_engine_id, platform_id", unique = true)
            })
    private Set<Platform> platforms;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
