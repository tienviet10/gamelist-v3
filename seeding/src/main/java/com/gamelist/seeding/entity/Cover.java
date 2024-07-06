package com.gamelist.seeding.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "covers")
public class Cover {

    @Id
    private Long id;

    @Column(name = "alpha_channel")
    private boolean alphaChannel;

    private boolean animated;

    @Column(nullable = false)
    private int height;

    @Column(nullable = false)
    private int width;

    @Column(nullable = false)
    private String url;

    @Column(name = "image_id", nullable = false)
    private String imageId;

    @Column(unique = true, nullable = false)
    private UUID checksum;

    @OneToOne
    @JoinColumn(name = "game_localization_id")
    private GameLocalization gameLocalization;

    @OneToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;
}
