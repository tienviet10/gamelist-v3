package com.gamelist.game_service.entity;

import com.google.gson.annotations.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "company_logos")
public class CompanyLogo {

    @Id
    private Long id;

    private boolean animated;
    private int height;
    private int width;

    @Column(nullable = false)
    private String url;

    @Column(name = "alpha_channel")
    @SerializedName(value = "alpha_channel")
    private boolean alphaChannel;

    @Column(name = "image_id", nullable = false)
    @SerializedName(value = "image_id")
    private String imageId;

    @Column(unique = true, nullable = false)
    private UUID checksum;
}
