package com.gamelist.game_service.entity;

import com.gamelist.game_service.enums.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "release_dates")
public class ReleaseDate {

    @Id
    private Long id;

    private LocalDateTime date;

    @Column(name = "m")
    private int month;

    @Column(name = "y")
    private int year;

    @Column(nullable = false)
    private String human;

    @Column(name = "category_type", nullable = false)
    private ReleaseDateCategoryType releaseDateCategoryType;

    @OneToOne
    @JoinColumn(name = "status")
    private ReleaseDateStatus releaseDateStatus;

    @OneToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @OneToOne
    @JoinColumn(name = "platform_id", nullable = false)
    private Platform platform;

    @OneToOne
    @JoinColumn(name = "region", nullable = false)
    private Region region;

    @Column(unique = true, nullable = false)
    private UUID checksum;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
