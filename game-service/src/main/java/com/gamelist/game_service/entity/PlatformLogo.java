package com.gamelist.game_service.entity;

import com.fasterxml.jackson.annotation.*;
import com.google.gson.annotations.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "platform_logos")
public class PlatformLogo {

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
    @SerializedName(value = "alpha_channel")
    private boolean alphaChannel;

    @Column(name = "image_id")
    @SerializedName(value = "image_id")
    private String imageId;

    @Column(unique = true, nullable = false)
    private UUID checksum;
}
