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
@Entity(name = "game_localizations")
public class GameLocalization {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private UUID checksum;

    @OneToOne
    @JoinColumn(name = "cover_id")
    private Cover cover;

    @OneToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
