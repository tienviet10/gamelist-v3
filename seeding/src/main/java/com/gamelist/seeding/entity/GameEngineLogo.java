package com.gamelist.seeding.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "game_engine_logos")
public class GameEngineLogo {

    @Id
    private Long id;

    private boolean animated;

    @Column(nullable = false)
    private int height;

    @Column(nullable = false)
    private int width;

    @Column(nullable = false)
    private String url;

    @Column(name = "alpha_channel")
    private boolean alphaChannel;

    @Column(name = "image_id", nullable = false)
    private String imageId;

    @Column(unique = true, nullable = false)
    private UUID checksum;
}